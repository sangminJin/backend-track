package com.shop.projectlion.web.itemdtl.dto;

import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.itemimage.entity.ItemImage;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class ItemDtlDto {

    private Long itemId;

    private String itemName;

    private Integer price;

    private String itemDetail;

    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    private Integer deliveryFee;

    private List<ItemImageDto> itemImageDtos = new ArrayList<>();

    public static ItemDtlDto of(Item item) {
        List<ItemImageDto> itemImageDtos = ItemImageDto.of(item.getItemImages());

        return ItemDtlDto.builder()
                .itemId(item.getId())
                .itemName(item.getItemName())
                .price(item.getPrice())
                .itemDetail(item.getItemDetail())
                .stockNumber(item.getStockNumber())
                .itemSellStatus(item.getItemSellStatus())
                .deliveryFee(item.getDelivery().getDeliveryFee())
                .itemImageDtos(itemImageDtos)
                .build();
    }

    @Getter
    @Setter
    @Builder
    public static class ItemImageDto {

        private String imageUrl;

        public static List<ItemImageDto> of(List<ItemImage> itemImages) {
            return itemImages.stream()
                    .map(itemImage -> ItemImageDto.builder()
                            .imageUrl(itemImage.getImageUrl())
                            .build())
                    .collect(Collectors.toList());
        }
    }
}
