package com.shop.projectlion.web.adminitem.controller;

import com.shop.projectlion.domain.delivery.service.DeliveryService;
import com.shop.projectlion.domain.item.service.ItemService;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.global.config.security.LoginMember;
import com.shop.projectlion.global.error.exception.BusinessException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import com.shop.projectlion.web.adminitem.dto.DeliveryDto;
import com.shop.projectlion.web.adminitem.dto.InsertItemDto;
import com.shop.projectlion.web.adminitem.dto.UpdateItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/items")
public class AdminItemController {

    private final ItemService itemService;
    private final DeliveryService deliveryService;

    @GetMapping("/new")
    public String itemForm(Model model, @LoginMember Member loginMember) {
        List<DeliveryDto> deliveryDtos = deliveryService.getDeliveryInfo(loginMember);
        model.addAttribute("deliveryDtos", deliveryDtos);
        model.addAttribute("insertItemDto", new InsertItemDto());

        return "adminitem/registeritemform";
    }

    @PostMapping("/new")
    public String itemRegister(@Valid InsertItemDto insertItemDto,
                               BindingResult bindingResult,
                               Model model,
                               @LoginMember Member loginMember) {
        List<DeliveryDto> deliveryDtos = deliveryService.getDeliveryInfo(loginMember);
        model.addAttribute("deliveryDtos", deliveryDtos);

        if(!insertItemDto.hasRepImage()) bindingResult.reject("notExistRepImg", ErrorCode.NOT_EXIST_REP_IMG.getMessage());
        if(bindingResult.hasErrors()) return "adminitem/registeritemform";

        Long savedItemId = null;
        try {
            savedItemId = itemService.saveItem(insertItemDto, loginMember);
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("itemRegisterError", ErrorCode.ITEM_REGISTER_ERROR.getMessage());
            return "adminitem/registeritemform";
        }

        return "redirect:/admin/items/" + savedItemId;
    }

    @GetMapping("/{itemId}")
    public String itemEditForm(@PathVariable Long itemId,
                               Model model,
                               @RequestParam(required = false) boolean isError,
                               @LoginMember Member loginMember) {
        if(isError) model.addAttribute("errorMsg", ErrorCode.ITEM_UPDATE_FILED_ERROR.getMessage());

        List<DeliveryDto> deliveryDtos = deliveryService.getDeliveryInfo(loginMember);
        model.addAttribute("deliveryDtos", deliveryDtos);

        try {
            UpdateItemDto updateItemDto = itemService.getItemDetail(itemId);
            model.addAttribute("updateItemDto", updateItemDto);
        } catch (BusinessException e) {
            e.printStackTrace();
            model.addAttribute("errorMsg", e.getMessage());
            return "error/error";
        }

        return "adminitem/updateitemform";
    }

    @PostMapping("/{itemId}")
    public String itemEdit(@Valid UpdateItemDto updateItemDto,
                           BindingResult bindingResult,
                           Model model,
                           @LoginMember Member loginMember) {
        if(bindingResult.hasErrors() || !updateItemDto.hasRepImage()) {
            // redirect시에 bindingRsult 객체를 넘기는게 잘 안되어 아래와 같이 처리..
            return "redirect:/admin/items/" + updateItemDto.getItemId() + "?isError=true";
        }

        try {
            itemService.updateItem(updateItemDto, loginMember);
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("itemUpdateError", ErrorCode.ITEM_UPDATE_ERROR.getMessage());
            return "error/error";
        }

        return "redirect:/admin/items/" + updateItemDto.getItemId();
    }
}