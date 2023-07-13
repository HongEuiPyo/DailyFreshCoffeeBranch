package com.example.dailyFreshCoffeeBranch.repository;

import com.example.dailyFreshCoffeeBranch.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemQueryDslRepository extends JpaRepository<Item, Long>, CustomItemQueryDslRepository {

}