package com.example.smallpeopleblog.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ItemCategory {
    ALL("ALL", "모든 음료"),
    COLD_BREW("COLD_BREW", "콜드브루"),
    BROOD("BROOD", "콜드브루"),
    ESPRESSO("ESPRESSO", "콜드브루"),
    FRAPPUCCINO("FRAPPUCCINO", "콜드브루"),
    REFRESHER("REFRESHER", "콜드브루"),
    TEA("TEA", "콜드브루"),
    BOTTLED("BOTTLED", "콜드브루");

    private final String key;
    private final String title;

}