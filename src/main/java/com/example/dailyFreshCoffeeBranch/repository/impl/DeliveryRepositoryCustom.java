package com.example.dailyFreshCoffeeBranch.repository.impl;

import com.example.dailyFreshCoffeeBranch.entity.Delivery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface DeliveryRepositoryCustom {

    Page<Delivery> findByMemberEmail(String email, Pageable pageable);

    Optional<Delivery> findByMemberEmailAndId(String email, Long id);

    Page<Delivery> findAllPage(Pageable pageable);
}