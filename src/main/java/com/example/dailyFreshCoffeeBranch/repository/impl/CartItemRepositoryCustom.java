package com.example.dailyFreshCoffeeBranch.repository.impl;

import com.example.dailyFreshCoffeeBranch.entity.CartItem;

import java.util.Optional;

public interface CartItemRepositoryCustom {

    Optional<CartItem> findByIdAndCartId(Long cartItemId, Long cartId);

    Optional<CartItem> findByItemId(Long itemId);

}