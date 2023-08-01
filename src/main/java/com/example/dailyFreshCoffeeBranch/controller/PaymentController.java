package com.example.dailyFreshCoffeeBranch.controller;

import com.example.dailyFreshCoffeeBranch.annotation.LoginUser;
import com.example.dailyFreshCoffeeBranch.dto.PaymentDto;
import com.example.dailyFreshCoffeeBranch.dto.UserInfoDto;
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
     * 영수증 목록
     *
     * @return
     */
    @GetMapping("/paymentList")
    public String paymentList() {
        return "payment/paymentList";
    }

    /**
     * 영수증 목록 처리
     *
     * @param userInfoDto
     * @param model
     * @return
     */
    @GetMapping("/paymentListAjax")
    public String paymentListProc(@LoginUser UserInfoDto userInfoDto, Model model, Pageable pageable) {
        Page<PaymentDto> paymentDtoPage = paymentService.getPaymentList(userInfoDto.getEmail(), pageable);
        model.addAttribute("paymentDtoPage", paymentDtoPage);
        return "payment/paymentListAjax";
    }

}