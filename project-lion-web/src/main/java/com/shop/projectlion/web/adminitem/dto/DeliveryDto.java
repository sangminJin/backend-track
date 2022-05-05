package com.shop.projectlion.web.adminitem.dto;

import com.shop.projectlion.domain.delivery.entity.Delivery;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DeliveryDto {

    private Long deliveryId;
    private String deliveryName;
    private int deliveryFee;

    public static DeliveryDto of(Delivery delivery) {
        return DeliveryDto.builder()
                .deliveryId(delivery.getId())
                .deliveryName(delivery.getDeliveryName())
                .deliveryFee(delivery.getDeliveryFee())
                .build();
    }
}