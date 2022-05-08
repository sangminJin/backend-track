package com.shop.projectlion.web.orderhist.dto;

import com.shop.projectlion.domain.order.constant.OrderStatus;
import com.shop.projectlion.domain.order.entity.Orders;
import com.shop.projectlion.domain.orderitem.entity.OrderItem;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class OrderHistDto {

    private Long orderId;
    private LocalDateTime orderTime;
    private OrderStatus orderStatus;
    private int totalPrice;
    private int totalDeliveryFee;
    private List<OrderItemHistDto> orderItemHistDtos;

    public static OrderHistDto of(Orders orders) {
        List<OrderItemHistDto> orderItemHistDtos = OrderItemHistDto.of(orders.getOrderItems());

        return OrderHistDto.builder()
                .orderId(orders.getId())
                .orderTime(orders.getOrderTime())
                .orderStatus(orders.getOrderStatus())
                .totalPrice(orders.getTotalPrice())
                .totalDeliveryFee(orders.getOrderItems().get(0).getItem().getDelivery().getDeliveryFee())
                .orderItemHistDtos(orderItemHistDtos)
                .build();
    }

    @Getter
    @Setter
    @Builder
    public static class OrderItemHistDto {
        private String itemName;
        private int count;
        private int orderPrice;
        private String imageUrl;

        public static List<OrderItemHistDto> of(List<OrderItem> orderItems) {
            return orderItems.stream()
                    .map(orderItem -> OrderItemHistDto.builder()
                            .itemName(orderItem.getItem().getItemName())
                            .count(orderItem.getCount())
                            .orderPrice(orderItem.getOrderPrice())
                            .imageUrl(orderItem.getItem().getItemImages().get(0).getImageUrl())
                            .build())
                    .collect(Collectors.toList());
        }
    }

}