package com.example.smallpeopleblog.repository;

import com.example.smallpeopleblog.entity.Item;

import java.util.List;

public interface CustomItemQueryDslRepository {

    List<Item> getTop3ItemList();

}
