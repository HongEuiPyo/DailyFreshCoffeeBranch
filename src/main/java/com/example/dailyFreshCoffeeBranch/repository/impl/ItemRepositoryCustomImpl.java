package com.example.dailyFreshCoffeeBranch.repository.impl;

import com.example.dailyFreshCoffeeBranch.entity.Item;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.dailyFreshCoffeeBranch.entity.QImageFile.imageFile;
import static com.example.dailyFreshCoffeeBranch.entity.QItem.item;

@RequiredArgsConstructor
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<Item> getTop3ItemList() {

        List<Item> result = queryFactory
                .selectFrom(item)
                .leftJoin(imageFile)
                .on(item.id.eq(imageFile.item.id))
                .where(imageFile.imageUrl.isNotNull())
                .orderBy(item.purchaseCnt.desc())
                .limit(3)
                .fetch();

        return result;
    }

}