package com.example.dailyFreshCoffeeBranch.repository;

import com.example.dailyFreshCoffeeBranch.constant.Role;
import com.example.dailyFreshCoffeeBranch.dto.AddressResponseDto;
import com.example.dailyFreshCoffeeBranch.dto.QAddressResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.example.dailyFreshCoffeeBranch.domain.QAddress.address;

@RequiredArgsConstructor
public class AddressRepositoryCustomImpl implements AddressRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    /**
     * 매장 위치
     *
     * @return
     */
    @Override
    public Optional<AddressResponseDto> findStoreLocation() {
        AddressResponseDto result = queryFactory
                .select(new QAddressResponseDto(address.id, address.latitude, address.longitude, address.roadAddress, address.createdTime, address.modifiedTime))
                .from(address)
                .where(address.member.role.eq(Role.ADMIN))
                .fetchOne();
        return Optional.ofNullable(result);
    }

    /**
     * 사용자 위치
     *
     * @param email
     * @return
     */
    @Override
    public Optional<AddressResponseDto> findLoginUserLocationByEmail(String email) {
        AddressResponseDto result = queryFactory
                .select(new QAddressResponseDto(address.id, address.latitude, address.longitude, address.roadAddress, address.createdTime, address.modifiedTime))
                .from(address)
                .where(address.member.email.eq(email))
                .fetchOne();
        return Optional.ofNullable(result);
    }

}