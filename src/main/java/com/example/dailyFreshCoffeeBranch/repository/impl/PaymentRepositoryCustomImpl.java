package com.example.dailyFreshCoffeeBranch.repository.impl;

import com.example.dailyFreshCoffeeBranch.entity.Payment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.dailyFreshCoffeeBranch.entity.QPayment.payment;

@RequiredArgsConstructor
public class PaymentRepositoryCustomImpl implements PaymentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Payment> findByMemberId(Long memberId, Pageable pageable) {
        List<Payment> content = queryFactory.selectFrom(payment)
                .where(payment.member.id.eq(memberId))
                .orderBy(payment.modifiedTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory.select(payment.count()).
                from(payment)
                .where(payment.member.id.eq(memberId))
                .fetchOne();

        return new PageImpl<>(content, pageable, count);
    }

}