package com.example.dailyFreshCoffeeBranch.controller;

import com.example.dailyFreshCoffeeBranch.dto.DeliveryDto;
import com.example.dailyFreshCoffeeBranch.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;

import static com.example.dailyFreshCoffeeBranch.com.MySecurityUtils.findMemberEmail;

@RequiredArgsConstructor
@Controller
public class DeliveryController {

    private final DeliveryService deliveryService;

    /**
     * 회원 배송 조회
     *
     * @param session
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/delivery")
    public String getMemberDeliveryList(HttpSession session, Principal principal, Model model) {
        String memberEmail = findMemberEmail(principal, session);
        DeliveryDto memberDelivery = deliveryService.getMemberDeliveryList(memberEmail);
        model.addAttribute("memberDelivery", memberDelivery);
        return "delivery/deliveryList";
    }
}