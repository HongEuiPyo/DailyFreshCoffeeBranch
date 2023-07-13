package com.example.dailyFreshCoffeeBranch.controller;

import com.example.dailyFreshCoffeeBranch.dto.PaymentDto;
import com.example.dailyFreshCoffeeBranch.security.SessionUser;
import com.example.dailyFreshCoffeeBranch.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
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
    public String paymentList(Principal principal, HttpSession session, Model model) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        String memberEmail = "";
        if (user == null) {
            memberEmail = principal.getName();
        } else {
            memberEmail = user.getEmail();
        }
        List<PaymentDto> paymentDtoList = paymentService.getPaymentList(memberEmail);
        model.addAttribute("paymentDtoList", paymentDtoList);
        return "payment/paymentList";
    }

}