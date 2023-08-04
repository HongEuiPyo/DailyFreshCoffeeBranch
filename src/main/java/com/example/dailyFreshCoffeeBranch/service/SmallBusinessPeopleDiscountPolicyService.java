package com.example.dailyFreshCoffeeBranch.service;

import com.example.dailyFreshCoffeeBranch.constant.Role;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class SmallBusinessPeopleDiscountPolicyService {

    @Value("${vipDiscountPolicy.applyRole}")
    private String applyRole;

    @Value("${vipDiscountPolicy.discountRate}")
    private double discountRate;

    public double apply(int totalPrice, Role memberRole) {
        if (memberRole.toString().equals(applyRole)) {
            double applyPrice = totalPrice * discountRate;
            totalPrice -= applyPrice;
        }
        return totalPrice;
    }

}