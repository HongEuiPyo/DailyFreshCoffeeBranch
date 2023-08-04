package com.example.dailyFreshCoffeeBranch.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter @Setter
public class CommentResponseDto {

    private Long id;
    private String content;
    private String writer;
    private String writerEmail;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;
    private Long boardId;

    @QueryProjection
    public CommentResponseDto(Long id, String content, String writer, String writerEmail, LocalDateTime createdTime, LocalDateTime modifiedTime, Long boardId) {
        this.id = id;
        this.content = content;
        this.writer = writer;
        this.writerEmail = writerEmail;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
        this.boardId = boardId;
    }

}