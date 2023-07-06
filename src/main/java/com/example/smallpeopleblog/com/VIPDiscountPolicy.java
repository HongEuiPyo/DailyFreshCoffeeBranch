package com.example.smallpeopleblog.com;

import com.example.smallpeopleblog.constant.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VIPDiscountPolicy {

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

    public String getApplyRole() {
        return applyRole;
    }

    public double getDiscountRate() {
        return discountRate;
    }
}