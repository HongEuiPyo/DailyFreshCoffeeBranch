package com.example.dailyFreshCoffeeBranch.controller;

import com.example.dailyFreshCoffeeBranch.annotation.LoginUser;
import com.example.dailyFreshCoffeeBranch.dto.CartResponseDto;
import com.example.dailyFreshCoffeeBranch.dto.LoginUserDto;
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
     * 장바구니 조회 처리
     *
     * @param loginUserDto
     * @param model
     * @return
     */
    @GetMapping(value = "/cart/itemsAjax")
    public String cartItemListProc
    (
            @LoginUser LoginUserDto loginUserDto,
            Model model
    ) {
        CartResponseDto result = cartService.getCartItemList(loginUserDto.getEmail());
        model.addAttribute("result", result);

        return "cart/cartListAjax";
    }

}