package com.example.dailyFreshCoffeeBranch.service;

import com.example.dailyFreshCoffeeBranch.dto.DeliveryDto;
import com.example.dailyFreshCoffeeBranch.dto.DeliveryItemDto;
import com.example.dailyFreshCoffeeBranch.entity.Delivery;
import com.example.dailyFreshCoffeeBranch.exception.DeliveryNotFoundException;
import com.example.dailyFreshCoffeeBranch.repository.DeliveryItemRepository;
import com.example.dailyFreshCoffeeBranch.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeliveryMngService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryItemRepository deliveryItemRepository;

    /**
     * 회원 배송 관리 조회
     *
     * @param pageable
     * @return
     */
    public Page<DeliveryDto> getMemberDeliveryMngList(Pageable pageable) {
        return deliveryRepository.findAllPage(pageable)
                .map(d -> DeliveryDto.of(d));
    }

    /**
     * 회원 배송 관리 상세
     *
     * @param id
     * @param pageable
     * @return
     */
    public DeliveryDto getMemberDeliveryMngDetail(Long id, Pageable pageable) {

        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new DeliveryNotFoundException("배송을 조회할 수 없습니다."));

        Page<DeliveryItemDto> deliveryItemDtoPage = deliveryItemRepository.findByDeliveryId(id, pageable)
                .map(di -> DeliveryItemDto.of(di));

        return DeliveryDto.create(delivery, deliveryItemDtoPage);
    }

}