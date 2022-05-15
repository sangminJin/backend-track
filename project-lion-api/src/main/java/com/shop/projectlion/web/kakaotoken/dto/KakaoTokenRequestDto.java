package com.shop.projectlion.web.kakaotoken.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class KakaoTokenRequestDto {

    private String grant_type;
    private String client_id;
    private String redirect_uri;
    private String code;
    private String client_secret;
    private String refresh_token;

}