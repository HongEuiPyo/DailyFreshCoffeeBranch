package com.example.smallpeopleblog.repository;

import com.example.smallpeopleblog.entity.Item;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.smallpeopleblog.entity.QImageFile.imageFile;
import static com.example.smallpeopleblog.entity.QItem.item;

@RequiredArgsConstructor
public class CustomItemQueryDslRepositoryImpl implements CustomItemQueryDslRepository{

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