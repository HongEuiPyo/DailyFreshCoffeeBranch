package com.example.dailyFreshCoffeeBranch.dto;

import com.example.dailyFreshCoffeeBranch.domain.Board;
import com.example.dailyFreshCoffeeBranch.domain.Comment;
import com.example.dailyFreshCoffeeBranch.domain.Member;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class CommentFormDto {

    private Long id;

    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String content;

    private String writer;

    private String writerEmail;

    private LocalDateTime createdTime;

    private Long boardId;

    public Comment toEntity(Member member, Board board) {
        return Comment.builder()
                .id(id)
                .content(content)
                .member(member)
                .board(board)
                .build();
    }

}
