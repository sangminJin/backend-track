package com.shop.projectlion.domain.item.service;

import com.shop.projectlion.domain.delivery.repository.DeliveryRepository;
import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.item.repository.ItemRepository;
import com.shop.projectlion.domain.itemimage.service.ItemImageService;
import com.shop.projectlion.global.error.exception.BusinessException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final DeliveryRepository deliveryRepository;
    private final ItemImageService itemImageService;

    public Item registerItem(Item item) {
        return itemRepository.save(item);
    }

    public Item findByItemId(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_ITEM));
    }

    public Item findItemDetailsById(Long itemId) {
        return itemRepository.findItemDetailsById(itemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_ITEM));
    }

    @Transactional
    public Item updateItem(Long itemId, Item updateItem) {
        Item findItem = findByItemId(itemId);
        findItem.modifyItemInfo(updateItem);
        return findItem;
    }
}
