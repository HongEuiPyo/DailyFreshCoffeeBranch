package com.example.dailyFreshCoffeeBranch.api;

import com.example.dailyFreshCoffeeBranch.annotation.LoginUser;
import com.example.dailyFreshCoffeeBranch.dto.CartItemFormDto;
import com.example.dailyFreshCoffeeBranch.dto.CartItemUpdateFormDto;
import com.example.dailyFreshCoffeeBranch.dto.LoginUserDto;
import com.example.dailyFreshCoffeeBranch.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class CartItemApiController {

    private final CartItemService cartService;


    /**
     * 장바구니 상품 등록
     * @param id
     * @param cartItemFormDto
     * @param bindingResult
     * @param loginUserDto
     * @return
     */
    @PostMapping(value = "/cart/items/{id}/add")
    public ResponseEntity<Map<String, String>> addToCart
    (
            @PathVariable Long id,
            @Valid @RequestBody CartItemFormDto cartItemFormDto,
            BindingResult bindingResult,
            @LoginUser LoginUserDto loginUserDto
    ) {
        Map<String, String> resultMap = new HashMap<>();

        if (bindingResult.hasErrors()) {

            for (FieldError error : bindingResult.getFieldErrors()) {
                resultMap.put(error.getField(), error.getDefaultMessage());
            }

            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }

        cartService.addToCart(loginUserDto.getEmail(), id, cartItemFormDto);

        resultMap.put("msg", "장바구니에 추가 완료하였습니다.");

        return ResponseEntity.ok().body(resultMap);
    }

    /**
     * 장바구니 상품 수정
     * @param id
     * @param cartItemUpdateFormDto
     * @param loginUserDto
     * @return
     */
    @PostMapping(value = "/cart/items/{id}/update")
    public ResponseEntity<Map<String, String>> updateCartItem
    (
            @PathVariable Long id,
            @Valid @RequestBody CartItemUpdateFormDto cartItemUpdateFormDto,
            BindingResult result,
            @LoginUser LoginUserDto loginUserDto
    ) {
        Map<String, String> resultMap = new HashMap<>();

        if (result.hasErrors()) {

            for (FieldError error : result.getFieldErrors()) {
                resultMap.put(error.getField(), error.getDefaultMessage());
            }

            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }

        cartService.updateCartItem(loginUserDto.getEmail(), id, cartItemUpdateFormDto);

        resultMap.put("msg", "장바구니 상품 수정을 완료하였습니다.");

        return ResponseEntity.ok().body(resultMap);
    }

    /**
     * 장바구니 상품 삭제
     * @param id
     * @param loginUserDto
     * @return
     */
    @DeleteMapping("/cart/items/{id}/delete")
    public ResponseEntity<Map<String, String>> deleteCartItem(@PathVariable Long id, @LoginUser LoginUserDto loginUserDto) {
        Map<String, String> resultMap = new HashMap<>();

        cartService.deleteCartItem(id, loginUserDto.getEmail());

        resultMap.put("msg", "장바구니 상품을 삭제 하였습니다.");

        return ResponseEntity.ok(resultMap);
    }

}