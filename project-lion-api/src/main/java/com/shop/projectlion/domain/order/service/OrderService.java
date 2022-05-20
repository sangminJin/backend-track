package com.shop.projectlion.domain.order.service;

import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.order.entity.Orders;
import com.shop.projectlion.domain.order.repository.OrderRepository;
import com.shop.projectlion.global.error.exception.EntityNotFoundException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public Orders saveOrders(Orders orders) {
        return orderRepository.save(orders);
    }

    public Page<Orders> getOrdersPage(Pageable pageable, Member member) {
        return orderRepository.findOrderPage(pageable, member);
    }

    public Orders findByOrderId(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ORDER_NOT_EXISTS));
    }
}
