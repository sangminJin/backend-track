package com.shop.projectlion.web.adminitem.dto;

import com.shop.projectlion.domain.delivery.entity.Delivery;
import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.member.entity.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
public class InsertItemDto {

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemName;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price;

    @NotBlank(message = "상품 상세는 필수 입력 값입니다.")
    private String itemDetail;

    @NotNull(message = "재고는 필수 입력 값입니다.")
    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    @NotNull(message = "배송 정보는 필수 입력 값입니다.")
    private Long deliveryId;

    private List<MultipartFile> itemImageFiles;

    public boolean hasRepImage() {
        MultipartFile repImageFile = itemImageFiles.stream().findFirst().orElse(null);
        return !repImageFile.isEmpty();
    }

    public Item toEntity(Member member, Delivery delivery) {
        return Item.builder()
                .itemDetail(itemDetail)
                .itemName(itemName)
                .itemSellStatus(itemSellStatus)
                .price(price)
                .stockNumber(stockNumber)
                .member(member)
                .delivery(delivery)
                .build();
    }

}