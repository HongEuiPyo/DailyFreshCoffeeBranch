package com.example.dailyFreshCoffeeBranch.repository;

import com.example.dailyFreshCoffeeBranch.entity.Item;

import java.util.List;

public interface CustomItemQueryDslRepository {

    List<Item> getTop3ItemList();

}
