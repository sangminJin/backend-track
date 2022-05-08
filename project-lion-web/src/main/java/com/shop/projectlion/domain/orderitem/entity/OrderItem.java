package com.shop.projectlion.domain.orderitem.entity;

import com.shop.projectlion.domain.base.BaseEntity;
import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.order.entity.Orders;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @Column(nullable = false)
    private Integer count;

    @Column(name = "order_price", nullable = false)
    private Integer orderPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    @Builder
    public OrderItem(Integer count, Integer orderPrice, Item item) {
        this.count = count;
        this.orderPrice = orderPrice;
        this.item = item;
    }

    public static OrderItem createOrderItem(Item item, int count) {
        OrderItem orderItem = OrderItem.builder()
                .count(count)
                .orderPrice(item.getPrice())
                .item(item)
                .build();

        item.minusStock(count);

        return orderItem;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public int getTotalPrice() {
        return orderPrice * count;
    }

    public void cancel() {
        this.getItem().plusStock(count);
    }
}
