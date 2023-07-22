package com.example.dailyFreshCoffeeBranch.repository.impl;

import com.example.dailyFreshCoffeeBranch.entity.DeliveryItem;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.dailyFreshCoffeeBranch.entity.QDeliveryItem.deliveryItem;

@RequiredArgsConstructor
public class DeliveryItemRepositoryCustomImpl implements DeliveryItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<DeliveryItem> findByDeliveryId(Long deliveryId, Pageable pageable) {

        List<DeliveryItem> content = queryFactory.selectFrom(deliveryItem)
                .where(deliveryItem.id.eq(deliveryId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int total = content.size();

        return new PageImpl<>(content, pageable, total);
    }
}