package com.example.dailyFreshCoffeeBranch.repository;

import com.example.dailyFreshCoffeeBranch.domain.DeliveryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryItemRepository extends JpaRepository<DeliveryItem, Long>, DeliveryItemRepositoryCustom {

}