package com.shop.projectlion.api.item.dto;

import com.shop.projectlion.domain.delivery.entity.Delivery;
import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import com.shop.projectlion.domain.item.entity.Item;
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

    @NotNull(message = "상품ID는 필수 입력 값입니다.")
    private Long itemId;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemName;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price;

    @NotBlank(message = "상품 상세는 필수 입력 값입니다.")
    private String itemDetail;

    @NotNull(message = "재고는 필수 입력 값입니다.")
    private Integer stockNumber;

    @NotNull(message = "판매상태는 필수 입력 값입니다.")
    private ItemSellStatus itemSellStatus;

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
