package com.shop.projectlion.api.item.service;

import com.shop.projectlion.api.item.dto.ItemDtlResponseDto;
import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemApiService {

    private final ItemService itemService;

    public ItemDtlResponseDto getItemDetail(Long itemId) {
        Item findItem = itemService.findItemDetailsById(itemId);
        return ItemDtlResponseDto.of(findItem);
    }
}
