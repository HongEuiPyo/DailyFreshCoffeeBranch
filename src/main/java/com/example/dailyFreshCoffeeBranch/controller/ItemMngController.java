package com.example.dailyFreshCoffeeBranch.controller;

import com.example.dailyFreshCoffeeBranch.dto.ItemDto;
import com.example.dailyFreshCoffeeBranch.dto.ItemSearchDto;
import com.example.dailyFreshCoffeeBranch.service.ItemMngService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class ItemMngController {

    private final ItemMngService itemMngService;


    /**
     * 상품 관리 목록
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/itemsMng")
    public String getItemMngList(@ModelAttribute("searchDto") ItemSearchDto searchDto, Model model) {

        List<ItemDto> itemDtoList = itemMngService.getItemMngList(searchDto);

        model.addAttribute("itemDtoList", itemDtoList);

        return "itemMng/itemMngList";
    }

}