package com.shop.projectlion.web.main.dto;

import com.shop.projectlion.domain.item.entity.Item;
import lombok.*;

@Builder
@Getter @Setter
public class MainItemDto {

    private Long itemId;

    private String itemName;

    private String itemDetail;

    private String imageUrl;

    private Integer price;

    public static MainItemDto of(Item item) {
        return MainItemDto.builder()
                .itemId(item.getId())
                .itemName(item.getItemName())
                .itemDetail(item.getItemDetail())
                .imageUrl(item.getItemImages().get(0).getImageUrl())
                .price(item.getPrice())
                .build();
    }
}
