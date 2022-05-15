package com.shop.projectlion.api.login.client;

import com.shop.projectlion.api.login.dto.KakaoUserInfo;
import com.shop.projectlion.global.error.exception.FeignClientException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "kapi", url = "${kakao.kapi.server}")
public interface KakaoKapiFeignClient {

    @GetMapping(value = "/v2/user/me")
    KakaoUserInfo getUserInfo(@RequestHeader String authorization) throws FeignClientException;

    @PostMapping(value = "/v1/user/logout", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String logout(@RequestHeader String authorization) throws FeignClientException;
}
