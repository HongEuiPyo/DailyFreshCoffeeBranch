package com.example.dailyFreshCoffeeBranch.repository.impl;

import com.example.dailyFreshCoffeeBranch.constant.DeliveryItemStatus;
import com.example.dailyFreshCoffeeBranch.entity.DeliveryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DeliveryItemRepositoryCustom {

    Page<DeliveryItem> findByDeliveryId(Long deliveryId, Pageable pageable);

    List<DeliveryItem> updateDeliveryItemStatus(Long deliveryItemId, DeliveryItemStatus deliveryItemStatus);
}