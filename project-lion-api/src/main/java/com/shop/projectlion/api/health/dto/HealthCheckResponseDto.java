package com.shop.projectlion.api.health.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class HealthCheckResponseDto {

    private boolean status;
    private String health;

}
