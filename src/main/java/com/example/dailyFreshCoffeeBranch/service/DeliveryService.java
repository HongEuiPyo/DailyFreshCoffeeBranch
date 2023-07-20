package com.example.dailyFreshCoffeeBranch.service;

import com.example.dailyFreshCoffeeBranch.api.NaverMapApi;
import com.example.dailyFreshCoffeeBranch.dto.DeliveryDto;
import com.example.dailyFreshCoffeeBranch.dto.DeliveryItemDto;
import com.example.dailyFreshCoffeeBranch.dto.Directions5RequestDto;
import com.example.dailyFreshCoffeeBranch.entity.Delivery;
import com.example.dailyFreshCoffeeBranch.repository.DeliveryItemRepository;
import com.example.dailyFreshCoffeeBranch.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryItemRepository deliveryItemRepository;
    private final NaverMapApi naverMapApi;

    public DeliveryDto getMemberDeliveryList(String email) {
        Delivery delivery = deliveryRepository.findByMemberEmail(email);
        List<DeliveryItemDto> deliveryItemDtoList = deliveryItemRepository.findByDeliveryId(delivery.getId())
                .stream()
                .map(deliveryItem -> DeliveryItemDto.of(deliveryItem))
                .collect(Collectors.toList());

        Directions5RequestDto requestDto = new Directions5RequestDto();

        String jsonResponse = naverMapApi.directions5(requestDto);

        DeliveryDto dto = DeliveryDto.of(delivery);

        return null;
    }
}