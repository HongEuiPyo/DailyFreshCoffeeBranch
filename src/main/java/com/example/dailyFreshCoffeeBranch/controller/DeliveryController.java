package com.example.dailyFreshCoffeeBranch.controller;

import com.example.dailyFreshCoffeeBranch.annotation.LoginUserInfo;
import com.example.dailyFreshCoffeeBranch.dto.DeliveryDto;
import com.example.dailyFreshCoffeeBranch.security.oauth2.UserInfo;
import com.example.dailyFreshCoffeeBranch.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class DeliveryController {

    private final DeliveryService deliveryService;

    /**
     * 회원 배송 조회
     *
     * @param userInfo
     * @param model
     * @return
     */
    @GetMapping("/delivery")
    public String getMemberDeliveryList(@LoginUserInfo UserInfo userInfo, Model model) {
        DeliveryDto memberDelivery = deliveryService.getMemberDeliveryList(userInfo.getEmail());
        model.addAttribute("memberDelivery", memberDelivery);
        return "delivery/deliveryList";
    }
}