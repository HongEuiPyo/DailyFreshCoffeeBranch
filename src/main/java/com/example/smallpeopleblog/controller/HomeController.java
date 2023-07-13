package com.example.smallpeopleblog.controller;

import com.example.smallpeopleblog.dto.ItemDto;
import com.example.smallpeopleblog.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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
        List<ItemDto> top3ItemList = homeService.getTop3ItemList();
        model.addAttribute("top3ItemList", top3ItemList);
        return "index";
    }

}
