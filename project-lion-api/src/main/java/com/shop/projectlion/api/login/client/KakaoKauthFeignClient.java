package com.shop.projectlion.api.login.client;

import com.shop.projectlion.global.error.exception.FeignClientException;
import com.shop.projectlion.web.kakaotoken.dto.KakaoTokenRequestDto;
import com.shop.projectlion.web.kakaotoken.dto.KakaoTokenResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "kauth", url = "${kakao.kauth.server}")
public interface KakaoKauthFeignClient {

    @PostMapping(value = "/oauth/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    KakaoTokenResponseDto getAccessToken(KakaoTokenRequestDto kakaoTokenRequestDto) throws FeignClientException;

}
