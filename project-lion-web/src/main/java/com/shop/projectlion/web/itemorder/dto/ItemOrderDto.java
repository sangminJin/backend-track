package com.shop.projectlion.web.itemorder.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter @Setter
@Builder
public class ItemOrderDto {

    @NotNull(message = "상품은 필수 입력값입니다.")
    private Long itemId;

    @Min(value = 1, message = "최소 주문 수량은 1개입니다.")
    @Max(value = 99, message = "최대 주문 수량은 99개입니다.")
    private Integer count;
}
