package com.shop.projectlion.web.orderhist.controller;

import com.shop.projectlion.global.config.paging.PageConfig;
import com.shop.projectlion.web.itemorder.service.ItemOrderService;
import com.shop.projectlion.web.orderhist.dto.OrderHistDto;
import com.shop.projectlion.web.orderhist.service.OrderHistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orderhist")
public class OrderHistController {

    private final OrderHistService orderHistService;
    private final ItemOrderService itemOrderService;

    @GetMapping
    public String orderHist(Optional<Integer> page, Model model, Principal principal) {
        Pageable pageable = PageRequest.of(page.orElse(0), PageConfig.ITEM_NUM_PER_PAGE);
        Page<OrderHistDto> pageOrderHistDtos = orderHistService.getOrderHistDtoPage(pageable, principal.getName());

        model.addAttribute("orders", pageOrderHistDtos);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", PageConfig.MAX_PAGE_NUM_PER_PAGE); // 메인페이지에 노출되는 최대 페이지 갯수

        return "orderhist/orderhist";
    }

    @PostMapping(value = "/{orderId}/cancel")
    public @ResponseBody ResponseEntity<String> itemOrderCancel(@PathVariable Long orderId, Principal principal) {

        if(!itemOrderService.hasCancelAuth(orderId, principal.getName())) {
            return new ResponseEntity<>("취소 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        try {
            itemOrderService.cancelOrderItem(orderId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("성공적으로 취소되었습니다.", HttpStatus.OK);
    }
}
