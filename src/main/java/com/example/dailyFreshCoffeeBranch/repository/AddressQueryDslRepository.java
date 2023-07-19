package com.example.dailyFreshCoffeeBranch.repository;

import com.example.dailyFreshCoffeeBranch.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressQueryDslRepository extends JpaRepository<Address, Long>, CustomAddressQueryDslRepository {
}
