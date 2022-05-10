package com.shop.projectlion.web.kakaotoken;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class KakaoTokenController {

    @Value("${kakao.client.secret}")
    private String clientSecret;

    @Value("${kakao.client.id}")
    private String clientId;

    @GetMapping("/login")
    public String login(){
        return "loginForm";
    }

    @GetMapping("/auth/kakao/callback")
    public @ResponseBody String loginCallback(String code){

        // todo 카카오 토큰 조회 및 반환 로직 구현
        KakaoTokenResponseDto kakaoToken = new KakaoTokenResponseDto();

        return "kakao token : " + kakaoToken;
    }

}