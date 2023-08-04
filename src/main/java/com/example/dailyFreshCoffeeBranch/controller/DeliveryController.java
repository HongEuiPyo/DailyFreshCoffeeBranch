package com.example.dailyFreshCoffeeBranch.controller;

import com.example.dailyFreshCoffeeBranch.annotation.LoginUser;
import com.example.dailyFreshCoffeeBranch.dto.DeliveryFormDto;
import com.example.dailyFreshCoffeeBranch.dto.LoginUserDto;
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
     * 회원 배송 조회 Ajax
     *
     * @param loginUserDto
     * @param model
     * @return
     */
    @GetMapping("/deliveriesAjax")
    public String getMemberDeliveryListAjax
    (
            @LoginUser LoginUserDto loginUserDto,
            Model model,
            Pageable pageable
    ) {
        Page<DeliveryFormDto> result = deliveryService.getMemberDeliveryList(loginUserDto.getEmail(), pageable);
        model.addAttribute("result", result);

        return "delivery/deliveryListAjax";
    }

    /**
     * 회원 배송 상세 Ajax
     *
     * @param id
     * @param loginUserDto
     * @param model
     * @return
     */
    @GetMapping("/deliveriesAjax/{id}")
    public String getMemberDeliveryDetailAjax
    (
            @PathVariable Long id,
            @LoginUser LoginUserDto loginUserDto,
            Model model,
            Pageable pageable
    ) {
        DeliveryFormDto result = deliveryService.getMemberDeliveryDetail(loginUserDto.getEmail(), id, pageable);
        model.addAttribute("result", result);

        return "delivery/deliveryDetailAjax";
    }

}