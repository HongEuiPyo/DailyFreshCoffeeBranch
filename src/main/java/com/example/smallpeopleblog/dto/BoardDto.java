package com.example.smallpeopleblog.dto;

import com.example.smallpeopleblog.entity.Board;
import com.example.smallpeopleblog.entity.Member;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class BoardDto {

    private Long id;

    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String content;

    private int view;
    private String writer;
    private List<CommentDto> commentDtoList;
    private LocalDateTime createdTime;


    public Board toEntity(Member member) {
        return Board.builder()
                .id(id)
                .title(title)
                .content(content)
                .view(view)
                .member(member)
                .build();
    }
}
