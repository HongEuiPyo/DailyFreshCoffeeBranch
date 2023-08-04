package com.example.dailyFreshCoffeeBranch.repository;

import com.example.dailyFreshCoffeeBranch.domain.CartItem;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.example.dailyFreshCoffeeBranch.domain.QCartItem.cartItem;

@RequiredArgsConstructor
public class CartItemRepositoryCustomImpl implements CartItemRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<CartItem> findByIdAndCartId(Long cartItemId, Long cartId) {

        CartItem content = queryFactory.selectFrom(cartItem)
                .where(cartItem.id.eq(cartItemId).and(cartItem.cart.id.eq(cartId)))
                .fetchOne();

        return Optional.ofNullable(content);
    }

    @Override
    public Optional<CartItem> findByItemId(Long itemId) {

        CartItem content = queryFactory.selectFrom(cartItem)
                .where(cartItem.item.id.eq(itemId))
                .fetchOne();

        return Optional.ofNullable(content);
    }
}