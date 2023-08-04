package com.example.dailyFreshCoffeeBranch.dto;

import com.example.dailyFreshCoffeeBranch.constant.DeliveryStatus;
import com.example.dailyFreshCoffeeBranch.domain.Delivery;
import lombok.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class DeliveryFormDto {

    private Long id;

    private Long memberId;

    private String memberEmail;

    private String memberName;

    private DeliveryStatus deliveryStatus;

    private Page<DeliveryItemFormDto> deliveryItemList;

    private LocalDateTime deliveryStartTime;

    private long deliveryTakenTime;

    private String deliveryDepartureRoadLocation;

    private String deliveryDestinationRoadLocation;

    public static DeliveryFormDto of(Delivery delivery) {
        return DeliveryFormDto.builder()
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

    public static DeliveryFormDto create(Delivery delivery, Page<DeliveryItemFormDto> deliveryItemDtoList) {
        return DeliveryFormDto.builder()
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