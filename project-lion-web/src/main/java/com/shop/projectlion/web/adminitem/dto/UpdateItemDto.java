package com.shop.projectlion.web.adminitem.dto;

import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.itemimage.entity.ItemImage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
public class UpdateItemDto {

    private Long itemId;

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

    private List<ItemImageDto> itemImageDtos;

    private List<String> originalImageNames;


    public boolean hasRepImage() {
        MultipartFile repImageFile = itemImageFiles.stream().findFirst().orElse(null);
        String repImageFileName = originalImageNames.stream().findFirst().orElse(null);

        if(repImageFile.isEmpty() && repImageFileName.isBlank()) return false;
        return true;
    }

    @Builder
    public UpdateItemDto(Long itemId,
                         String itemName,
                         Integer price,
                         String itemDetail,
                         Integer stockNumber,
                         ItemSellStatus itemSellStatus,
                         Long deliveryId,
                         List<ItemImageDto> itemImageDtos) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.itemDetail = itemDetail;
        this.stockNumber = stockNumber;
        this.itemSellStatus = itemSellStatus;
        this.deliveryId = deliveryId;
        this.itemImageDtos = itemImageDtos;
    }

    public Item toEntity() {
        return Item.builder()
                .itemName(itemName)
                .itemDetail(itemDetail)
                .price(price)
                .itemSellStatus(itemSellStatus)
                .stockNumber(stockNumber)
                .build();
    }

    public static UpdateItemDto of(Item item) {
        List<ItemImageDto> itemImageDtos = ItemImageDto.of(item.getItemImages());

        UpdateItemDto updateItemDto = UpdateItemDto.builder()
                .itemId(item.getId())
                .itemName(item.getItemName())
                .itemDetail(item.getItemDetail())
                .itemSellStatus(item.getItemSellStatus())
                .stockNumber(item.getStockNumber())
                .price(item.getPrice())
                .deliveryId(item.getDelivery().getId())
                .itemImageDtos(itemImageDtos)
                .build();

        return updateItemDto;
    }

    @Builder
    @Getter
    @Setter
    public static class ItemImageDto {
        private Long itemImageId;
        private String originalImageName;

        public static List<ItemImageDto> of(List<ItemImage> itemImages) {
            return itemImages.stream()
                    .map(itemImage -> ItemImageDto.builder()
                            .itemImageId(itemImage.getId())
                            .originalImageName(itemImage.getOriginalImageName())
                            .build())
                    .collect(Collectors.toList());
        }
    }
}