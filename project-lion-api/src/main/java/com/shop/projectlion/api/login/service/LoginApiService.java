package com.shop.projectlion.api.login.service;

import com.shop.projectlion.api.login.client.KakaoKapiFeignClient;
import com.shop.projectlion.api.login.dto.KakaoUserInfo;
import com.shop.projectlion.domain.jwt.dto.AccessTokenDto;
import com.shop.projectlion.domain.jwt.dto.TokenDto;
import com.shop.projectlion.domain.jwt.service.TokenManager;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.member.exception.NotValidTokenException;
import com.shop.projectlion.domain.member.service.MemberService;
import com.shop.projectlion.global.error.exception.EntityNotFoundException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import com.shop.projectlion.global.util.DateTimeUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LoginApiService {

    private final KakaoKapiFeignClient kapiFeignClient;
    private final TokenManager tokenManager;
    private final MemberService memberService;

    @Transactional
    public TokenDto login(String authorization) {
        // kakao access token을 이용하여 카카오 회원정보 조회
        KakaoUserInfo kakaoUserInfo = kapiFeignClient.getUserInfo(authorization);
        String email = kakaoUserInfo.getKakaoAccount().getEmail();

        // 기존 회원이면 조회, 아니면 가입
        Member findMember = memberService.findMemberByEmail(email)
                .orElseGet(() -> {
                    Member member = kakaoUserInfo.toEntity();
                    return memberService.registerMember(member);
                });

        // 토근정보 갱신
        TokenDto tokenDto = tokenManager.createTokenDto(findMember.getEmail(), findMember.getRole());
        findMember.updateRefreshToken(tokenDto.getRefreshToken(),
                DateTimeUtils.convertToLocalDateTime(tokenDto.getRefreshTokenExpireTime()));

        return tokenDto;
    }

    @Transactional
    public AccessTokenDto reissueToken(String authorization) {
        String refreshToken = authorization.substring(7);
        if (!tokenManager.validateToken(refreshToken)) throw new NotValidTokenException(ErrorCode.NOT_VALID_TOKEN);

        Claims claims = tokenManager.getTokenClaims(refreshToken);

        Date expiration = claims.getExpiration();
        if (tokenManager.isTokenExpired(expiration)) throw new NotValidTokenException(ErrorCode.REFRESH_TOKEN_EXPIRED);

        // 토근에 들어있는 정보로 멤버 조회
        String email = claims.getAudience();
        Member findMember = memberService.findMemberByEmail(email)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException(ErrorCode.MEMBER_NOT_EXISTS);
                });

        // 토근정보 갱신
        TokenDto tokenDto = tokenManager.createTokenDto(findMember.getEmail(), findMember.getRole());
        findMember.updateRefreshToken(tokenDto.getRefreshToken(),
                DateTimeUtils.convertToLocalDateTime(tokenDto.getRefreshTokenExpireTime()));

        return AccessTokenDto.builder()
                .grantType(tokenDto.getGrantType())
                .accessToken(tokenDto.getAccessToken())
                .accessTokenExpireTime(tokenDto.getAccessTokenExpireTime())
                .build();
    }

    @Transactional
    public void logout(String authorization) {
        String accessToken = authorization.substring(7);
        if (!tokenManager.validateToken(accessToken)) throw new NotValidTokenException(ErrorCode.NOT_VALID_TOKEN);

        Claims claims = tokenManager.getTokenClaims(accessToken);

        Date expiration = claims.getExpiration();
        if (tokenManager.isTokenExpired(expiration)) throw new NotValidTokenException(ErrorCode.ACCESS_TOKEN_EXPIRED);

        // 토근에 들어있는 정보로 멤버 조회
        String email = tokenManager.getMemberEmail(accessToken);
        Member findMember = memberService.findMemberByEmail(email)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException(ErrorCode.MEMBER_NOT_EXISTS);
                });

        // 토근정보 만료
        String expireRefreshToken = tokenManager.createRefreshToken(findMember.getEmail(), new Date(System.currentTimeMillis()));
        findMember.expireToken(expireRefreshToken);
    }
}
