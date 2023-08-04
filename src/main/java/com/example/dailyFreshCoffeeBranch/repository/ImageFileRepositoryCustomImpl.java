package com.example.dailyFreshCoffeeBranch.repository;

import com.example.dailyFreshCoffeeBranch.domain.ImageFile;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.dailyFreshCoffeeBranch.domain.QImageFile.imageFile;

@RequiredArgsConstructor
public class ImageFileRepositoryCustomImpl implements ImageFileRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ImageFile> findAllByItemId(Long itemId) {

        return queryFactory.selectFrom(imageFile)
                .where(imageFile.item.id.eq(itemId))
                .fetch();
    }

    @Override
    public void deleteByItemId(Long itemId) {
        queryFactory.delete(imageFile)
                .where(imageFile.item.id.eq(itemId))
                .execute();
    }

}