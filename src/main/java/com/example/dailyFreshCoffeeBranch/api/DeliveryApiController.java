package com.example.dailyFreshCoffeeBranch.api;

import com.example.dailyFreshCoffeeBranch.annotation.LoginUserInfo;
import com.example.dailyFreshCoffeeBranch.dto.PaymentDto;
import com.example.dailyFreshCoffeeBranch.security.oauth2.UserInfo;
import com.example.dailyFreshCoffeeBranch.service.DeliveryApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class DeliveryApiController {

    private final DeliveryApiService deliveryApiService;

    /**
     * 장바구니 상품 배송 확정
     *
     * @param paymentDto
     * @param userInfo
     * @return
     */
    @PostMapping("/delivery/deliverCartItems")
    public ResponseEntity<Map<String, String>> deliverCartItems(
            @Valid @RequestBody PaymentDto paymentDto,
            BindingResult result,
            @LoginUserInfo UserInfo userInfo
    ) {
        Map<String, String> resultMap = new HashMap<>();

        if (result.hasErrors()) {

            for (FieldError error : result.getFieldErrors()) {
                resultMap.put(error.getField(), error.getDefaultMessage());
            }

            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }

        deliveryApiService.deliverCartItems(userInfo.getEmail(), paymentDto);

        resultMap.put("msg", "'배송'을 완료하였습니다.");

        return ResponseEntity.ok().body(resultMap);
    }

}