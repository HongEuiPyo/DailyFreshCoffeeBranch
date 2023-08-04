package com.example.dailyFreshCoffeeBranch.controller;

import com.example.dailyFreshCoffeeBranch.dto.AddressResponseDto;
import com.example.dailyFreshCoffeeBranch.dto.ItemFormDto;
import com.example.dailyFreshCoffeeBranch.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final HomeService homeService;


    /**
     * 홈 화면
     *
     * @return
     */
    @GetMapping("/")
    public String home(Model model) {

        // 현재 날짜 계산
        LocalDate localDate = LocalDate.now();
        int yyyy = localDate.getYear();
        int MM = localDate.getMonthValue();
        model.addAttribute("yyyy", yyyy);
        model.addAttribute("MM", MM);

        // 판매량 TOP3 상품 목록 조회
        List<ItemFormDto> top3ItemList = homeService.getTop3ItemList();
        model.addAttribute("top3ItemList", top3ItemList);

        return "index";
    }

    /**
     * 오시는 길
     *
     * @return
     */
    @GetMapping("/store")
    public String store(Model model) {
        AddressResponseDto result = homeService.getStoreLocation();
        model.addAttribute("result", result);

        return "store";
    }

}