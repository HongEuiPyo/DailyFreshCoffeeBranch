package com.example.dailyFreshCoffeeBranch.api;

import com.example.dailyFreshCoffeeBranch.annotation.LoginUser;
import com.example.dailyFreshCoffeeBranch.dto.CommentFormDto;
import com.example.dailyFreshCoffeeBranch.dto.LoginUserDto;
import com.example.dailyFreshCoffeeBranch.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CommentApiController {

    private final CommentService commentService;


    /**
     * 댓글 등록
     *
     * @param id
     * @param commentFormDto
     * @param loginUserDto
     * @return
     */
    @PostMapping("/boards/{id}/comments/create")
    public ResponseEntity<CommentFormDto> createComment(
            @PathVariable Long id,
            @RequestBody CommentFormDto commentFormDto,
            @LoginUser LoginUserDto loginUserDto
    ) {
        CommentFormDto comment = commentService.createComment(loginUserDto.getEmail(), id, commentFormDto);

        return ResponseEntity.ok(comment);
    }

    /**
     * 공지사항 댓글 수정
     *
     * @param boardId
     * @param commentId
     * @param commentFormDto
     * @return
     */
    @PutMapping("/boards/{boardId}/comments/{commentId}/update")
    public ResponseEntity<CommentFormDto> updateComment
    (
            @PathVariable Long boardId,
            @PathVariable Long commentId,
            @RequestBody CommentFormDto commentFormDto
    ) {
        CommentFormDto updatedCommentFormDto = commentService.updateComment(boardId, commentId, commentFormDto);

        return ResponseEntity.ok(updatedCommentFormDto);
    }

    /**
     * 공지사항 댓글 삭제
     * @param boardId
     * @param commentId
     * @return
     */
    @DeleteMapping("/boards/{boardId}/comments/{commentId}/delete")
    public ResponseEntity<Map<String, String>> deleteComment(
            @PathVariable Long boardId,
            @PathVariable Long commentId
    ) {
        Map<String, String> resultMap = new HashMap<>();

        commentService.deleteComment(boardId, commentId);

        resultMap.put("msg", "댓글이 성공적으로 삭제되었습니다.");

        return ResponseEntity.ok(resultMap);
    }

}