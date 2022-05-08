package com.shop.projectlion.web.itemdtl.controller;

import com.shop.projectlion.web.itemdtl.dto.ItemDtlDto;
import com.shop.projectlion.web.itemorder.dto.ItemOrderDto;
import com.shop.projectlion.web.itemdtl.service.ItemDtlService;
import com.shop.projectlion.web.itemorder.service.ItemOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/itemdtl")
public class ItemDtlController {

    private final ItemDtlService itemDtlService;
    private final ItemOrderService itemOrderService;

    @GetMapping(value = "/{itemId}")
    public String itemDtl(Model model, @PathVariable("itemId") Long itemId){
        ItemDtlDto itemDtlDto = itemDtlService.getItemDtlDto(itemId);
        model.addAttribute("item", itemDtlDto);
        return "itemdtl/itemdtl";
    }

    @PostMapping(value = "/order")
    public @ResponseBody ResponseEntity<String> itemOrder(@RequestBody @Valid ItemOrderDto itemOrderDto,
                                                          BindingResult bindingResult,
                                                          Principal principal){
        if(bindingResult.hasErrors()) {
            StringBuffer sb = new StringBuffer();
            bindingResult.getFieldErrors().forEach(fieldError -> sb.append(fieldError.getDefaultMessage()));
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        try {
            itemOrderService.orderItem(itemOrderDto, principal.getName());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("성공적으로 주문되었습니다.", HttpStatus.OK);
    }
}
