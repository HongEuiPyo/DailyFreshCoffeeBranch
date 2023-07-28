package com.example.dailyFreshCoffeeBranch.service;

import com.example.dailyFreshCoffeeBranch.dto.MemberPointUpDto;
import com.example.dailyFreshCoffeeBranch.dto.PaymentDto;
import com.example.dailyFreshCoffeeBranch.entity.Member;
import com.example.dailyFreshCoffeeBranch.entity.Payment;
import com.example.dailyFreshCoffeeBranch.exception.MemberNotFoundException;
import com.example.dailyFreshCoffeeBranch.repository.MemberRepository;
import com.example.dailyFreshCoffeeBranch.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final MemberRepository memberRepository;
    private final PaymentRepository paymentRepository;

    /**
     * 구매 상품 목록 조회하기
     *
     * @param email
     * @return
     */
    public Page<PaymentDto> getPaymentList(String email, Pageable pageable) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("회원을 조회할 수 없습니다."));
        Page<Payment> paymentPage = paymentRepository.findByMemberId(member.getId(), pageable);
        return paymentPage.map(Payment::toDto);
    }

    /**
     * 포인트 충전하기
     *
     * @param email
     * @param memberPointUpDto
     * @return
     */
    @Transactional
    public void addPoint(String email, MemberPointUpDto memberPointUpDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("회원을 조회할 수 없습니다."));
        member.addPoint(memberPointUpDto.getPoint());
    }

}