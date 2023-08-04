package com.example.dailyFreshCoffeeBranch.dto;

import com.example.dailyFreshCoffeeBranch.constant.DeliveryItemStatus;
import com.example.dailyFreshCoffeeBranch.domain.DeliveryItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class DeliveryItemFormDto {

    private Long id;

    @JsonProperty("deliveryItemStatus")
    private DeliveryItemStatus deliveryItemStatus;

    private int count;

    private ItemFormDto itemFormDto;

    public static DeliveryItemFormDto of(DeliveryItem deliveryItem) {
        return DeliveryItemFormDto.builder()
                .id(deliveryItem.getId())
                .deliveryItemStatus(deliveryItem.getDeliveryItemStatus())
                .count(deliveryItem.getCount())
                .itemFormDto(deliveryItem.getItem().toDto())
                .build();
    }

}
