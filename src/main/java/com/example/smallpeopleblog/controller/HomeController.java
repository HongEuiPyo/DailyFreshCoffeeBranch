package com.example.smallpeopleblog.controller;

import com.example.smallpeopleblog.dto.ItemDto;
import com.example.smallpeopleblog.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final HomeService homeService;


    /**
     * 홈 화면
     * @return
     */
    @RequestMapping("/")
    public String home(Model model) {
        // 1. 현재 날짜 계산
        LocalDate localDate = LocalDate.now();
        int yyyy = localDate.getYear();
        int MM = localDate.getMonthValue();
        model.addAttribute("yyyy", yyyy);
        model.addAttribute("MM", MM);

        // 2. 판매량 TOP3 상품 목록 조회
        List<ItemDto> top3ItemList = homeService.getTop3ItemList();
        model.addAttribute("top3ItemList", top3ItemList);
        return "index";
    }

}
