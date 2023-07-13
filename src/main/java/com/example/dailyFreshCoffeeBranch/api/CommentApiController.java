package com.example.dailyFreshCoffeeBranch.api;

import com.example.dailyFreshCoffeeBranch.service.CommentService;
import com.example.dailyFreshCoffeeBranch.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;


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
        CommentDto comment = commentService.createComment(principal.getName(), id, commentDto);
        return ResponseEntity.ok(comment);
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
    public ResponseEntity<Map<String, String>> deleteComment(@PathVariable Long boardId, @PathVariable Long commentId) {
        Map<String, String> resultMap = new HashMap<>();

        commentService.deleteComment(boardId, commentId);

        resultMap.put("msg", "댓글이 성공적으로 삭제되었습니다.");

        return ResponseEntity.ok(resultMap);
    }

}