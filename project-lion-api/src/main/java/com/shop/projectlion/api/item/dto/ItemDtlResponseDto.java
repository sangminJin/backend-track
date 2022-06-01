package com.shop.projectlion.api.item.dto;

import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.itemimage.entity.ItemImage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@ApiModel(value = "item 단건 조회 api response", description = "서버 item 단건 조회 api response 모델")
public class ItemDtlResponseDto {

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

    @ApiModelProperty(value = "판매상태", required = true, example = "판매중")
    private ItemSellStatus itemSellStatus;

    @ApiModelProperty(value = "배송정보ID", required = true, example = "1")
    private Long deliveryId;

    @ApiModelProperty(value = "배달비", required = true, example = "3000")
    private Integer deliveryFee;

    @ApiModelProperty(value = "상품사진", required = true)
    private List<ItemImageDto> itemImageDtos = new ArrayList<>();

    public static ItemDtlResponseDto of(Item item) {
        List<ItemImageDto> itemImageDtos = ItemImageDto.of(item.getItemImages());

        return ItemDtlResponseDto.builder()
                .itemId(item.getId())
                .itemName(item.getItemName())
                .price(item.getPrice())
                .itemDetail(item.getItemDetail())
                .stockNumber(item.getStockNumber())
                .itemSellStatus(item.getItemSellStatus())
                .deliveryId(item.getDelivery().getId())
                .deliveryFee(item.getDelivery().getDeliveryFee())
                .itemImageDtos(itemImageDtos)
                .build();
    }

    @Getter
    @Setter
    @Builder
    @ApiModel(value = "item image api response", description = "서버 item image 조회 api response 모델")
    public static class ItemImageDto {

        @ApiModelProperty(value = "상품이미지ID", required = true, example = "1")
        private Long itemImageId;

        @ApiModelProperty(value = "이미지경로", required = true, example = "/images/40dccc37-4fdb-4e61-8ca5-488fb46d8ff9.jpg")
        private String imageUrl;

        public static List<ItemImageDto> of(List<ItemImage> itemImages) {
            return itemImages.stream()
                    .map(itemImage -> ItemImageDto.builder()
                            .itemImageId(itemImage.getId())
                            .imageUrl(itemImage.getImageUrl())
                            .build())
                    .collect(Collectors.toList());
        }
    }
}
