package com.example.dailyFreshCoffeeBranch.repository;

import com.example.dailyFreshCoffeeBranch.domain.Cart;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.example.dailyFreshCoffeeBranch.domain.QCart.cart;

@RequiredArgsConstructor
public class CartRepositoryCustomImpl implements CartRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Cart> findByMemberId(Long memberId) {
        Cart content = queryFactory.selectFrom(cart)
                .where(cart.member.id.eq(memberId))
                .fetchOne();

        return Optional.ofNullable(content);
    }

}