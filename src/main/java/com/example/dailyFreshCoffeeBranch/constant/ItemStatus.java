package com.example.dailyFreshCoffeeBranch.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
public enum ItemStatus {

    SELL("SELL", "판매"),
    SOLD_OUT("SOLD_OUT", "매진");

    private final String key;
    private final String title;

}
