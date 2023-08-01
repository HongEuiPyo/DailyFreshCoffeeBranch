package com.example.dailyFreshCoffeeBranch.api.naver;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class NaverClient {

    @Value("${naver.client-id}")
    private String CLIENT_ID;

    @Value("${naver.client-secret}")
    private String CLIENT_SECRET;

}