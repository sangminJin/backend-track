package com.shop.projectlion.api.login.controller;

import com.shop.projectlion.api.login.dto.MemberTypeRequestDto;
import com.shop.projectlion.api.login.service.LoginApiService;
import com.shop.projectlion.domain.jwt.constant.GrantType;
import com.shop.projectlion.domain.jwt.dto.AccessTokenDto;
import com.shop.projectlion.domain.jwt.dto.TokenDto;
import com.shop.projectlion.global.error.exception.BusinessException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import com.shop.projectlion.global.error.exception.RequestHeaderException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class LoginApiController {

    private final LoginApiService loginApiService;

    @PostMapping(value = "/oauth/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenDto> login(@RequestHeader String authorization,
                                         @RequestBody MemberTypeRequestDto memberTypeRequestDto) {
        if(!memberTypeRequestDto.getMemberType().equals("kakao")) throw new BusinessException(ErrorCode.INVALID_MEMBER_TYPE);
        if(authorization.isBlank()) throw new RequestHeaderException(ErrorCode.NOT_EXISTS_AUTHORIZATION);
        if(!authorization.startsWith(GrantType.BEARRER.getType())) throw new RequestHeaderException(ErrorCode.NOT_VALID_BEARER_GRANT_TYPE);

        TokenDto tokenDto = loginApiService.login(authorization);

        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<AccessTokenDto> reissueAccessToken(@RequestHeader String authorization) {
        if(authorization.isBlank()) throw new RequestHeaderException(ErrorCode.NOT_EXISTS_AUTHORIZATION);
        if(!authorization.startsWith(GrantType.BEARRER.getType())) throw new RequestHeaderException(ErrorCode.NOT_VALID_BEARER_GRANT_TYPE);

        AccessTokenDto accessTokenDto = loginApiService.reissueToken(authorization);

        return ResponseEntity.ok(accessTokenDto);
    }

    @PostMapping(value = "/logout", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> logout(@RequestHeader String authorization) {
        if(authorization.isBlank()) throw new RequestHeaderException(ErrorCode.NOT_EXISTS_AUTHORIZATION);
        if(!authorization.startsWith(GrantType.BEARRER.getType())) throw new RequestHeaderException(ErrorCode.NOT_VALID_BEARER_GRANT_TYPE);

        loginApiService.logout(authorization);

        return ResponseEntity.ok("logout success");
    }
}
