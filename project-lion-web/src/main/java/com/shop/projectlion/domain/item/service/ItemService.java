package com.shop.projectlion.domain.item.service;

import com.shop.projectlion.domain.delivery.entity.Delivery;
import com.shop.projectlion.domain.delivery.repository.DeliveryRepository;
import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.item.entity.ItemImage;
import com.shop.projectlion.domain.item.repository.ItemImageRepository;
import com.shop.projectlion.domain.item.repository.ItemRepository;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.infra.FileService;
import com.shop.projectlion.web.adminitem.dto.InsertItemDto;
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
            String originalFilename = multipartFile.getOriginalFilename();

            ItemImage itemImage = ItemImage.builder()
                    .isRepImage(false)
                    .originalImageName(originalFilename)
                    .item(saveItem)
                    .build();

            if (i == 0) itemImage.updateRepImage(true);

            itemImageService.saveItemImg(itemImage, multipartFile);
        }

    }
}
