package com.example.dailyFreshCoffeeBranch.constant;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ItemStatus {
    SELL, SOLD_OUT
}
