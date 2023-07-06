package com.example.smallpeopleblog.api;

import com.example.smallpeopleblog.dto.CartItemDto;
import com.example.smallpeopleblog.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> addToCart(
            @PathVariable Long id,
            @Valid @RequestBody CartItemDto cartItemDto,
            BindingResult bindingResult,
            Principal principal
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }

            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        }

        CartItemDto savedCartItemDto = cartService.addToCart(principal.getName(), id, cartItemDto);

        return ResponseEntity.ok().body(savedCartItemDto);
    }

    /**
     * 장바구니 상품 수정
     * @param id
     * @param cartItemDto
     * @param principal
     * @return
     */
    @PostMapping(value = "/cart/items/{id}/update")
    public ResponseEntity<?> updateCartItem(
            @PathVariable Long id,
            @RequestBody CartItemDto cartItemDto,
            Principal principal
    ) {
        CartItemDto upCartItem = cartService.updateCartItem(principal.getName(), id, cartItemDto);
        return ResponseEntity.ok().body(upCartItem);
    }

    /**
     * 장바구니 상품 삭제
     * @param id
     * @param principal
     * @return
     */
    @DeleteMapping("/cart/items/{id}/delete")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long id, Principal principal) {
        cartService.deleteCartItem(id, principal.getName());
        return ResponseEntity.ok("장바구니 상품이 성공적으로 삭제처리 되었습니다.");
    }

}