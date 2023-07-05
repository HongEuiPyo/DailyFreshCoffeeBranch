package com.example.smallpeopleblog.board;

import com.example.smallpeopleblog.comment.CommentRepository;
import com.example.smallpeopleblog.dto.BoardDto;
import com.example.smallpeopleblog.entity.Board;
import com.example.smallpeopleblog.entity.Member;
import com.example.smallpeopleblog.member.MemberRepository;
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
    private final CommentRepository commentRepository;

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
        board.increaseView(); // 조회수 증가
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
    public BoardDto updateBoard(Long boardId, BoardDto boardDto, String email) throws Exception {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()  -> new RuntimeException("could not find board" + boardId));
        if (board.getMember().getEmail().equals(email)) {
            board.update(boardDto);
        } else {
            throw new RuntimeException("공지사항의 담당자가 아닌 관계로 수정이 불가능합니다." + email);
        }
        return board.toDto();
    }

    /**
     * 공지사항 삭제
     * @param boardId
     */
    public void deleteBoard(Long boardId) { boardRepository.deleteById(boardId); }

}