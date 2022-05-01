package com.shop.projectlion.web.adminitem.dto;

import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.item.entity.ItemImage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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

    private List<Long> itemImageIds;

    public boolean hasRepImage() {
        MultipartFile repImageFile = itemImageFiles.stream().findFirst().orElse(null);
        String repImageFileName = originalImageNames.stream().findFirst().orElse(null);

        if(repImageFile.isEmpty() && repImageFileName.isBlank()) return false;
        return true;
    }

    public void makeItemImageDtos() {
        List<ItemImageDto> itemImageDtos = new ArrayList<>();
        for(int i=0; i<5; i++) {
            ItemImageDto itemImageDto = ItemImageDto.builder()
                    .itemImageId(itemImageIds.get(i))
                    .originalImageName(originalImageNames.get(i))
                    .build();
            itemImageDtos.add(itemImageDto);
        }
        this.itemImageDtos = itemImageDtos;
    }

    @Setter
    @Getter
    public static class ItemImageDto {
        private Long itemImageId;
        private String originalImageName;

        @Builder
        public ItemImageDto(Long itemImageId, String originalImageName) {
            this.itemImageId = itemImageId;
            this.originalImageName = originalImageName;
        }
    }

    public UpdateItemDto(Item entity) {
        this.itemId = entity.getId();
        this.itemName = entity.getItemName();
        this.price = entity.getPrice();
        this.itemDetail = entity.getItemDetail();
        this.stockNumber = entity.getStockNumber();
        this.itemSellStatus = entity.getItemSellStatus();
        this.deliveryId = entity.getDelivery().getId();
        // 아이템 이미지 관련 필드 초기화
        initItemImageInfo(entity.getItemImages());
    }

    private void initItemImageInfo(List<ItemImage> itemImages) {
        List<ItemImageDto> itemImageDtos = new ArrayList<>();
        List<String> originalImageNames = new ArrayList<>();
        List<Long> itemImageIds = new ArrayList<>();

        itemImages.forEach((itemImage) -> {
            ItemImageDto itemImageDto = ItemImageDto.builder()
                    .itemImageId(itemImage.getId())
                    .originalImageName(itemImage.getOriginalImageName())
                    .build();

            itemImageDtos.add(itemImageDto);
            originalImageNames.add(itemImage.getOriginalImageName());
            itemImageIds.add(itemImage.getId());
        });

        this.itemImageDtos = itemImageDtos;
        this.originalImageNames = originalImageNames;
        this.itemImageIds = itemImageIds;
    }
}