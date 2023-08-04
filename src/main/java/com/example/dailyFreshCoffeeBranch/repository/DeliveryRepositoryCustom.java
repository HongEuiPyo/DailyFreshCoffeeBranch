package com.example.dailyFreshCoffeeBranch.repository;

import com.example.dailyFreshCoffeeBranch.domain.Delivery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface DeliveryRepositoryCustom {

    Page<Delivery> findByMemberEmail(String email, Pageable pageable);

    Optional<Delivery> findByMemberEmailAndId(String email, Long id);

    Page<Delivery> findAllPage(Pageable pageable);

    long getDelivered(Long deliveryId);

    long getDelivering(Long deliveryId);
}