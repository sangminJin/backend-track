package com.shop.projectlion.api.item.service;

import com.shop.projectlion.api.item.dto.ItemDtlResponseDto;
import com.shop.projectlion.api.item.dto.ItemModifyRequestDto;
import com.shop.projectlion.api.item.dto.ItemModifyResponseDto;
import com.shop.projectlion.domain.delivery.entity.Delivery;
import com.shop.projectlion.domain.delivery.service.DeliveryService;
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
    private final DeliveryService deliveryService;

    public ItemDtlResponseDto getItemDetail(Long itemId) {
        Item findItem = itemService.findItemDetailsById(itemId);
        return ItemDtlResponseDto.of(findItem);
    }

    @Transactional
    public ItemModifyResponseDto modifyItemInfo(ItemModifyRequestDto itemModifyRequestDto) {
        Item findItem = itemService.findByItemId(itemModifyRequestDto.getItemId());
        Delivery findDelivery = deliveryService.findByDeliveryId(itemModifyRequestDto.getDeliveryId());
        findItem.modifyItemInfo(itemModifyRequestDto.toEntity(findDelivery));

        return ItemModifyResponseDto.of(findItem);
    }
}
