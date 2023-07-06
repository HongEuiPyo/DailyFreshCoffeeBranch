package com.example.smallpeopleblog.controller;

import com.example.smallpeopleblog.service.CommentService;
import com.example.smallpeopleblog.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class CommentController {

    private final CommentService commentService;

    /**
     * 공지사항 댓글 목록
     * @param id
     * @param pageable
     * @return
     */
    @GetMapping("/boards/{id}/comments")
    public String getBoardComments(@PathVariable Long id, Pageable pageable, Model model) {
        Page<CommentDto> commentDtoPage = commentService.getBoardCommentsByBoardId(id, pageable);
        model.addAttribute("commentDtoPage", commentDtoPage);
        return "comment/commentList";
    }

}