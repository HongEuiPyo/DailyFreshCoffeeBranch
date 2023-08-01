package com.example.dailyFreshCoffeeBranch.repository.impl;

import com.example.dailyFreshCoffeeBranch.dto.ItemSearchDto;
import com.example.dailyFreshCoffeeBranch.entity.Item;

import java.util.List;

public interface ItemRepositoryCustom {

    List<Item> getTop3ItemList();

    List<Item> findAllSearching(ItemSearchDto searchDto);
}
