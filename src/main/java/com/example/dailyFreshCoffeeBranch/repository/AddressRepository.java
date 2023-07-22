package com.example.dailyFreshCoffeeBranch.repository;

import com.example.dailyFreshCoffeeBranch.entity.Address;
import com.example.dailyFreshCoffeeBranch.repository.impl.AddressRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long>, AddressRepositoryCustom {
}
