package com.shop.projectlion.api.login.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop.projectlion.domain.member.constant.MemberType;
import com.shop.projectlion.domain.member.constant.Role;
import com.shop.projectlion.domain.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter @Setter
@JsonIgnoreProperties({"connected_at"})
public class KakaoUserInfo {

    private String id;

    private Map<String, String> properties;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter @Setter
    @JsonIgnoreProperties({"profile_nickname_needs_agreement", "profile_image_needs_agreement", "profile", "has_email",
            "email_needs_agreement", "is_email_valid", "is_email_verified"})
    public static class KakaoAccount {
        private String email;
    }

    public Member toEntity() {
        return Member.builder()
                .email(kakaoAccount.getEmail())
                .memberName(properties.get("nickname"))
                .memberType(MemberType.KAKAO)
                .role(Role.ADMIN)
                .build();
    }

}