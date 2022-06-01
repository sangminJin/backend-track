package com.shop.projectlion.api.item.dto;

import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import com.shop.projectlion.domain.item.entity.Item;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ItemModifyResponseDto {

    @ApiModelProperty(value = "상품ID", required = true, example = "1")
    private Long itemId;

    @ApiModelProperty(value = "상품명", required = true, example = "떡볶이")
    private String itemName;

    @ApiModelProperty(value = "상품가격", required = true, example = "5000")
    private Integer price;

    @ApiModelProperty(value = "상품상세", required = true, example = "국내산")
    private String itemDetail;

    @ApiModelProperty(value = "재고", required = true, example = "50")
    private Integer stockNumber;

    @ApiModelProperty(value = "판매상태", required = true, example = "SELL")
    private ItemSellStatus itemSellStatus;

    @ApiModelProperty(value = "배송정보ID", required = true, example = "1")
    private Long deliveryId;

    public static ItemModifyResponseDto of(Item item) {
        return ItemModifyResponseDto.builder()
                .itemId(item.getId())
                .itemName(item.getItemName())
                .price(item.getPrice())
                .itemDetail(item.getItemDetail())
                .stockNumber(item.getStockNumber())
                .itemSellStatus(item.getItemSellStatus())
                .deliveryId(item.getDelivery().getId())
                .build();
    }
}
