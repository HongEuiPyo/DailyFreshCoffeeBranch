package com.example.dailyFreshCoffeeBranch.api;

import com.example.dailyFreshCoffeeBranch.com.MySecurityUtils;
import com.example.dailyFreshCoffeeBranch.dto.CartItemDto;
import com.example.dailyFreshCoffeeBranch.dto.CartItemUpdateDto;
import com.example.dailyFreshCoffeeBranch.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class CartApiController {

    private final CartService cartService;


    /**
     * 장바구니 상품 등록
     * @param id
     * @param cartItemDto
     * @param bindingResult
     * @param principal
     * @return
     */
    @PostMapping(value = "/cart/items/{id}/add")
    public ResponseEntity<Map<String, String>> addToCart(
            @PathVariable Long id,
            @Valid @RequestBody CartItemDto cartItemDto,
            BindingResult bindingResult,
            Principal principal,
            HttpSession session
    ) {
        Map<String, String> resultMap = new HashMap<>();

        if (bindingResult.hasErrors()) {

            for (FieldError error : bindingResult.getFieldErrors()) {
                resultMap.put(error.getField(), error.getDefaultMessage());
            }

            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }

        String memberEmail = MySecurityUtils.getTrueMemberEmail(principal, session);

        cartService.addToCart(memberEmail, id, cartItemDto);

        resultMap.put("msg", "장바구니에 추가 완료하였습니다.");

        return ResponseEntity.ok().body(resultMap);
    }

    /**
     * 장바구니 상품 수정
     * @param id
     * @param cartItemUpdateDto
     * @param principal
     * @return
     */
    @PostMapping(value = "/cart/items/{id}/update")
    public ResponseEntity<Map<String, String>> updateCartItem(
            @PathVariable Long id,
            @Valid @RequestBody CartItemUpdateDto cartItemUpdateDto,
            BindingResult result,
            Principal principal,
            HttpSession session
    ) {
        Map<String, String> resultMap = new HashMap<>();

        if (result.hasErrors()) {

            for (FieldError error : result.getFieldErrors()) {
                resultMap.put(error.getField(), error.getDefaultMessage());
            }

            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }

        String memberEmail = MySecurityUtils.getTrueMemberEmail(principal, session);

        cartService.updateCartItem(memberEmail, id, cartItemUpdateDto);

        resultMap.put("msg", "장바구니 상품 수정을 완료하였습니다.");

        return ResponseEntity.ok().body(resultMap);
    }

    /**
     * 장바구니 상품 삭제
     * @param id
     * @param principal
     * @return
     */
    @DeleteMapping("/cart/items/{id}/delete")
    public ResponseEntity<Map<String, String>> deleteCartItem(@PathVariable Long id, Principal principal, HttpSession session) {
        String memberEmail = MySecurityUtils.getTrueMemberEmail(principal, session);

        Map<String, String> resultMap = new HashMap<>();

        cartService.deleteCartItem(id, memberEmail);

        resultMap.put("msg", "장바구니 상품을 삭제 하였습니다.");

        return ResponseEntity.ok(resultMap);
    }

}