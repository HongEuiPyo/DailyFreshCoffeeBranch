package com.example.dailyFreshCoffeeBranch.controller;

import com.example.dailyFreshCoffeeBranch.annotation.LoginUser;
import com.example.dailyFreshCoffeeBranch.dto.PaymentFormDto;
import com.example.dailyFreshCoffeeBranch.dto.LoginUserDto;
import com.example.dailyFreshCoffeeBranch.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class PaymentController {

    private final PaymentService paymentService;


    /**
     * 영수증 목록 처리
     *
     * @param loginUserDto
     * @param model
     * @return
     */
    @GetMapping("/paymentListAjax")
    public String paymentListProc(@LoginUser LoginUserDto loginUserDto, Model model, Pageable pageable) {
        Page<PaymentFormDto> result = paymentService.getPaymentList(loginUserDto.getEmail(), pageable);
        model.addAttribute("result", result);

        return "payment/paymentListAjax";
    }

}