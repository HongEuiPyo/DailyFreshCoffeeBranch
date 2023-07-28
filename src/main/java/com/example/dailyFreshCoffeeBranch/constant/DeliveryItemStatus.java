package com.example.dailyFreshCoffeeBranch.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DeliveryItemStatus {

    DELIVERING("DELIVERING", "배송중"),
    DELIVERED("DELIVERED", "배송완료");

    private final String key;
    private final String title;

}