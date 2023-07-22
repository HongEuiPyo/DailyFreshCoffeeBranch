package com.example.dailyFreshCoffeeBranch.repository.impl;

import com.example.dailyFreshCoffeeBranch.entity.Comment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.example.dailyFreshCoffeeBranch.entity.QComment.comment;

@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Comment> findAllByBoardIdWithPage(Pageable pageable, Long boardId) {

        List<Comment> content = queryFactory.selectFrom(comment)
                .where(comment.board.id.eq(boardId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int total = content.size();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Optional<Comment> findByBoardIdAndCommentId(Long boardId, Long commentId) {

        Comment content = queryFactory.selectFrom(comment)
                .where(comment.board.id.eq(boardId).and(comment.id.eq(commentId)))
                .fetchOne();

        return Optional.ofNullable(content);
    }

}