package com.shop.projectlion.api.item.controller;

import com.shop.projectlion.api.item.dto.ItemDtlResponseDto;
import com.shop.projectlion.api.item.dto.ItemModifyRequestDto;
import com.shop.projectlion.api.item.dto.ItemModifyResponseDto;
import com.shop.projectlion.api.item.service.ItemApiService;
import com.shop.projectlion.global.error.exception.ErrorCode;
import com.shop.projectlion.global.error.exception.InvalidValueException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

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

    @PatchMapping(value = "/items/{itemId}")
    public ResponseEntity<ItemModifyResponseDto> modifyItem(@PathVariable Long itemId,
                                                            @Valid @RequestBody ItemModifyRequestDto itemModifyRequestDto) {
        if(!Objects.equals(itemId, itemModifyRequestDto.getItemId())) throw new InvalidValueException(ErrorCode.INVALID_ITEM_ID);

        ItemModifyResponseDto itemModifyResponseDto = itemApiService.modifyItemInfo(itemModifyRequestDto);
        return ResponseEntity.ok(itemModifyResponseDto);
    }
}
