package com.shop.projectlion.domain.orderitem.repository;

import com.shop.projectlion.domain.orderitem.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
