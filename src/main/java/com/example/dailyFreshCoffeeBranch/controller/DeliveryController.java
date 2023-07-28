package com.example.dailyFreshCoffeeBranch.controller;

import com.example.dailyFreshCoffeeBranch.annotation.LoginUserInfo;
import com.example.dailyFreshCoffeeBranch.dto.DeliveryDto;
import com.example.dailyFreshCoffeeBranch.security.oauth2.UserInfo;
import com.example.dailyFreshCoffeeBranch.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    public String getMemberDeliveryList(@LoginUserInfo UserInfo userInfo, Model model, Pageable pageable) {
        Page<DeliveryDto> deliveryList = deliveryService.getMemberDeliveryList(userInfo.getEmail(), pageable);
        model.addAttribute("deliveryList", deliveryList);
        return "delivery/deliveryList";
    }

    /**
     * 회원 배송 상세
     *
     * @param id
     * @param userInfo
     * @param model
     * @return
     */
    @GetMapping("/deliveries/{id}")
    public String getMemberDeliveryDetail(@PathVariable Long id, @LoginUserInfo UserInfo userInfo, Model model, Pageable pageable) {
        DeliveryDto delivery = deliveryService.getMemberDeliveryDetail(userInfo.getEmail(), id, pageable);
        model.addAttribute("delivery", delivery);
        return "delivery/deliveryDetail";
    }

    /**
     * 회원 배송 조회 Ajax
     *
     * @param userInfo
     * @param model
     * @return
     */
    @GetMapping("/deliveriesAjax")
    public String getMemberDeliveryListAjax(@LoginUserInfo UserInfo userInfo, Model model, Pageable pageable) {
        Page<DeliveryDto> deliveryPage = deliveryService.getMemberDeliveryList(userInfo.getEmail(), pageable);
        model.addAttribute("deliveryPage", deliveryPage);
        return "delivery/deliveryListAjax";
    }

    /**
     * 회원 배송 상세 Ajax
     *
     * @param id
     * @param userInfo
     * @param model
     * @return
     */
    @GetMapping("/deliveriesAjax/{id}")
    public String getMemberDeliveryDetailAjax(@PathVariable Long id, @LoginUserInfo UserInfo userInfo, Model model, Pageable pageable) {
        DeliveryDto delivery = deliveryService.getMemberDeliveryDetail(userInfo.getEmail(), id, pageable);
        model.addAttribute("delivery", delivery);
        return "delivery/deliveryDetailAjax";
    }

}