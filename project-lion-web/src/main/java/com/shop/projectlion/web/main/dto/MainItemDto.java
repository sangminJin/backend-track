package com.shop.projectlion.web.main.dto;

import lombok.*;

@Builder
@Getter @Setter
public class MainItemDto {

    private Long itemId;

    private String itemName;

    private String itemDetail;

    private String imageUrl;

    private Integer price;

}
