package com.shop.projectlion.api.item.dto;

import com.shop.projectlion.domain.delivery.entity.Delivery;
import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import com.shop.projectlion.domain.item.entity.Item;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class ItemModifyRequestDto {

    @ApiModelProperty(value = "상품ID", required = true, example = "1")
    @NotNull(message = "상품ID는 필수 입력 값입니다.")
    private Long itemId;

    @ApiModelProperty(value = "상품명", required = true, example = "떡볶이")
    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemName;

    @ApiModelProperty(value = "상품가격", required = true, example = "5000")
    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price;

    @ApiModelProperty(value = "상품상세", required = true, example = "국내산")
    @NotBlank(message = "상품 상세는 필수 입력 값입니다.")
    private String itemDetail;

    @ApiModelProperty(value = "재고", required = true, example = "50")
    @NotNull(message = "재고는 필수 입력 값입니다.")
    private Integer stockNumber;

    @ApiModelProperty(value = "판매상태", required = true, example = "SELL")
    @NotNull(message = "판매상태는 필수 입력 값입니다.")
    private ItemSellStatus itemSellStatus;

    @ApiModelProperty(value = "배송정보ID", required = true, example = "1")
    @NotNull(message = "배송 정보는 필수 입력 값입니다.")
    private Long deliveryId;

    @Builder
    public ItemModifyRequestDto(Long itemId,
                                String itemName,
                                Integer price,
                                String itemDetail,
                                Integer stockNumber,
                                ItemSellStatus itemSellStatus,
                                Long deliveryId) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.itemDetail = itemDetail;
        this.stockNumber = stockNumber;
        this.itemSellStatus = itemSellStatus;
        this.deliveryId = deliveryId;
    }

    public static ItemModifyRequestDto of(Item item) {
        return ItemModifyRequestDto.builder()
                .itemId(item.getId())
                .itemName(item.getItemName())
                .price(item.getPrice())
                .itemDetail(item.getItemDetail())
                .stockNumber(item.getStockNumber())
                .itemSellStatus(item.getItemSellStatus())
                .deliveryId(item.getDelivery().getId())
                .build();
    }

    public Item toEntity(Delivery delivery) {
        return Item.builder()
                .itemName(itemName)
                .price(price)
                .itemDetail(itemDetail)
                .stockNumber(stockNumber)
                .itemSellStatus(itemSellStatus)
                .delivery(delivery)
                .build();
    }
}
