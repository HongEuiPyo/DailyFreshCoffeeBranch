package com.example.dailyFreshCoffeeBranch.entity;

import com.example.dailyFreshCoffeeBranch.dto.CartDto;
import com.example.dailyFreshCoffeeBranch.dto.CartItemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "cart")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Cart extends BaseEntity {

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id desc")
    private List<CartItem> cartItemList;

    @Builder
    public Cart(Member member) {
        this.member = member;
    }

    @Builder
    public Cart(Member member, List<CartItem> cartItemList) {
        this.member = member;
        this.cartItemList = cartItemList;
    }


    public CartDto toDto() {
        List<CartItemDto> cartItemDtoList = new ArrayList<>();
        if (cartItemList != null) {
            cartItemDtoList = cartItemList.stream()
                    .map(cartItem -> cartItem.toDto())
                    .collect(Collectors.toList());
        }
        return CartDto.builder()
                .id(id)
                .memberId(member.getId())
                .cartItemDtoList(cartItemDtoList)
                .build();
    }

}