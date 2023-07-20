package com.example.dailyFreshCoffeeBranch.controller;

import com.example.dailyFreshCoffeeBranch.annotation.LoginUserInfo;
import com.example.dailyFreshCoffeeBranch.dto.PaymentDto;
import com.example.dailyFreshCoffeeBranch.security.oauth2.UserInfo;
import com.example.dailyFreshCoffeeBranch.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class PaymentController {

    private final PaymentService paymentService;


    /**
     * 영수증 목록
     * @param userInfo
     * @param model
     * @return
     */
    @GetMapping("/paymentList")
    public String paymentList(@LoginUserInfo UserInfo userInfo, Model model) {
        List<PaymentDto> paymentDtoList = paymentService.getPaymentList(userInfo.getEmail());
        model.addAttribute("paymentDtoList", paymentDtoList);
        return "payment/paymentList";
    }

}