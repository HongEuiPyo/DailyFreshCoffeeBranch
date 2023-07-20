package com.example.dailyFreshCoffeeBranch.dto;

import com.example.dailyFreshCoffeeBranch.constant.DeliveryStatus;
import com.example.dailyFreshCoffeeBranch.entity.Delivery;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class DeliveryDto {

    private DeliveryStatus deliveryStatus;

    private List<DeliveryItemDto> deliveryItemList;

    private LocalDateTime deliveryStartTime;

    private LocalDateTime deliveryTakenTime;

    public static DeliveryDto of(Delivery delivery) {
        return DeliveryDto.builder()
                .deliveryStatus(delivery.getDeliveryStatus())
                .deliveryStartTime(delivery.getCreatedTime())
                .build();
    }

}
