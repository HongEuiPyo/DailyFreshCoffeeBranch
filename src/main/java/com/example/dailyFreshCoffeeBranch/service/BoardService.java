package com.example.dailyFreshCoffeeBranch.service;

import com.example.dailyFreshCoffeeBranch.dto.BoardFormDto;
import com.example.dailyFreshCoffeeBranch.dto.BoardResponseDto;
import com.example.dailyFreshCoffeeBranch.domain.Board;
import com.example.dailyFreshCoffeeBranch.domain.Member;
import com.example.dailyFreshCoffeeBranch.exception.BoardNotFoundException;
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
     *
     * @return
     */
    public Page<BoardResponseDto> getBoardList(Pageable pageable){
        return boardRepository.findAllWithPaging(pageable);
    }

    /**
     * 공지사항 목록 CNT
     *
     * @return
     */
    public long getBoardListCnt(){
        return boardRepository.count();
    }

    /**
     * 공지사항 상세
     *
     * @param boardId
     * @return
     */
    @Transactional
    public BoardFormDto findBoardByBoardId(Long boardId){
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("게시판을 찾을 수 없습니다. 관리자에게 문의해주시기 바랍니다."));
        board.increaseView();
        return board.toDto();
    }

    /**
     * 공지사항 등록
     *
     * @param boardFormDto
     * @param email
     * @return
     */
    @Transactional
    public BoardFormDto createBoard(BoardFormDto boardFormDto, String email){
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("could not find user" + email));
        Board board = boardFormDto.toEntity(member);
        Board savedBoard = boardRepository.save(board);
        BoardFormDto result = savedBoard.toDto();
        return result;
    }

    /**
     * 공지사항 수정
     *
     * @param boardFormDto
     * @param email
     * @return
     */
    public BoardFormDto updateBoard(Long boardId, BoardFormDto boardFormDto, String email) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()  -> new RuntimeException("could not find board" + boardId));
        if (board.getMember().getEmail().equals(email)) {
            board.update(boardFormDto);
            boardRepository.save(board);
        } else {
            throw new RuntimeException("공지사항의 글쓴이만 게시글 수정권한이 있습니다." + email);
        }
        return board.toDto();
    }

    /**
     * 공지사항 삭제
     *
     * @param boardId
     */
    public void deleteBoard(Long boardId) { boardRepository.deleteById(boardId); }

}