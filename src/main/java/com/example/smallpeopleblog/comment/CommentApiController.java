package com.example.smallpeopleblog.comment;

import com.example.smallpeopleblog.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CommentApiController {

    private final CommentService commentService;

    /**
     * 댓글 등록
     * @param id
     * @param commentDto
     * @param principal
     * @return
     */
    @PostMapping("/boards/{id}/comments/create")
    public ResponseEntity<CommentDto> createComment(@PathVariable Long id, @RequestBody CommentDto commentDto, Principal principal) {
        return ResponseEntity.ok(commentService.createComment(principal.getName(), id, commentDto));
    }

    /**
     * 공지사항 댓글 수정
     * @param boardId
     * @param commentId
     * @param commentDto
     * @return
     */
    @PutMapping("/boards/{boardId}/comments/{commentId}/update")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long boardId, @PathVariable Long commentId, @RequestBody CommentDto commentDto) {
        CommentDto updatedCommentDto = commentService.updateComment(boardId, commentId, commentDto);
        return ResponseEntity.ok(updatedCommentDto);
    }

    /**
     * 공지사항 댓글 삭제
     * @param boardId
     * @param commentId
     * @return
     */
    @DeleteMapping("/boards/{boardId}/comments/{commentId}/delete")
    public ResponseEntity<String> deleteComment(@PathVariable Long boardId, @PathVariable Long commentId) {
        commentService.deleteComment(boardId, commentId);
        return ResponseEntity.ok("댓글이 성공적으로 삭제되었습니다.");
    }

}