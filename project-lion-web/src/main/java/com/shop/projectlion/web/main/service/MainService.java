package com.shop.projectlion.web.main.service;

import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.item.service.ItemService;
import com.shop.projectlion.web.main.dto.ItemSearchDto;
import com.shop.projectlion.web.main.dto.MainItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MainService {

    private final ItemService itemService;

    public Page<MainItemDto> getMainItemDtoPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        Page<Item> results = itemService.searchMainItemPage(itemSearchDto.getSearchQuery(), pageable);

        List<MainItemDto> mainItemDtos = results.map(MainItemDto::of)
                .stream()
                .collect(Collectors.toList());

        return new PageImpl<>(mainItemDtos, pageable, results.getTotalElements());
    }
}
