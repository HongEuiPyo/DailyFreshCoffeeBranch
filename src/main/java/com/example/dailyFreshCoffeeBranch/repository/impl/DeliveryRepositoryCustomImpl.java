package com.example.dailyFreshCoffeeBranch.repository.impl;

import com.example.dailyFreshCoffeeBranch.entity.Delivery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.example.dailyFreshCoffeeBranch.entity.QDelivery.delivery;


@RequiredArgsConstructor
public class DeliveryRepositoryCustomImpl implements DeliveryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Delivery> findByMemberEmail(String email, Pageable pageable) {

        List<Delivery> content = queryFactory.selectFrom(delivery)
                .where(delivery.member.email.eq(email))
                .orderBy(delivery.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory.select(delivery.count())
                .from(delivery)
                .where(delivery.member.email.eq(email))
                .fetchOne();

        return new PageImpl<>(content, pageable, count);
    }

    @Override
    public Optional<Delivery> findByMemberEmailAndId(String email, Long id) {

        Delivery content = queryFactory.selectFrom(delivery)
                .where(delivery.member.email.eq(email).and(delivery.id.eq(id)))
                .fetchOne();

        return Optional.ofNullable(content);
    }

    @Override
    public Page<Delivery> findAllPage(Pageable pageable) {

        List<Delivery> content = queryFactory.selectFrom(delivery)
                .orderBy(delivery.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory.select(delivery.count())
                .from(delivery)
                .fetchOne();

        return new PageImpl<>(content, pageable, count);
    }
}