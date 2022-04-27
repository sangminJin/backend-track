package com.shop.projectlion.domain.member.entity;

import com.shop.projectlion.domain.base.BaseEntity;
import com.shop.projectlion.domain.member.constant.MemberType;
import com.shop.projectlion.domain.member.constant.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "member_name", nullable = false)
    private String memberName;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_type")
    private MemberType memberType;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "token_expiration_time")
    private LocalDateTime tokenExpirationTime;

    @Builder
    public Member(String email, String memberName, MemberType memberType, String password, Role role) {
        this.email = email;
        this.memberName = memberName;
        this.memberType = memberType;
        this.password = password;
        this.role = role;
    }
}
