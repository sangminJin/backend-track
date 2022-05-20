package com.shop.projectlion.api.item.controller;

import com.shop.projectlion.api.item.dto.ItemDtlResponseDto;
import com.shop.projectlion.api.item.service.ItemApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/admin")
@RestController
public class ItemApiController {

    private final ItemApiService itemApiService;

    @GetMapping(value = "/items/{itemId}")
    public ResponseEntity<ItemDtlResponseDto> getItemDtl(@PathVariable Long itemId) {
        ItemDtlResponseDto itemDtlResponseDto = itemApiService.getItemDetail(itemId);
        return ResponseEntity.ok(itemDtlResponseDto);
    }

}
