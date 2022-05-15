package com.shop.projectlion.web.kakaotoken.controller;

import com.shop.projectlion.api.login.client.KakaoKauthFeignClient;
import com.shop.projectlion.web.kakaotoken.dto.KakaoTokenRequestDto;
import com.shop.projectlion.web.kakaotoken.dto.KakaoTokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class KakaoTokenController {

    private final KakaoKauthFeignClient kakaoKauthFeignClient;

    @Value("${kakao.client.secret}")
    private String clientSecret;

    @Value("${kakao.client.id}")
    private String clientId;

    @GetMapping("/login")
    public String login() {
        return "loginForm";
    }

    @GetMapping("/auth/kakao/callback")
    @ResponseBody
    public String loginCallback(String code, HttpServletRequest request) {
        KakaoTokenRequestDto requestDto = KakaoTokenRequestDto.builder()
                .grant_type("authorization_code")
                .client_id(clientId)
                .redirect_uri(request.getRequestURL().toString())
                .code(code)
                .client_secret(clientSecret)
                .build();

        KakaoTokenResponseDto kakaoToken = kakaoKauthFeignClient.getAccessToken(requestDto);

        return "kakao token : " + kakaoToken;
    }
}