package com.shop.projectlion.web.login.controller;

import com.shop.projectlion.domain.member.service.MemberService;
import com.shop.projectlion.global.error.exception.BusinessException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import com.shop.projectlion.web.login.dto.MemberRegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm(@RequestParam(required = false) boolean isError,
                            Model model) {
        if(isError) model.addAttribute("loginError", ErrorCode.LOGIN_ERROR.getMessage());
        return "login/loginForm";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("memberRegisterDto", new MemberRegisterDto());
        return "login/registerform";
    }

    @PostMapping("/register")
    public String register(@Valid MemberRegisterDto memberRegisterDto,
                           BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "login/registerform";

        try {
            memberService.saveMember(memberRegisterDto);
        } catch (BusinessException e) {
            e.printStackTrace();
            bindingResult.reject("validationError", e.getMessage());
            return "login/registerform";
        }
        return "redirect:/";
    }

}
