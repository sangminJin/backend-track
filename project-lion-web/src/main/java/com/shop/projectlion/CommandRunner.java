package com.shop.projectlion;

import com.shop.projectlion.domain.delivery.entity.Delivery;
import com.shop.projectlion.domain.delivery.repository.DeliveryRepository;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.member.service.MemberService;
import com.shop.projectlion.web.login.dto.MemberRegisterDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommandRunner implements CommandLineRunner {

    private final MemberService memberService;

    private final DeliveryRepository deliveryRepository;

    @Override
    public void run(String... args) {
        initData();
    }

    @Transactional
    public void initData() {
        MemberRegisterDto memberRegisterDto = MemberRegisterDto.builder()
                .name("관리자")
                .email("admin@lionshop.com")
                .password("1q2w3e4r")
                .password2("1q2w3e4r")
                .build();

        Member saveMember = memberService.saveMember(memberRegisterDto);

        Delivery delivery1 = Delivery.builder()
                .deliveryName("마포구 물류센터")
                .deliveryFee(3000)
                .member(saveMember)
                .build();

        Delivery delivery2 = Delivery.builder()
                .deliveryName("마포구 물류센터 무료배송")
                .deliveryFee(0)
                .member(saveMember)
                .build();

        deliveryRepository.save(delivery1);
        deliveryRepository.save(delivery2);

        log.info("==========================");
        log.info("테스트용 초기 계정 정보");
        log.info("ID : admin@lionshop.com");
        log.info("PW : 1q2w3e4r");
        log.info("권한 : ADMIN");
        log.info("==========================");
    }

}
