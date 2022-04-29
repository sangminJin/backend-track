package com.shop.projectlion.web.adminitem.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class DeliveryDto {

    private Long deliveryId;
    private String deliveryName;
    private int deliveryFee;

    @Builder
    public DeliveryDto(Long deliveryId, String deliveryName, int deliveryFee) {
        this.deliveryId = deliveryId;
        this.deliveryName = deliveryName;
        this.deliveryFee = deliveryFee;
    }
}