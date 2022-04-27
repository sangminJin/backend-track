package com.shop.projectlion.domain.member.service;

import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.member.repository.MemberRepository;
import com.shop.projectlion.global.error.exception.BusinessException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import com.shop.projectlion.web.login.dto.MemberRegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long saveMember(MemberRegisterDto memberRegisterDto) {
        checkPwdEquals(memberRegisterDto.getPassword(), memberRegisterDto.getPassword2());
        checkDuplicateMember(memberRegisterDto.getEmail());

        Member savedMember = memberRepository.save(memberRegisterDto.toEntity(passwordEncoder));
        return savedMember.getId();
    }

    private void checkPwdEquals(String pw1, String pw2) {
        if(pw1.isEmpty() || pw2.isEmpty() || !pw1.equals(pw2)) {
            throw new BusinessException(ErrorCode.MISMATCHED_PASSWORD);
        }
    }

    private void checkDuplicateMember(String email) {
        Optional<Member> result = memberRepository.findByEmail(email);
        if(result.isPresent()) {
            throw new BusinessException(ErrorCode.ALREADY_REGISTERED_MEMBER);
        }
    }
}
