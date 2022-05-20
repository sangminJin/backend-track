package com.shop.projectlion.domain.order.repository;

import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.order.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepositoryCustom {

    Page<Orders> findOrderPage(Pageable pageable, Member member);
}
