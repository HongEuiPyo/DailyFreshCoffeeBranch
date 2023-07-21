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

    private Long id;

    private DeliveryStatus deliveryStatus;

    private List<DeliveryItemDto> deliveryItemList;

    private LocalDateTime deliveryStartTime;

    private long deliveryTakenTime;

    private String deliveryDepartureRoadLocation;

    private String deliveryDestinationRoadLocation;

    public static DeliveryDto of(Delivery delivery) {
        return DeliveryDto.builder()
                .id(delivery.getId())
                .deliveryStatus(delivery.getDeliveryStatus())
                .deliveryStartTime(delivery.getCreatedTime())
                .deliveryTakenTime(delivery.getDeliveryTakenTime())
                .deliveryDepartureRoadLocation(delivery.getDeliveryDepartureRoadLocation())
                .deliveryDestinationRoadLocation(delivery.getDeliveryDestinationRoadLocation())
                .build();
    }

}
