package com.shop.projectlion.web.orderhist.service;

import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.member.service.MemberService;
import com.shop.projectlion.domain.order.entity.Orders;
import com.shop.projectlion.domain.order.service.OrderService;
import com.shop.projectlion.web.orderhist.dto.OrderHistDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderHistService {

    private final MemberService memberService;
    private final OrderService orderService;

    public Page<OrderHistDto> getOrderHistDtoPage(Pageable pageable, String email) {
        Member findMember = memberService.findMemberByEmail(email);
        Page<Orders> results = orderService.getOrdersPage(pageable, findMember);

        List<OrderHistDto> orderHistDtos = results.map(OrderHistDto::of)
                .stream()
                .collect(Collectors.toList());

        return new PageImpl<>(orderHistDtos, pageable, results.getTotalElements());
    }
}
