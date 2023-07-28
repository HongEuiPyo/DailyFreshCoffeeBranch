package com.example.dailyFreshCoffeeBranch.service;

import com.example.dailyFreshCoffeeBranch.constant.DeliveryItemStatus;
import com.example.dailyFreshCoffeeBranch.constant.DeliveryStatus;
import com.example.dailyFreshCoffeeBranch.dto.DeliveryItemDto;
import com.example.dailyFreshCoffeeBranch.entity.Delivery;
import com.example.dailyFreshCoffeeBranch.entity.DeliveryItem;
import com.example.dailyFreshCoffeeBranch.exception.DeliveryNotFoundException;
import com.example.dailyFreshCoffeeBranch.repository.DeliveryItemRepository;
import com.example.dailyFreshCoffeeBranch.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DeliveryItemApiService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryItemRepository deliveryItemRepository;


    /**
     * 배송 상품 상태 변경
     *
     * @param deliveryId
     * @param deliveryItemId
     * @param requestDto
     */
    @Transactional
    public void updateStatus(Long deliveryId, Long deliveryItemId, DeliveryItemDto requestDto) {
        List<DeliveryItem> deliveryItemList = deliveryItemRepository.updateDeliveryItemStatus(deliveryItemId, requestDto.getDeliveryItemStatus());
        Delivery deliveryEntity = deliveryRepository.findById(deliveryId).orElseThrow(() -> new DeliveryNotFoundException("배송을 조회할 수 없습니다."));
        checkIfDeliveryIsDelivered(deliveryId, deliveryItemList, deliveryEntity.getDeliveryStatus()); // 모든 배송 상품 배송 여부 체크
    }

    /**
     * 모든 배송 상품 배송 여부 체크
     *
     * @param deliveryId
     * @param deliveryItemEntityList
     */
    private void checkIfDeliveryIsDelivered(Long deliveryId, List<DeliveryItem> deliveryItemEntityList, DeliveryStatus status) {
        for (DeliveryItem deliveryItemEntity : deliveryItemEntityList) {
            int deliveredCnt = 0;

            if (DeliveryItemStatus.DELIVERED.equals(deliveryItemEntity.getDeliveryItemStatus())) {
                deliveredCnt += 1;
            }

            if (deliveredCnt == deliveryItemEntityList.size()) {
                deliveryRepository.getDelivered(deliveryId);
            } else if (DeliveryStatus.DELIVERED.equals(status)) {
                deliveryRepository.getDelivering(deliveryId);
            }
        }
    }
}