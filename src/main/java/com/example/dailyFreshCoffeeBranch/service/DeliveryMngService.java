package com.example.dailyFreshCoffeeBranch.service;

import com.example.dailyFreshCoffeeBranch.dto.DeliveryFormDto;
import com.example.dailyFreshCoffeeBranch.dto.DeliveryItemFormDto;
import com.example.dailyFreshCoffeeBranch.domain.Delivery;
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
    public Page<DeliveryFormDto> getMemberDeliveryMngList(Pageable pageable) {
        return deliveryRepository.findAllPage(pageable)
                .map(d -> DeliveryFormDto.of(d));
    }

    /**
     * 회원 배송 관리 상세
     *
     * @param id
     * @param pageable
     * @return
     */
    public DeliveryFormDto getMemberDeliveryMngDetail(Long id, Pageable pageable) {

        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new DeliveryNotFoundException("배송을 조회할 수 없습니다."));

        Page<DeliveryItemFormDto> deliveryItemDtoPage = deliveryItemRepository.findByDeliveryId(id, pageable)
                .map(DeliveryItemFormDto::of);

        return DeliveryFormDto.create(delivery, deliveryItemDtoPage);
    }

}