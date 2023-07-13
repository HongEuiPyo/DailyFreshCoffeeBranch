package com.example.dailyFreshCoffeeBranch.repository;

import com.example.dailyFreshCoffeeBranch.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.board.id = :boardId ORDER BY c.id desc")
    Page<Comment> findAllByBoardIdWithPage(Pageable pageable, @Param("boardId") Long boardId);

    @Query("SELECT c FROM Comment c WHERE c.board.id = :boardId AND c.id = :commentId")
    Optional<Comment> findByBoardIdAndCommentId(@Param("boardId") Long boardId, @Param("commentId") Long commentId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Comment c WHERE c.board.id = :boardId AND c.id = :commentId")
    void deleteByBoardIdAndCommentId(@Param("boardId") Long boardId, @Param("commentId") Long commentId);
}