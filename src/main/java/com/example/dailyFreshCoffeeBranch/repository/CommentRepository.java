package com.example.dailyFreshCoffeeBranch.repository;

import com.example.dailyFreshCoffeeBranch.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {

    @Transactional
    @Modifying
    @Query("DELETE FROM Comment c WHERE c.board.id = :boardId AND c.id = :commentId")
    void deleteByBoardIdAndCommentId(@Param("boardId") Long boardId, @Param("commentId") Long commentId);

}