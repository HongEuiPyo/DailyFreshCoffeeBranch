package com.example.dailyFreshCoffeeBranch.repository;

import com.example.dailyFreshCoffeeBranch.domain.Cart;

import java.util.Optional;

public interface CartRepositoryCustom {
    Optional<Cart> findByMemberId(Long memberId);

}