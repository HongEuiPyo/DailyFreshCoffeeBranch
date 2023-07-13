package com.example.dailyFreshCoffeeBranch.controller;

import com.example.dailyFreshCoffeeBranch.security.SessionUser;
import com.example.dailyFreshCoffeeBranch.service.CartService;
import com.example.dailyFreshCoffeeBranch.dto.CartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class CartController {

    private final CartService cartService;

    /**
     * 장바구니 조회
     * @param principal
     * @param model
     * @return
     */
    @GetMapping(value = "/cart/items")
    public String cartItemList(Principal principal, HttpSession session, Model model) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        String memberEmail = "";
        if (user == null) {
            memberEmail = principal.getName();
        } else {
            memberEmail = user.getEmail();
        }
        CartDto cartDto = cartService.getCartItemList(memberEmail);
        model.addAttribute("cartDto", cartDto);
        return "cart/cartList";
    }



}