package com.example.dailyFreshCoffeeBranch.service;

import com.example.dailyFreshCoffeeBranch.dto.BoardDto;
import com.example.dailyFreshCoffeeBranch.entity.Board;
import com.example.dailyFreshCoffeeBranch.entity.Member;
import com.example.dailyFreshCoffeeBranch.repository.BoardRepository;
import com.example.dailyFreshCoffeeBranch.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    /**
     * 공지사항 목록
     * @return
     */
    public Page<BoardDto> getBoardList(Pageable pageable){
        return boardRepository.findAllWithPaging(pageable)
                .map(board -> board.toDto());
    }

    /**
     * 공지사항 목록 CNT
     * @return
     */
    public long getBoardListCnt(){
        return boardRepository.count();
    }

    /**
     * 공지사항 상세
     * @param boardId
     * @return
     */
    @Transactional
    public BoardDto findBoardByBoardId(Long boardId){
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("could not find board" + boardId));
        board.increaseView();
        return board.toDto();
    }

    /**
     * 공지사항 등록
     * @param boardDto
     * @param email
     * @return
     */
    @Transactional
    public BoardDto createBoard(BoardDto boardDto, String email){
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("could not find user" + email));
        Board board = boardDto.toEntity(member);
        Board savedBoard = boardRepository.save(board);
        BoardDto result = savedBoard.toDto();
        return result;
    }

    /**
     * 공지사항 수정
     * @param boardDto
     * @param email
     * @return
     */
    public BoardDto updateBoard(Long boardId, BoardDto boardDto, String email) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()  -> new RuntimeException("could not find board" + boardId));
        if (board.getMember().getEmail().equals(email)) {
            board.update(boardDto);
            boardRepository.save(board);
        } else {
            throw new RuntimeException("공지사항의 글쓴이만 게시글 수정권한이 있습니다." + email);
        }
        return board.toDto();
    }

    /**
     * 공지사항 삭제
     * @param boardId
     */
    public void deleteBoard(Long boardId) { boardRepository.deleteById(boardId); }

}