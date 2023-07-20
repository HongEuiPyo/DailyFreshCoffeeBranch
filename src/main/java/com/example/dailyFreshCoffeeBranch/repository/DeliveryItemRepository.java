package com.example.dailyFreshCoffeeBranch.repository;

import com.example.dailyFreshCoffeeBranch.entity.DeliveryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeliveryItemRepository extends JpaRepository<DeliveryItem, Long> {

    @Query("SELECT d FROM DeliveryItem d WHERE d.delivery.id = :deliveryId")
    List<DeliveryItem> findByDeliveryId(@Param("deliveryId") Long deliveryId);
}
