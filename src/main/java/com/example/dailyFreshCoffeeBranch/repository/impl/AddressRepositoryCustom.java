package com.example.dailyFreshCoffeeBranch.repository.impl;

import com.example.dailyFreshCoffeeBranch.entity.Address;

public interface AddressRepositoryCustom {

    Address getStoreLocation();
    Address getLoginUserLocation(String email);
}
