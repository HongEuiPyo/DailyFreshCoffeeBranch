package com.example.dailyFreshCoffeeBranch.dto;

import com.example.dailyFreshCoffeeBranch.entity.Member;
import com.example.dailyFreshCoffeeBranch.entity.Payment;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class PaymentDto {

    private Long id;
    private String itemNames;
    private int totalUsePoint;
    private double discount;
    private LocalDateTime modifiedTime;

    public Payment toEntity(Member member) {
        return Payment.builder()
                .id(id)
                .itemNames(itemNames)
                .totalPayPrice(totalUsePoint)
                .discount(discount)
                .member(member)
                .build();
    }

}
