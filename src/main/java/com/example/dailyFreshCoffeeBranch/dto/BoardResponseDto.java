package com.example.dailyFreshCoffeeBranch.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter @Setter
public class BoardResponseDto {

    private Long id;
    private String title;
    private String content;
    private int view;
    private String writer;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;


    @QueryProjection
    public BoardResponseDto(Long id, String title, String content, int view, String writer, LocalDateTime createdTime, LocalDateTime modifiedTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.view = view;
        this.writer = writer;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }

}