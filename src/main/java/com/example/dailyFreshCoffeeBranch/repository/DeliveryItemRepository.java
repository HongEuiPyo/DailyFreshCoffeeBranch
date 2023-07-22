package com.example.dailyFreshCoffeeBranch.repository;

import com.example.dailyFreshCoffeeBranch.entity.DeliveryItem;
import com.example.dailyFreshCoffeeBranch.repository.impl.DeliveryItemRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryItemRepository extends JpaRepository<DeliveryItem, Long>, DeliveryItemRepositoryCustom {

}