package com.example.dailyFreshCoffeeBranch.repository;

import com.example.dailyFreshCoffeeBranch.dto.AddressResponseDto;

import java.util.Optional;

public interface AddressRepositoryCustom {

    Optional<AddressResponseDto> findStoreLocation();
    Optional<AddressResponseDto> findLoginUserLocationByEmail(String email);

}