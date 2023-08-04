package com.example.dailyFreshCoffeeBranch.dto;

import com.example.dailyFreshCoffeeBranch.domain.Cart;
import com.example.dailyFreshCoffeeBranch.domain.Member;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class CartResponseDto {

    private Long id;
    private Long memberId;
    private List<CartItemFormDto> cartItemFormDtoList;


    public Cart toEntity(Member member) {
        return Cart.builder()
                .member(member)
                .build();
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (int i = 0; i< cartItemFormDtoList.size(); i++)
            totalPrice += cartItemFormDtoList.get(i).getPrice() * cartItemFormDtoList.get(i).getCount();
        return totalPrice;
    }

}