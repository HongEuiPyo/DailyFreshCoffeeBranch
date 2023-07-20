package com.example.dailyFreshCoffeeBranch.controller;

import com.example.dailyFreshCoffeeBranch.annotation.LoginUserInfo;
import com.example.dailyFreshCoffeeBranch.dto.CartDto;
import com.example.dailyFreshCoffeeBranch.security.oauth2.UserInfo;
import com.example.dailyFreshCoffeeBranch.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class CartController {

    private final CartItemService cartService;

    /**
     * 장바구니 조회
     * @param userInfo
     * @param model
     * @return
     */
    @GetMapping(value = "/cart/items")
    public String cartItemList(@LoginUserInfo UserInfo userInfo, Model model) {
        CartDto cartDto = cartService.getCartItemList(userInfo.getEmail());
        model.addAttribute("cartDto", cartDto);
        return "cart/cartList";
    }



}