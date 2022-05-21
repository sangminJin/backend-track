package com.shop.projectlion.api.health.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@ApiModel(value = "health check api response", description = "서버 health check api response 모델")
public class HealthCheckResponseDto {

    @ApiModelProperty(value = "status 상태", required = true, example = "true")
    private boolean status;

    @ApiModelProperty(value = "health 상태", required = true, example = "ok")
    private String health;
}