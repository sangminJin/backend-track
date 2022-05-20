package com.shop.projectlion.domain.order.entity;

import com.shop.projectlion.domain.common.BaseEntity;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.order.constant.OrderStatus;
import com.shop.projectlion.domain.orderitem.entity.OrderItem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Orders extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @Column(name = "order_time", nullable = false, updatable = false)
    private LocalDateTime orderTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Builder
    public Orders(OrderStatus orderStatus, LocalDateTime orderTime, Member member) {
        this.orderStatus = orderStatus;
        this.orderTime = orderTime;
        this.member = member;
    }

    public static Orders createOrders(Member member, OrderItem orderItem) {
        Orders orders = Orders.builder()
                .orderStatus(OrderStatus.ORDER)
                .orderTime(LocalDateTime.now())
                .member(member)
                .build();

        orders.addOrderItem(orderItem);

        return orders;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public int getTotalPrice() {
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }

    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCEL;
        this.orderItems.forEach(OrderItem::cancel);
    }
}
