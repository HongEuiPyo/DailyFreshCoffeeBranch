package com.example.dailyFreshCoffeeBranch.repository;

import com.example.dailyFreshCoffeeBranch.constant.Role;
import com.example.dailyFreshCoffeeBranch.entity.Address;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.example.dailyFreshCoffeeBranch.entity.QAddress.address;

@RequiredArgsConstructor
public class CustomAddressQueryDslRepositoryImpl implements CustomAddressQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Address getStoreLocation() {
        return queryFactory
                .selectFrom(address)
                .where(address.member.role.eq(Role.ADMIN))
                .fetchFirst();
    }

    @Override
    public Address getLoginUserLocation(String email) {
        return queryFactory
                .selectFrom(address)
                .where(address.member.email.eq(email))
                .fetchFirst();
    }

}