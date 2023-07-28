package com.example.dailyFreshCoffeeBranch.repository.impl;

import com.example.dailyFreshCoffeeBranch.entity.Address;

public interface AddressRepositoryCustom {

    Address findStoreLocation();
    Address findLoginUserLocationByEmail(String email);
}
