package com.example.dailyFreshCoffeeBranch.controller;

import com.example.dailyFreshCoffeeBranch.dto.DeliveryFormDto;
import com.example.dailyFreshCoffeeBranch.service.DeliveryMngService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class DeliveryMngController {

    private final DeliveryMngService deliveryMngService;


    /**
     * 회원 배송 관리 조회
     *
     * @param model
     * @param pageable
     * @return
     */
    @GetMapping("/admin/deliveriesMng")
    public String getMemberDeliveryMngList(Model model, Pageable pageable) {
        Page<DeliveryFormDto> result = deliveryMngService.getMemberDeliveryMngList(pageable);
        model.addAttribute("result", result);

        return "deliveryMng/deliveryMngList";
    }

    /**
     * 회원 배송 관리 상세
     *
     * @param id
     * @param model
     * @param pageable
     * @return
     */
    @GetMapping("/admin/deliveriesMng/{id}")
    public String getMemberDeliveryMngDetail
    (
            @PathVariable Long id,
            Model model, Pageable pageable
    ) {
        DeliveryFormDto result = deliveryMngService.getMemberDeliveryMngDetail(id, pageable);
        model.addAttribute("result", result);

        return "deliveryMng/deliveryMngDetail";
    }
}