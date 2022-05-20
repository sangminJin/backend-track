package com.shop.projectlion.api.login.controller;

import com.shop.projectlion.api.login.dto.MemberTypeRequestDto;
import com.shop.projectlion.api.login.service.LoginApiService;
import com.shop.projectlion.api.login.validator.LoginValidator;
import com.shop.projectlion.domain.jwt.dto.AccessTokenDto;
import com.shop.projectlion.domain.jwt.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class LoginApiController {

    private final LoginApiService loginApiService;
    private final LoginValidator loginValidator;

    @PostMapping(value = "/oauth/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenDto> login(@RequestHeader String authorization,
                                         @RequestBody MemberTypeRequestDto memberTypeRequestDto) {
        loginValidator.validateAuthorization(authorization);
        loginValidator.validateMemberType(memberTypeRequestDto.getMemberType());

        TokenDto tokenDto = loginApiService.login(authorization);

        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<AccessTokenDto> reissueAccessToken(@RequestHeader String authorization) {
        loginValidator.validateAuthorization(authorization);

        AccessTokenDto accessTokenDto = loginApiService.reissueToken(authorization);

        return ResponseEntity.ok(accessTokenDto);
    }

    @PostMapping(value = "/logout", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> logout(@RequestHeader String authorization) {
        loginValidator.validateAuthorization(authorization);

        loginApiService.logout(authorization);

        return ResponseEntity.ok("logout success");
    }
}
