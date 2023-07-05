package com.example.smallpeopleblog.cart;

import com.example.smallpeopleblog.dto.CartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String cartItemList(Principal principal, Model model) {
        CartDto cartDto = cartService.getCartItemList(principal.getName());
        model.addAttribute("cartDto", cartDto);
        return "cart/cartList";
    }



}