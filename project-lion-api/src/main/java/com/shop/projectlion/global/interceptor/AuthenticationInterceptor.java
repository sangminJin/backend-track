package com.shop.projectlion.global.interceptor;

import com.shop.projectlion.domain.jwt.constant.GrantType;
import com.shop.projectlion.domain.jwt.service.TokenManager;
import com.shop.projectlion.domain.member.constant.TokenType;
import com.shop.projectlion.domain.member.exception.NotValidTokenException;
import com.shop.projectlion.global.error.exception.AuthenticationException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // 토큰 유무 확인
        if(!StringUtils.hasText(authorizationHeader))
            throw new AuthenticationException(ErrorCode.NOT_EXISTS_AUTHORIZATION);

        // Bearer Grant Type 검증
        String[] authorizations = authorizationHeader.split(" ");
        if(authorizations.length < 2 || (!GrantType.BEARRER.getType().equals(authorizations[0])))
            throw new AuthenticationException(ErrorCode.NOT_VALID_BEARER_GRANT_TYPE);

        String accessToken = authorizations[1];

        // 토큰 유효성 검증
        if (!tokenManager.validateToken(accessToken))
            throw new NotValidTokenException(ErrorCode.NOT_VALID_TOKEN);

        Claims claims = tokenManager.getTokenClaims(accessToken);

        // TokenType이 ACCESS인지 검증
        if(!TokenType.isAccessToken(claims.getSubject()))
            throw new AuthenticationException(ErrorCode.NOT_ACCESS_TOKEN_TYPE);

        // 엑세스 토큰의 만료 시간 검증
        if (tokenManager.isTokenExpired(claims.getExpiration()))
            throw new NotValidTokenException(ErrorCode.REFRESH_TOKEN_EXPIRED);

        return true;
    }
}