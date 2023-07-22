package com.example.dailyFreshCoffeeBranch.repository.impl;

import com.example.dailyFreshCoffeeBranch.entity.DeliveryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeliveryItemRepositoryCustom {

    Page<DeliveryItem> findByDeliveryId(Long deliveryId, Pageable pageable);

}