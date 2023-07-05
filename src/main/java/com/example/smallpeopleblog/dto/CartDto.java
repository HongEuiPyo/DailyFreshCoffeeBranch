package com.example.smallpeopleblog.dto;

import com.example.smallpeopleblog.entity.Cart;
import com.example.smallpeopleblog.entity.Member;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class CartDto {

    private Long id;

    private Long memberId;

    private List<CartItemDto> cartItemDtoList;

    public Cart toEntity(Member member) {
        return Cart.builder()
                .member(member)
                .build();
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (int i=0; i<cartItemDtoList.size(); i++)
            totalPrice += cartItemDtoList.get(i).getPrice() * cartItemDtoList.get(i).getCount();
        return totalPrice;
    }

}