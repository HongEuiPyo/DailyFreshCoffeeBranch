package com.example.dailyFreshCoffeeBranch.dto;

import com.example.dailyFreshCoffeeBranch.constant.DeliveryStatus;
import com.example.dailyFreshCoffeeBranch.entity.Delivery;
import lombok.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class DeliveryDto {

    private Long id;

    private Long memberId;

    private String memberEmail;

    private String memberName;

    private DeliveryStatus deliveryStatus;

    private Page<DeliveryItemDto> deliveryItemList;

    private LocalDateTime deliveryStartTime;

    private long deliveryTakenTime;

    private String deliveryDepartureRoadLocation;

    private String deliveryDestinationRoadLocation;

    public static DeliveryDto of(Delivery delivery) {
        return DeliveryDto.builder()
                .id(delivery.getId())
                .memberId(delivery.getMember().getId())
                .memberEmail(delivery.getMember().getEmail())
                .memberName(delivery.getMember().getName())
                .deliveryStatus(delivery.getDeliveryStatus())
                .deliveryStartTime(delivery.getCreatedTime())
                .deliveryTakenTime(delivery.getDeliveryTakenTime())
                .deliveryDepartureRoadLocation(delivery.getDeliveryDepartureRoadLocation())
                .deliveryDestinationRoadLocation(delivery.getDeliveryDestinationRoadLocation())
                .build();
    }

    public static DeliveryDto create(Delivery delivery, Page<DeliveryItemDto> deliveryItemDtoList) {
        return DeliveryDto.builder()
                .id(delivery.getId())
                .memberId(delivery.getMember().getId())
                .memberEmail(delivery.getMember().getEmail())
                .memberName(delivery.getMember().getName())
                .deliveryStatus(delivery.getDeliveryStatus())
                .deliveryStartTime(delivery.getCreatedTime())
                .deliveryTakenTime(delivery.getDeliveryTakenTime())
                .deliveryDepartureRoadLocation(delivery.getDeliveryDepartureRoadLocation())
                .deliveryDestinationRoadLocation(delivery.getDeliveryDestinationRoadLocation())
                .deliveryItemList(deliveryItemDtoList)
                .build();
    }

}