package com.shop.projectlion.domain.member.entity;

import com.shop.projectlion.domain.common.BaseEntity;
import com.shop.projectlion.domain.member.constant.MemberType;
import com.shop.projectlion.domain.member.constant.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, length = 50, nullable = false)
    private String email;

    @Column(length = 200)
    private String password;

    @Column(name = "member_name", nullable = false, length = 20)
    private String memberName;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_type", nullable = false, length = 10)
    private MemberType memberType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    @Column(name = "refresh_token", length = 250)
    private String refreshToken;

    @Column(name = "token_expiration_time")
    private LocalDateTime tokenExpirationTime;

    @Builder
    public Member(MemberType memberType,
                  String email,
                  String password,
                  String memberName,
                  Role role) {
        this.memberType = memberType;
        this.email = email;
        this.password = password;
        this.memberName = memberName;
        this.role = role;
    }

    public void updateRefreshToken(String refreshToken, LocalDateTime tokenExpirationTime) {
        this.refreshToken = refreshToken;
        this.tokenExpirationTime = tokenExpirationTime;
    }

    public void expireToken(String expireRefreshToken) {
        this.refreshToken = expireRefreshToken;
        tokenExpirationTime = LocalDateTime.now();
    }

    public boolean hasAdminRole() {
        return Role.ADMIN == this.role;
    }
}