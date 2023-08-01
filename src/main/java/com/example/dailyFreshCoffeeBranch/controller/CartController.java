package com.example.dailyFreshCoffeeBranch.controller;

import com.example.dailyFreshCoffeeBranch.annotation.LoginUser;
import com.example.dailyFreshCoffeeBranch.dto.CartDto;
import com.example.dailyFreshCoffeeBranch.dto.UserInfoDto;
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
     *
     * @return
     */
    @GetMapping(value = "/cart/items")
    public String cartItemList() {
        return "cart/cartList";
    }

    /**
     * 장바구니 조회 처리
     * @param userInfoDto
     * @param model
     * @return
     */
    @GetMapping(value = "/cart/itemsAjax")
    public String cartItemListProc(@LoginUser UserInfoDto userInfoDto, Model model) {
        CartDto cartDto = cartService.getCartItemList(userInfoDto.getEmail());
        model.addAttribute("cartDto", cartDto);
        return "cart/cartListAjax";
    }

}