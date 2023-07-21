package com.example.dailyFreshCoffeeBranch.repository;

import com.example.dailyFreshCoffeeBranch.entity.Address;

public interface CustomAddressQueryDslRepository {

    Address getStoreLocation();
    Address getLoginUserLocation(String email);
}
