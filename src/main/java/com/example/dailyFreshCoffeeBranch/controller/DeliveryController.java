package com.example.dailyFreshCoffeeBranch.controller;

import com.example.dailyFreshCoffeeBranch.annotation.LoginUser;
import com.example.dailyFreshCoffeeBranch.dto.DeliveryDto;
import com.example.dailyFreshCoffeeBranch.dto.UserInfoDto;
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
     * @param userInfoDto
     * @param model
     * @return
     */
    @GetMapping("/deliveries")
    public String getMemberDeliveryList(@LoginUser UserInfoDto userInfoDto, Model model, Pageable pageable) {
        Page<DeliveryDto> deliveryList = deliveryService.getMemberDeliveryList(userInfoDto.getEmail(), pageable);
        model.addAttribute("deliveryList", deliveryList);
        return "delivery/deliveryList";
    }

    /**
     * 회원 배송 상세
     *
     * @param id
     * @param userInfoDto
     * @param model
     * @return
     */
    @GetMapping("/deliveries/{id}")
    public String getMemberDeliveryDetail(@PathVariable Long id, @LoginUser UserInfoDto userInfoDto, Model model, Pageable pageable) {
        DeliveryDto delivery = deliveryService.getMemberDeliveryDetail(userInfoDto.getEmail(), id, pageable);
        model.addAttribute("delivery", delivery);
        return "delivery/deliveryDetail";
    }

    /**
     * 회원 배송 조회 Ajax
     *
     * @param userInfoDto
     * @param model
     * @return
     */
    @GetMapping("/deliveriesAjax")
    public String getMemberDeliveryListAjax(@LoginUser UserInfoDto userInfoDto, Model model, Pageable pageable) {
        Page<DeliveryDto> deliveryPage = deliveryService.getMemberDeliveryList(userInfoDto.getEmail(), pageable);
        model.addAttribute("deliveryPage", deliveryPage);
        return "delivery/deliveryListAjax";
    }

    /**
     * 회원 배송 상세 Ajax
     *
     * @param id
     * @param userInfoDto
     * @param model
     * @return
     */
    @GetMapping("/deliveriesAjax/{id}")
    public String getMemberDeliveryDetailAjax(@PathVariable Long id, @LoginUser UserInfoDto userInfoDto, Model model, Pageable pageable) {
        DeliveryDto delivery = deliveryService.getMemberDeliveryDetail(userInfoDto.getEmail(), id, pageable);
        model.addAttribute("delivery", delivery);
        return "delivery/deliveryDetailAjax";
    }

}