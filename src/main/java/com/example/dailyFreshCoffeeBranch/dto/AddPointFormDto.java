package com.example.dailyFreshCoffeeBranch.dto;

import lombok.*;

import javax.validation.constraints.Positive;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class AddPointFormDto {

    @Positive(message = "포인트 충전을 위해 유효한 금액을 입력해주세요.(입력금액: 0 초과)")
    private int point;

}