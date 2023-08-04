package com.example.dailyFreshCoffeeBranch.repository;

import com.example.dailyFreshCoffeeBranch.domain.CartItem;

import java.util.Optional;

public interface CartItemRepositoryCustom {

    Optional<CartItem> findByIdAndCartId(Long cartItemId, Long cartId);

    Optional<CartItem> findByItemId(Long itemId);

}