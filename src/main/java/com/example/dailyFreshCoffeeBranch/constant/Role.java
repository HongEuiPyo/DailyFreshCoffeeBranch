package com.example.dailyFreshCoffeeBranch.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Role {
    USER("ROLE_USER", "사용자")
    , SMALL_BUSINESS("ROLE_SMALL_BUSINESS", "소상공인")
    , ADMIN("ROLE_ADMIN", "관리자");

    private final String key;
    private final String title;

}