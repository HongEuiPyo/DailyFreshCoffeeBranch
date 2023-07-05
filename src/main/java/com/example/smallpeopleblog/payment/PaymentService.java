package com.example.smallpeopleblog.payment;

import com.example.smallpeopleblog.cart.CartRepository;
import com.example.smallpeopleblog.cartItem.CartItemRepository;
import com.example.smallpeopleblog.dto.MemberDto;
import com.example.smallpeopleblog.dto.PaymentDto;
import com.example.smallpeopleblog.entity.Cart;
import com.example.smallpeopleblog.entity.Member;
import com.example.smallpeopleblog.entity.Payment;
import com.example.smallpeopleblog.exception.CartNotFoundException;
import com.example.smallpeopleblog.exception.MemberNotFoundException;
import com.example.smallpeopleblog.exception.OutOfPointException;
import com.example.smallpeopleblog.member.MemberRepository;
import com.example.smallpeopleblog.policy.VIPDiscountPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final PaymentRepository paymentRepository;
    private final VIPDiscountPolicy vipDiscountPolicy;

    /**
     * 구매 목록
     * @param email
     * @return
     */
    public List<PaymentDto> getPaymentList(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("회원을 조회할 수 없습니다."));

        List<Payment> paymentList = paymentRepository.findByMemberId(member.getId());

        return paymentList.stream()
                .map(payment -> payment.toDto())
                .collect(Collectors.toList());
    }

    /**
     * 포인트 충전
     * @param email
     * @param memberDto
     * @return
     */
    @Transactional
    public MemberDto addPoint(String email, MemberDto memberDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("회원을 조회할 수 없습니다."));

        member.addPoint(memberDto.getPoint());

        return member.toDto();
    }

    /**
     * 장바구니 상품 구매 확정
     * @param email
     * @param paymentDto
     */
    @Transactional
    public void confirmCartItemPurchase(String email, PaymentDto paymentDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("회원을 조회할 수 없습니다."));

        double applyPrice = vipDiscountPolicy.apply(paymentDto.getTotalUsePoint(), member.getRole()); // VIP 할인 정책 적용

        if (member.getPoint() < applyPrice) {
            throw new OutOfPointException("포인트가 부족합니다.");
        }

        member.payPoint(applyPrice); // 결제

        if (applyPrice != paymentDto.getTotalUsePoint()) {
            paymentDto.setDiscount(vipDiscountPolicy.getDiscountRate());
        }

        paymentRepository.save(paymentDto.toEntity(member)); // 구매 목록 저장

        Cart cart = cartRepository.findByMemberId(member.getId())
                .orElseThrow(() -> new CartNotFoundException("장바구니를 찾을 수 없습니다."));

        cartItemRepository.deleteAll(cart.getCartItemList()); // 장바구니 상품 목록 삭제
    }

}