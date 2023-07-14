package com.example.dailyFreshCoffeeBranch.service;

import com.example.dailyFreshCoffeeBranch.dto.ItemDto;
import com.example.dailyFreshCoffeeBranch.entity.Item;
import com.example.dailyFreshCoffeeBranch.repository.ItemQueryDslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class HomeService {

    private final ItemQueryDslRepository itemQueryDslRepository;


    public List<ItemDto> getTop3ItemList() {
        List<Item> itemList =  itemQueryDslRepository.getTop3ItemList();
        return itemList.stream()
                .map(item -> item.toDto())
                .collect(Collectors.toList());
    }

}