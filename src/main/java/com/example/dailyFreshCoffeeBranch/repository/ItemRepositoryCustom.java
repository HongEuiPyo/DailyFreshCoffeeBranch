package com.example.dailyFreshCoffeeBranch.repository;

import com.example.dailyFreshCoffeeBranch.dto.ItemSearchFormDto;
import com.example.dailyFreshCoffeeBranch.domain.Item;

import java.util.List;

public interface ItemRepositoryCustom {

    List<Item> getTop3ItemList();

    List<Item> findAllSearching(ItemSearchFormDto searchDto);

    void getSoldOut(Long id);
}
