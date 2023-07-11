package com.example.smallpeopleblog.controller;

import com.example.smallpeopleblog.dto.PaymentDto;
import com.example.smallpeopleblog.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class PaymentController {

    private final PaymentService paymentService;


    /**
     * 영수증 목록
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/paymentList")
    public String paymentList(Principal principal, Model model) {
        List<PaymentDto> paymentDtoList = paymentService.getPaymentList(principal.getName());
        model.addAttribute("paymentDtoList", paymentDtoList);
        return "payment/paymentList";
    }

}