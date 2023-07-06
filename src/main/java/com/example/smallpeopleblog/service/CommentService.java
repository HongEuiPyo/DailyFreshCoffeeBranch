package com.example.smallpeopleblog.service;

import com.example.smallpeopleblog.repository.BoardRepository;
import com.example.smallpeopleblog.dto.CommentDto;
import com.example.smallpeopleblog.entity.Board;
import com.example.smallpeopleblog.entity.Comment;
import com.example.smallpeopleblog.entity.Member;
import com.example.smallpeopleblog.exception.CommentNotFoundException;
import com.example.smallpeopleblog.repository.MemberRepository;
import com.example.smallpeopleblog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    /**
     * 댓글 등록
     * @param email
     * @param boardId
     * @param commentDto
     * @return
     */
    @Transactional
    public CommentDto createComment(String email, Long boardId, CommentDto commentDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("could not find user" + email));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("could not find board" + boardId));
        Comment comment = commentDto.toEntity(member, board);
        return commentRepository.save(comment).toDto();
    }

    /**
     * 공지사항 댓글 목록
     * @param boardId
     * @param pageable
     * @return
     */
    public Page<CommentDto> getBoardCommentsByBoardId(Long boardId, Pageable pageable) {
        Page<Comment> commentPage = commentRepository.findAllByBoardIdWithPage(pageable, boardId);
        if (commentPage!=null) {
            return commentPage.map(comment -> comment.toDto());
        }
        return null;
    }

    /**
     * 공지사항 댓글 수정
     * @param boardId
     * @param commentId
     * @param commentDto
     * @return
     */
    @Transactional
    public CommentDto updateComment(Long boardId, Long commentId, CommentDto commentDto) {
        Comment comment = commentRepository.findByBoardIdAndCommentId(boardId, commentId)
                .orElseThrow(() -> new CommentNotFoundException("댓글 정보를 확인할 수 없습니다. 관리자에게 문의하시기 바랍니다."));
        Comment updatedComment = comment.update(commentDto);
        return updatedComment.toDto();
    }

    /**
     * 공지사항 댓글 삭제
     * @param boardId
     * @param commentId
     */
    public void deleteComment(Long boardId, Long commentId) {
        commentRepository.deleteByBoardIdAndCommentId(boardId, commentId);
    }
}
