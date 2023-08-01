package com.example.dailyFreshCoffeeBranch.service;


import com.example.dailyFreshCoffeeBranch.dto.ItemDto;
import com.example.dailyFreshCoffeeBranch.dto.ItemSearchDto;
import com.example.dailyFreshCoffeeBranch.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class ItemMngService {

    private final ItemRepository itemRepository;

    public List<ItemDto> getItemMngList(ItemSearchDto searchDto) {

        return itemRepository.findAllSearching(searchDto).stream()
                .map(i -> i.toDto())
                .collect(Collectors.toList());
    }

}