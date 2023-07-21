package com.example.dailyFreshCoffeeBranch.service;

import com.example.dailyFreshCoffeeBranch.dto.DeliveryDto;
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

    /**
     * 회원 배송 목록 조회하기
     *
     * @param email
     * @return
     */
    public List<DeliveryDto> getMemberDeliveryList(String email) {
        List<Delivery> deliveryList = deliveryRepository.findByMemberEmail(email);

        return deliveryList.stream()
                .map(d -> DeliveryDto.of(d))
                .collect(Collectors.toList());
    }

    public DeliveryDto getMemberDeliveryDetail(String email, Long id) {
        List<Delivery> deliveryList = deliveryRepository.findByMemberEmail(email);

//        List<DeliveryItemDto> deliveryItemDtoList = deliveryItemRepository.findByDeliveryId(delivery.getId())
//                .stream()
//                .map(DeliveryItemDto::of)
//                .collect(Collectors.toList());

        return null;
    }
}