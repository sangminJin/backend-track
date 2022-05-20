package com.shop.projectlion.domain.order.repository;

import com.shop.projectlion.domain.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long>, OrderRepositoryCustom {
}
