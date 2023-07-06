package com.example.smallpeopleblog.entity;

import com.example.smallpeopleblog.dto.CartItemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "cart_item")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CartItem {

    @Id
    @Column(name = "cart_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ColumnDefault("0")
    private int count;


    @Builder
    public CartItem(Cart cart, Item item, int count) {
        this.cart = cart;
        this.item = item;
        this.count = count;
    }


    public CartItemDto toDto() {
        String imageUrl = "";

        if (item.getImageFileList() != null) {
            imageUrl = item.getImageFileList().get(0).getImageUrl();
        }

        return CartItemDto.builder()
                .id(id)
                .itemId(item.getId())
                .itemName(item.getTitle())
                .count(count)
                .price(item.getPrice())
                .imageUrl(imageUrl)
                .build();
    }

    // 수량 수정
    public void updateCount(CartItemDto cartItemDto) {
        this.count = cartItemDto.getCount();
    }
}