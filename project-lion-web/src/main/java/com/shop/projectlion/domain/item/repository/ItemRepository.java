package com.shop.projectlion.domain.item.repository;

import com.shop.projectlion.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i " +
            "LEFT JOIN FETCH i.delivery " +
            "LEFT JOIN FETCH i.itemImages " +
            "WHERE i.id = :itemId")
    Optional<Item> findItemDetailsById(@Param("itemId") Long itemId);
}
