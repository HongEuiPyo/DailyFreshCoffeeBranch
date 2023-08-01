package com.example.dailyFreshCoffeeBranch.api;

import com.example.dailyFreshCoffeeBranch.annotation.LoginUser;
import com.example.dailyFreshCoffeeBranch.dto.MemberPointUpDto;
import com.example.dailyFreshCoffeeBranch.dto.UserInfoDto;
import com.example.dailyFreshCoffeeBranch.service.PaymentService;
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
public class PaymentApiController {

    private final PaymentService paymentService;


    /**
     * 포인트 충전
     * @param memberPointUpDto
     * @param userInfoDto
     * @return
     */
    @PostMapping("/payment/addPoint")
    public ResponseEntity<Map<String, String>> addPoint(
            @Valid @RequestBody MemberPointUpDto memberPointUpDto,
            BindingResult result,
            @LoginUser UserInfoDto userInfoDto
    ) {
        Map<String, String> resultMap = new HashMap<>();

        if (result.hasErrors()) {

            for (FieldError error : result.getFieldErrors()) {
                resultMap.put(error.getField(), error.getDefaultMessage());
            }

            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }

        paymentService.addPoint(userInfoDto.getEmail(), memberPointUpDto);

        resultMap.put("msg", "'포인트 충전'을 완료하였습니다.");

        return ResponseEntity.ok().body(resultMap);
    }

}