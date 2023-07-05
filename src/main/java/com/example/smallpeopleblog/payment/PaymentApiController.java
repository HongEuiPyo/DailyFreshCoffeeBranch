package com.example.smallpeopleblog.payment;

import com.example.smallpeopleblog.dto.MemberDto;
import com.example.smallpeopleblog.dto.PaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class PaymentApiController {

    private final PaymentService paymentService;

    /**
     * 포인트 충전
     * @param memberDto
     * @param principal
     * @return
     */
    @PostMapping("/payment/addPoint")
    public ResponseEntity<?> addPoint(@RequestBody MemberDto memberDto, Principal principal) {
        MemberDto upMemberDto = paymentService.addPoint(principal.getName(), memberDto);
        return ResponseEntity.ok().body(upMemberDto);
    }

    /**
     * 장바구니 상품 구매 확정
     * @param paymentDto
     * @param principal
     * @return
     */
    @PostMapping("/payment/confirmCartItemPurchase")
    public ResponseEntity<?> confirmCartItemPurchase(@RequestBody PaymentDto paymentDto, Principal principal) {
        paymentService.confirmCartItemPurchase(principal.getName(), paymentDto);
        return ResponseEntity.ok().body("장바구니 상품 구매 확정을 완료하였습니다.");
    }

}
