package com.shop.projectlion.domain.item.entity;

import com.shop.projectlion.domain.base.BaseEntity;
import com.shop.projectlion.domain.delivery.entity.Delivery;
import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.web.adminitem.dto.UpdateItemDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(name = "item_detail", columnDefinition = "longtext")
    private String itemDetail;

    @Column(name = "item_name", length = 100)
    private String itemName;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_sell_status")
    private ItemSellStatus itemSellStatus;

    @Column
    private Integer price;

    @Column(name = "stock_number")
    private Integer stockNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @OneToMany(mappedBy = "item")
    private List<ItemImage> itemImages = new ArrayList<>();

    @Builder
    public Item(String itemDetail,
                String itemName,
                ItemSellStatus itemSellStatus,
                Integer price,
                Integer stockNumber,
                Member member,
                Delivery delivery) {
        this.itemDetail = itemDetail;
        this.itemName = itemName;
        this.itemSellStatus = itemSellStatus;
        this.price = price;
        this.stockNumber = stockNumber;
        this.member = member;
        this.delivery = delivery;
    }

    public void modifyItemInfo(UpdateItemDto updateItemDto,
                               Delivery delivery,
                               Member member) {
        this.itemDetail = updateItemDto.getItemDetail();
        this.itemName = updateItemDto.getItemName();
        this.itemSellStatus = updateItemDto.getItemSellStatus();
        this.price = updateItemDto.getPrice();
        this.stockNumber = updateItemDto.getStockNumber();
        this.member = member;
        this.delivery = delivery;
    }
}
