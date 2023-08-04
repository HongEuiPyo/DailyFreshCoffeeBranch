package com.example.dailyFreshCoffeeBranch.domain;

import com.example.dailyFreshCoffeeBranch.dto.BoardFormDto;
import com.example.dailyFreshCoffeeBranch.dto.CommentFormDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "board")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Board extends BaseEntity {

    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(columnDefinition = "integer default 0")
    private Integer view;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id desc")
    private List<Comment> comments;

    public BoardFormDto toDto() {
        List<CommentFormDto> commentFormDtoList = new ArrayList();
        if (comments!=null) {
            commentFormDtoList = comments.stream().map(Comment::toDto).collect(Collectors.toList());
        }

        return BoardFormDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .view(view)
                .writer(member.getName())
                .commentFormDtoList(commentFormDtoList)
                .createdTime(getCreatedTime())
                .build();
    }

    public void update(BoardFormDto boardFormDto) {
        title = boardFormDto.getTitle();
        content = boardFormDto.getContent();
    }

    public void increaseView() {
        this.view += 1;
    }
}