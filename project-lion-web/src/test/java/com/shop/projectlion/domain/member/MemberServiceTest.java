package com.shop.projectlion.domain.member;

import com.shop.projectlion.web.login.dto.MemberRegisterDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(locations = "classpath:application-test.yml")
@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 성공")
    void signUpTest() {
        // given
        MemberRegisterDto memberRegisterDto = MemberRegisterDto.builder()
                .name("홍길동")
                .email("hong@gmail.com")
                .password("1q2w3e4r")
                .password2("1q2w3e4r")
                .build();

        // when
        Long savedId = memberService.saveMember(memberRegisterDto);
        Member findMember = memberRepository.findById(savedId).get();

        // then
        assertEquals(findMember.getMemberName(), memberRegisterDto.getName());
        assertEquals(findMember.getEmail(), memberRegisterDto.getEmail());
    }
}