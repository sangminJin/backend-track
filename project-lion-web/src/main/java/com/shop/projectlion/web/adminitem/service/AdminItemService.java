package com.shop.projectlion.web.adminitem.service;

import com.shop.projectlion.domain.delivery.entity.Delivery;
import com.shop.projectlion.domain.delivery.service.DeliveryService;
import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.itemimage.entity.ItemImage;
import com.shop.projectlion.domain.itemimage.service.ItemImageService;
import com.shop.projectlion.domain.item.service.ItemService;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.global.error.exception.BusinessException;
import com.shop.projectlion.web.adminitem.dto.DeliveryDto;
import com.shop.projectlion.web.adminitem.dto.InsertItemDto;
import com.shop.projectlion.web.adminitem.dto.UpdateItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AdminItemService {

    private final DeliveryService deliveryService;
    private final ItemService itemService;
    private final ItemImageService itemImageService;

    public List<DeliveryDto> getMemberDeliveryDtos(Member member) {
        return deliveryService.findByMember(member)
                .stream()
                .map(DeliveryDto::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long registerItem(InsertItemDto insertItemDto, Member member) throws IOException {
        Delivery delivery = deliveryService.findByDeliveryId(insertItemDto.getDeliveryId());
        Item saveItem = itemService.registerItem(insertItemDto.toEntity(member, delivery));
        itemImageService.registerItemImages(saveItem, insertItemDto.getItemImageFiles());
        return saveItem.getId();
    }

    public UpdateItemDto getUpdateItemDto(Long itemId) throws BusinessException {
        Item findItem = itemService.findItemDetailsById(itemId);
        return UpdateItemDto.of(findItem);
    }

    public List<UpdateItemDto.ItemImageDto> getItemImageDto(Long itemId) {
        Item findItem = itemService.findByItemId(itemId);
        List<ItemImage> findItemImages = itemImageService.findByItemOrderByIdAsc(findItem);
        return UpdateItemDto.ItemImageDto.of(findItemImages);
    }

    @Transactional
    public void updateItem(UpdateItemDto updateItemDto) throws IOException {
        Item updatedItem = itemService.updateItem(updateItemDto.getItemId(), updateItemDto.toEntity());

        Delivery delivery = deliveryService.findByDeliveryId(updateItemDto.getDeliveryId());
        updatedItem.modifyDelivery(delivery);
        
        updateItemImages(updateItemDto, updatedItem);
    }

    private void updateItemImages(UpdateItemDto updateItemDto, Item item) throws IOException {
        List<ItemImage> itemImages = itemImageService.findByItemOrderByIdAsc(item);
        List<MultipartFile> itemImageFiles = updateItemDto.getItemImageFiles();
        List<String> originalImageNames = updateItemDto.getOriginalImageNames();

        for(int i=0; i<itemImages.size(); i++) {
            ItemImage itemImage = itemImages.get(i);
            MultipartFile itemImageFile = itemImageFiles.get(i);
            String originalImageName = originalImageNames.get(i);

            // 첨부파일 있으면, 기존 파일 수정 또는 신규 파일 등록
            if(!itemImageFile.isEmpty()) itemImageService.updateItemImage(itemImage, itemImageFile);
            // 기존 등록 파일 삭제만 눌렀으면, 기존 파일 삭제
            else if(!StringUtils.hasText(originalImageName)
                    && StringUtils.hasText(itemImage.getOriginalImageName())) itemImageService.deleteItemImage(itemImage);
        }
    }
}
