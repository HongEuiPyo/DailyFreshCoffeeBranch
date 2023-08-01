package com.example.dailyFreshCoffeeBranch.repository.impl;

import com.example.dailyFreshCoffeeBranch.dto.ItemSearchDto;
import com.example.dailyFreshCoffeeBranch.entity.Item;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.thymeleaf.util.StringUtils;

import java.util.List;

import static com.example.dailyFreshCoffeeBranch.entity.QImageFile.imageFile;
import static com.example.dailyFreshCoffeeBranch.entity.QItem.item;

@RequiredArgsConstructor
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        if (StringUtils.equals("title", searchBy)) {
            return item.title.like("%" + searchQuery + "%");
        } else if (StringUtils.equals("createdBy", searchBy)) {
            return item.createdBy.like("%" + searchQuery + "%");
        }
        return null;
    }

    @Override
    public List<Item> getTop3ItemList() {

        return queryFactory.selectFrom(item)
                .leftJoin(imageFile)
                .on(item.id.eq(imageFile.item.id))
                .where(imageFile.imageUrl.isNotNull())
                .orderBy(item.purchaseCnt.desc())
                .limit(3)
                .fetch();
    }

    @Override
    public List<Item> findAllSearching(ItemSearchDto searchDto) {

        return queryFactory.selectFrom(item)
                .orderBy(item.id.asc())
                .fetch();
    }

}