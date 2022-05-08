package com.shop.projectlion.web.itemdtl.service;

import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.item.service.ItemService;
import com.shop.projectlion.web.itemdtl.dto.ItemDtlDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemDtlService {

    private final ItemService itemService;

    public ItemDtlDto getItemDtlDto(Long itemId) {
        Item findItem = itemService.findItemDetailsById(itemId);
        return ItemDtlDto.of(findItem);
    }
}
