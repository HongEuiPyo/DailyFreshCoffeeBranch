package com.example.dailyFreshCoffeeBranch.repository.impl;

import com.example.dailyFreshCoffeeBranch.constant.Role;
import com.example.dailyFreshCoffeeBranch.entity.Address;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.example.dailyFreshCoffeeBranch.entity.QAddress.address;

@RequiredArgsConstructor
public class AddressRepositoryCustomImpl implements AddressRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Address findStoreLocation() {
        return queryFactory
                .selectFrom(address)
                .where(address.member.role.eq(Role.ADMIN))
                .fetchFirst();
    }

    @Override
    public Address findLoginUserLocationByEmail(String email) {
        return queryFactory
                .selectFrom(address)
                .where(address.member.email.eq(email))
                .fetchFirst();
    }

}