package com.example.dailyFreshCoffeeBranch.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ItemCategory {
    ALL("ALL", "모든 음료"),
    COLD_BREW("COLD_BREW", "콜드브루"),
    BROOD("BROOD", "브루드"),
    ESPRESSO("ESPRESSO", "에스프레소"),
    FRAPPUCCINO("FRAPPUCCINO", "프라푸치노"),
    REFRESHER("REFRESHER", "리프레셔"),
    TEA("TEA", "티"),
    BOTTLED("BOTTLED", "병음료");

    private final String key;
    private final String title;

}