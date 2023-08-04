package com.example.dailyFreshCoffeeBranch.repository;

import com.example.dailyFreshCoffeeBranch.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DeliveryRepository extends JpaRepository<Delivery, Long>, DeliveryRepositoryCustom {

}