package com.example.dailyFreshCoffeeBranch.repository;

import com.example.dailyFreshCoffeeBranch.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT b FROM Board b WHERE b.id > 0 ORDER BY b.id desc")
    Page<Board> findAllWithPaging(Pageable pageable);

    @Query("SELECT b FROM Board b")
    List<Board> findAllBoard();

    @Query("SELECT b FROM Board b WHERE b.title=?1 AND b.member.id=?2")
    List<Board> findByTitleAndMemberId2(String title, Long memberId);

    @Query(value = "SELECT * FROM Board", nativeQuery = true) // SQL
    List<Board> findAllBoardBySQL();

    @Query(value = "SELECT TITLE, MEMBER FROM BOARD", nativeQuery = true) // SQL
    List<Object[]> findAllBoardBySQL2();

    // SELECT COUNT(*) FROM BOARD WHERE MEMBER_ID = memberId;
    int countAllByMemberId(Long memberId);

    // SELECT * FROM BOARD WHERE MEMBER_ID = memberId;
    List<Board> findByMemberId(Long memberId);

    // SELECT * FROM BOARD WHERE TITLE = title AND MEMBER_ID = memberId;
    List<Board> findByTitleAndMemberId(String title, Long memberId);

    // DELETE FROM BOARD WHERE MEMBER_ID = memberId;
    int deleteByMemberId(Long memberId);

}
