package com.example.dailyFreshCoffeeBranch.repository;

import com.example.dailyFreshCoffeeBranch.entity.CartItem;
import com.example.dailyFreshCoffeeBranch.repository.impl.CartItemRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long>, CartItemRepositoryCustom {
}