package com.example.dailyFreshCoffeeBranch.repository;

import com.example.dailyFreshCoffeeBranch.constant.DeliveryItemStatus;
import com.example.dailyFreshCoffeeBranch.domain.DeliveryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DeliveryItemRepositoryCustom {

    Page<DeliveryItem> findByDeliveryId(Long deliveryId, Pageable pageable);

    List<DeliveryItem> updateDeliveryItemStatus(Long deliveryItemId, DeliveryItemStatus deliveryItemStatus);
}