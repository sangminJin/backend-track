package com.shop.projectlion.web.adminitem.controller;

import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.global.config.security.LoginMember;
import com.shop.projectlion.global.error.exception.BusinessException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import com.shop.projectlion.web.adminitem.dto.DeliveryDto;
import com.shop.projectlion.web.adminitem.dto.InsertItemDto;
import com.shop.projectlion.web.adminitem.dto.UpdateItemDto;
import com.shop.projectlion.web.adminitem.service.AdminItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/items")
public class AdminItemController {

    private final AdminItemService adminItemService;

    @ModelAttribute("deliveryDtos")
    public List<DeliveryDto> deliveryDtos(@LoginMember Member loginMember) {
        return adminItemService.getMemberDeliveryDtos(loginMember);
    }

    @GetMapping("/new")
    public String itemForm(Model model, @LoginMember Member loginMember) {
        model.addAttribute("insertItemDto", new InsertItemDto());
        return "adminitem/registeritemform";
    }

    @PostMapping("/new")
    public String itemRegister(@Valid InsertItemDto insertItemDto,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               @LoginMember Member loginMember) {
        if(!insertItemDto.hasRepImage()) bindingResult.reject("notExistRepImg", ErrorCode.NOT_EXIST_REP_IMG.getMessage());
        if(bindingResult.hasErrors()) return "adminitem/registeritemform";

        try {
            Long savedItemId = adminItemService.registerItem(insertItemDto, loginMember);
            redirectAttributes.addAttribute("itemId", savedItemId);
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("itemRegisterError", ErrorCode.ITEM_REGISTER_ERROR.getMessage());
            return "adminitem/registeritemform";
        }

        return "redirect:/admin/items/{itemId}";
    }

    @GetMapping("/{itemId}")
    public String itemEditForm(@PathVariable Long itemId, Model model) {
        try {
            UpdateItemDto updateItemDto = adminItemService.getUpdateItemDto(itemId);
            model.addAttribute("updateItemDto", updateItemDto);
        } catch (BusinessException e) {
            e.printStackTrace();
            model.addAttribute("errorMsg", e.getMessage());
            return "error/error";
        }
        return "adminitem/updateitemform";
    }

    @PostMapping("/{itemId}")
    public String itemEdit(@PathVariable Long itemId,
                           @Valid UpdateItemDto updateItemDto,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if(!updateItemDto.hasRepImage()) bindingResult.reject("notExistRepImg", ErrorCode.NOT_EXIST_REP_IMG.getMessage());
        if(bindingResult.hasErrors()) {
            List<UpdateItemDto.ItemImageDto> itemImageDtos = adminItemService.getItemImageDto(itemId);
            updateItemDto.setItemImageDtos(itemImageDtos);
            return "adminitem/updateitemform";
        }

        try {
            adminItemService.updateItem(updateItemDto);
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("itemUpdateError", ErrorCode.ITEM_UPDATE_ERROR.getMessage());
            return "error/error";
        }

        redirectAttributes.addAttribute("itemId", itemId);
        return "redirect:/admin/items/{itemId}";
    }
}