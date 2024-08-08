package com.example.dailyFreshCoffeeBranch.repository;

import com.example.dailyFreshCoffeeBranch.dto.BoardResponseDto;
import com.example.dailyFreshCoffeeBranch.dto.QBoardResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.dailyFreshCoffeeBranch.domain.QBoard.board;

@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public Page<BoardResponseDto> findAllWithPaging(Pageable pageable) {
        List<BoardResponseDto> result = queryFactory
                .select(new QBoardResponseDto(
                        board.id
                        , board.title
                        , board.content
                        , board.view
                        , board.member.name
                        , board.createdTime
                        , board.modifiedTime))
                .from(board)
                .orderBy(board.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory.select(board.count())
                .from(board)
                .fetchOne();

        return new PageImpl<>(result, pageable, count);
    }

}