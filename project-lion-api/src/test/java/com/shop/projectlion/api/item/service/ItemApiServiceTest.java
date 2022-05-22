package com.shop.projectlion.api.item.service;

import com.shop.projectlion.api.item.dto.ItemDtlResponseDto;
import com.shop.projectlion.api.item.dto.ItemModifyRequestDto;
import com.shop.projectlion.api.item.dto.ItemModifyResponseDto;
import com.shop.projectlion.domain.delivery.entity.Delivery;
import com.shop.projectlion.domain.delivery.service.DeliveryService;
import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.item.service.ItemService;
import com.shop.projectlion.domain.member.constant.MemberType;
import com.shop.projectlion.domain.member.constant.Role;
import com.shop.projectlion.domain.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DisplayName("ItemApiService 테스트")
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ItemApiServiceTest {

    @InjectMocks
    ItemApiService itemApiService;

    @Mock
    ItemService itemService;

    @Mock
    DeliveryService deliveryService;

    @Test
    @DisplayName("item 조회 테스트")
    void getItemDetail() {
        // given
        Member member = Member.builder()
                .memberName("테스트")
                .memberType(MemberType.KAKAO)
                .password("")
                .email("admin@lionshop.com")
                .role(Role.ADMIN)
                .build();

        Delivery delivery = Delivery.builder()
                .deliveryName("마포구 물류센터")
                .deliveryFee(3000)
                .member(member)
                .build();

        Long itemId = 1L;
        Item item = Item.builder()
                .itemName("테스트 상품명")
                .itemSellStatus(ItemSellStatus.SELL)
                .itemDetail("테스트 상세")
                .price(5000)
                .stockNumber(100)
                .delivery(delivery)
                .build();

        // mocking
        when(itemService.findItemDetailsById(itemId)).thenReturn(item);

        // when
        ItemDtlResponseDto itemDtlResponseDto = itemApiService.getItemDetail(itemId);

        // then
        assertEquals("테스트 상품명", itemDtlResponseDto.getItemName());
        assertEquals("테스트 상세", itemDtlResponseDto.getItemDetail());
        assertEquals(5000, itemDtlResponseDto.getPrice());
        assertEquals(100, itemDtlResponseDto.getStockNumber());
        assertEquals(3000, itemDtlResponseDto.getDeliveryFee());
        assertEquals(ItemSellStatus.SELL, itemDtlResponseDto.getItemSellStatus());
    }

    @Test
    @DisplayName("item 수정 테스트")
    void modifyItemInfo() {
        // given
        Member member = Member.builder()
                .memberName("테스트")
                .memberType(MemberType.KAKAO)
                .password("")
                .email("admin@lionshop.com")
                .role(Role.ADMIN)
                .build();

        Long deliveryId = 1L;
        Delivery delivery = Delivery.builder()
                .deliveryName("마포구 물류센터")
                .deliveryFee(3000)
                .member(member)
                .build();

        Long itemId = 1L;
        Item item = Item.builder()
                .itemName("테스트 상품명")
                .itemSellStatus(ItemSellStatus.SELL)
                .itemDetail("테스트 상세")
                .price(5000)
                .stockNumber(100)
                .delivery(delivery)
                .build();

        ItemModifyRequestDto itemModifyRequestDto = ItemModifyRequestDto.builder()
                .itemId(itemId)
                .itemName("수정 상품명")
                .price(99999)
                .itemDetail("수정 상세")
                .stockNumber(0)
                .itemSellStatus(ItemSellStatus.SOLD_OUT)
                .deliveryId(deliveryId)
                .build();

        // mocking
        when(deliveryService.findByDeliveryId(deliveryId)).thenReturn(delivery);
        when(itemService.findByItemId(itemId)).thenReturn(item);

        // when
        ItemModifyResponseDto itemModifyResponseDto = itemApiService.modifyItemInfo(itemModifyRequestDto);

        // then
        assertEquals("수정 상품명", itemModifyResponseDto.getItemName());
        assertEquals("수정 상세", itemModifyResponseDto.getItemDetail());
        assertEquals(99999, itemModifyResponseDto.getPrice());
        assertEquals(0, itemModifyResponseDto.getStockNumber());
        assertEquals(ItemSellStatus.SOLD_OUT, itemModifyResponseDto.getItemSellStatus());
    }
}