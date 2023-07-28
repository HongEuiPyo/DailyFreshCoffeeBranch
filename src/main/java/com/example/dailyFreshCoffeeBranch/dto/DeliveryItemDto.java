package com.example.dailyFreshCoffeeBranch.dto;

import com.example.dailyFreshCoffeeBranch.constant.DeliveryItemStatus;
import com.example.dailyFreshCoffeeBranch.entity.DeliveryItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class DeliveryItemDto {

    private Long id;

    @JsonProperty("deliveryItemStatus")
    private DeliveryItemStatus deliveryItemStatus;

    private int count;

    private ItemDto itemDto;

    public static DeliveryItemDto of(DeliveryItem deliveryItem) {
        return DeliveryItemDto.builder()
                .id(deliveryItem.getId())
                .deliveryItemStatus(deliveryItem.getDeliveryItemStatus())
                .count(deliveryItem.getCount())
                .itemDto(deliveryItem.getItem().toDto())
                .build();
    }

}
