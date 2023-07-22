package com.example.dailyFreshCoffeeBranch.repository.impl;

import com.example.dailyFreshCoffeeBranch.entity.Item;

import java.util.List;

public interface ItemRepositoryCustom {

    List<Item> getTop3ItemList();

}
