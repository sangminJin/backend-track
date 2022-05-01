package com.shop.projectlion.domain.item.service;

import com.shop.projectlion.domain.delivery.entity.Delivery;
import com.shop.projectlion.domain.delivery.repository.DeliveryRepository;
import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.item.entity.ItemImage;
import com.shop.projectlion.domain.item.repository.ItemRepository;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.global.error.exception.BusinessException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import com.shop.projectlion.web.adminitem.dto.InsertItemDto;
import com.shop.projectlion.web.adminitem.dto.UpdateItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final DeliveryRepository deliveryRepository;
    private final ItemImageService itemImageService;

    @Transactional
    public void saveItem(InsertItemDto insertItemDto, Member member) throws Exception{
        Delivery delivery = deliveryRepository.findById(insertItemDto.getDeliveryId()).orElse(null);
        Item saveItem = itemRepository.save(insertItemDto.toEntity(member, delivery));

        List<MultipartFile> itemImageFiles = insertItemDto.getItemImageFiles();
        for(int i=0; i<itemImageFiles.size(); i++) {
            MultipartFile multipartFile = itemImageFiles.get(i);

            ItemImage itemImage = ItemImage.builder()
                    .isRepImage(false)
                    .originalImageName(multipartFile.getOriginalFilename())
                    .build();

            itemImage.updateItem(saveItem);
            if (i == 0) itemImage.updateRepImage(true);

            itemImageService.saveItemImg(itemImage, multipartFile);
        }
    }

    public UpdateItemDto getItemDetail(Long itemId) {
        Item findItem = itemRepository.findItemDetailsById(itemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_ITEM));

        UpdateItemDto updateItemDto = new UpdateItemDto(findItem);

        return updateItemDto;
    }

    @Transactional
    public void updateItem(UpdateItemDto updateItemDto, Member loginMember) throws Exception{
        Item findItem = itemRepository.findById(updateItemDto.getItemId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_ITEM));
        Delivery delivery = deliveryRepository.findById(updateItemDto.getDeliveryId()).orElse(null);

        findItem.modifyItemInfo(updateItemDto, delivery, loginMember);

        List<ItemImage> findItemImages = findItem.getItemImages();  // 기존에 저장되있던 이미지 리스트
        List<MultipartFile> itemImageFiles = updateItemDto.getItemImageFiles();
        List<String> originalImageNames = updateItemDto.getOriginalImageNames();

        for(int i=0; i<5; i++) {
            ItemImage findItemImage = findItemImages.get(i);
            MultipartFile multipartFile = itemImageFiles.get(i);
            String originalImageName = originalImageNames.get(i);

            // originalImageName 값이 있다 => 이미지 변경 없음
            if(!originalImageName.isBlank()) continue;

            // 기존 이미지도 없고, 새로 첨부한 이미지도 없다 => 이미지 변경 없음
            if(findItemImage.getImageName() == null && multipartFile.isEmpty()) continue;

            // 나머지 경우는 사진 변경이 있다 => 기존 이미지 삭제 후 새로 삽입
            itemImageService.deleteItemImg(findItemImage);

            ItemImage updateItemImage = ItemImage.builder()
                    .isRepImage(false)
                    .originalImageName(multipartFile.getOriginalFilename())
                    .build();
            updateItemImage.updateItem(findItem);
            if (i == 0) updateItemImage.updateRepImage(true);

            itemImageService.saveItemImg(updateItemImage, multipartFile);
        }
    }
}
