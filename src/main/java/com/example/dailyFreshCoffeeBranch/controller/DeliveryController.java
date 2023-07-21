package com.example.dailyFreshCoffeeBranch.controller;

import com.example.dailyFreshCoffeeBranch.annotation.LoginUserInfo;
import com.example.dailyFreshCoffeeBranch.dto.DeliveryDto;
import com.example.dailyFreshCoffeeBranch.security.oauth2.UserInfo;
import com.example.dailyFreshCoffeeBranch.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

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
    @GetMapping("/deliveries")
    public String getMemberDeliveryList(@LoginUserInfo UserInfo userInfo, Model model) {
        List<DeliveryDto> deliveryList = deliveryService.getMemberDeliveryList(userInfo.getEmail());
        model.addAttribute("deliveryList", deliveryList);
        return "delivery/deliveryList";
    }

    @GetMapping("/deliveries/{id}")
    public String getMemberDeliveryDetail(@PathVariable Long id, @LoginUserInfo UserInfo userInfo, Model model) {
        DeliveryDto memberDelivery = deliveryService.getMemberDeliveryDetail(userInfo.getEmail(), id);
        model.addAttribute("memberDelivery", memberDelivery);
        return "delivery/deliveryList";
    }
}