package com.shop.projectlion.domain.item.repository;

import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import com.shop.projectlion.domain.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<Item> findItemPage(String searchQuery, ItemSellStatus itemSellStatus, Pageable pageable);
}
