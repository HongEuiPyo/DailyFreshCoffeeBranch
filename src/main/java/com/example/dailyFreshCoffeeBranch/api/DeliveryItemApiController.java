package com.example.dailyFreshCoffeeBranch.api;

import com.example.dailyFreshCoffeeBranch.dto.DeliveryItemDto;
import com.example.dailyFreshCoffeeBranch.service.DeliveryItemApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class DeliveryItemApiController {

    private final DeliveryItemApiService deliveryItemApiService;


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/deliveries/{deliveryId}/deliveryItems/{deliveryItemId}/updateStatus")
    public ResponseEntity<?> updateStatus(@PathVariable Long deliveryId, @PathVariable Long deliveryItemId, DeliveryItemDto requestDto) {
        try {

            deliveryItemApiService.updateStatus(deliveryId, deliveryItemId, requestDto);

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        HashMap<String, String> resultMap = new HashMap<>();
        resultMap.put("status", "ok");
        resultMap.put("msg", "배송 상품 상태 변경을 완료하였습니다.");

        return ResponseEntity.ok().body(resultMap);
    }
}