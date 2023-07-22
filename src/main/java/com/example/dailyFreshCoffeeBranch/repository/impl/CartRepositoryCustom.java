package com.example.dailyFreshCoffeeBranch.repository.impl;

import com.example.dailyFreshCoffeeBranch.entity.Cart;

import java.util.Optional;

public interface CartRepositoryCustom {
    Optional<Cart> findByMemberId(Long memberId);

}