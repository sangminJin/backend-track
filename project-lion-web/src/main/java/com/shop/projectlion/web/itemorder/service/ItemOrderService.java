package com.shop.projectlion.web.itemorder.service;

import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.item.service.ItemService;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.member.service.MemberService;
import com.shop.projectlion.domain.order.entity.Orders;
import com.shop.projectlion.domain.order.service.OrderService;
import com.shop.projectlion.domain.orderitem.entity.OrderItem;
import com.shop.projectlion.web.itemorder.dto.ItemOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemOrderService {

    private final MemberService memberService;
    private final ItemService itemService;
    private final OrderService orderService;

    @Transactional
    public void orderItem(ItemOrderDto itemOrderDto, String email) {
        Member member = memberService.findMemberByEmail(email);
        Item findItem = itemService.findByItemId(itemOrderDto.getItemId());

        // 주문상품 엔티티 생성
        OrderItem orderItem = OrderItem.createOrderItem(findItem, itemOrderDto.getCount());
        // 주문 엔티티 생성
        Orders orders = Orders.createOrders(member, orderItem);

        orderService.saveOrders(orders);
    }

    @Transactional
    public void cancelOrderItem(Long orderId) {
        Orders findOrder = orderService.findByOrderId(orderId);
        findOrder.cancelOrder();
    }

    public boolean hasCancelAuth(Long orderId, String email) {
        Member findMember = memberService.findMemberByEmail(email);

        String loginMemberEmail = findMember.getEmail();
        String orderMemberEmail = orderService.findByOrderId(orderId).getMember().getEmail();
        
        return StringUtils.equals(loginMemberEmail, orderMemberEmail);
    }
}
