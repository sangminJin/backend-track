package com.shop.projectlion.api.item.dto;

import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.itemimage.entity.ItemImage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class ItemDtlResponseDto {

    private Long itemId;

    private String itemName;

    private Integer price;

    private String itemDetail;

    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    private Long deliveryId;

    private Integer deliveryFee;

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
    public static class ItemImageDto {

        private Long itemImageId;
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
