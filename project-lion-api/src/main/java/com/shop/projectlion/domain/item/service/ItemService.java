package com.shop.projectlion.domain.item.service;

import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.item.repository.ItemRepository;
import com.shop.projectlion.global.error.exception.BusinessException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public Item registerItem(Item item) {
        return itemRepository.save(item);
    }

    public Item findByItemId(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ITEM_NOT_EXISTS));
    }

    public Item findItemDetailsById(Long itemId) {
        return itemRepository.findItemDetailsById(itemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ITEM_NOT_EXISTS));
    }

    @Transactional
    public Item updateItem(Long itemId, Item updateItem) {
        Item findItem = findByItemId(itemId);
        findItem.modifyItemInfo(updateItem);
        return findItem;
    }

    public Page<Item> searchMainItemPage(String searchQuery, Pageable pageable) {
        return itemRepository.findItemPage(searchQuery, ItemSellStatus.SELL, pageable);
    }
}
