package com.shop.projectlion.domain.item.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import com.shop.projectlion.domain.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static com.shop.projectlion.domain.item.entity.QItem.item;
import static org.springframework.util.StringUtils.hasText;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public ItemRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Item> findItemPage(String searchQuery, ItemSellStatus itemSellStatus, Pageable pageable) {
        List<Item> contents = queryFactory
                .selectFrom(item)
                .where(ExpressionUtils.or(
                        itemNameLike(searchQuery),
                        itemDetailLike(searchQuery)),
                        itemSellStatusEq(itemSellStatus)
                )
                .orderBy(item.createTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = queryFactory
                .select(item.count())
                .from(item)
                .where(ExpressionUtils.or(
                        itemNameLike(searchQuery),
                        itemDetailLike(searchQuery)),
                        itemSellStatusEq(itemSellStatus)
                )
                .fetchOne();

        return new PageImpl<>(contents, pageable, totalCount);
    }

    private BooleanExpression itemNameLike(String searchQuery) {
        return hasText(searchQuery) ? item.itemName.like("%" + searchQuery + "%") : null;
    }

    private BooleanExpression itemDetailLike(String searchQuery) {
        return hasText(searchQuery) ? item.itemDetail.like("%" + searchQuery + "%") : null;
    }

    private BooleanExpression itemSellStatusEq(ItemSellStatus itemSellStatus) {
        return itemSellStatus != null ? item.itemSellStatus.eq(itemSellStatus) : null;
    }
}
