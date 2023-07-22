package com.example.dailyFreshCoffeeBranch.repository;

import com.example.dailyFreshCoffeeBranch.entity.Delivery;
import com.example.dailyFreshCoffeeBranch.repository.impl.DeliveryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DeliveryRepository extends JpaRepository<Delivery, Long>, DeliveryRepositoryCustom {

}