package com.example.dailyFreshCoffeeBranch.dto;

import com.example.dailyFreshCoffeeBranch.entity.Board;
import com.example.dailyFreshCoffeeBranch.entity.Member;
import lombok.*;

import javax.validation.constraints.AssertTrue;
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

    private String content;

    private int view;
    private String writer;
    private List<CommentDto> commentDtoList;
    private LocalDateTime createdTime;


    @AssertTrue(message = "내용은 필수 입력 값입니다.")
    public boolean isContentBlank() {
        if (content == null) {
            return false;
        } else if (content == "") {
            return false;
        }else if (content.equals("<p>&nbsp;</p>")) {
            return false;
        }
        return true;
    }

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