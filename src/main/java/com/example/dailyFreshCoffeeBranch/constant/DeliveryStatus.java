package com.example.dailyFreshCoffeeBranch.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DeliveryStatus {

    DELIVERING("DELIVERING", "배송중"),
    DELIVERED("DELIVERED", "배송완료");

    private final String key;
    private final String title;

}
