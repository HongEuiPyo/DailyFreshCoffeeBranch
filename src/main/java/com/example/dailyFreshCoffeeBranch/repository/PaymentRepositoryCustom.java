package com.example.dailyFreshCoffeeBranch.repository;

import com.example.dailyFreshCoffeeBranch.domain.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentRepositoryCustom {
    Page<Payment> findByMemberId(Long memberId, Pageable pageable);

}