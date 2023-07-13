package com.example.dailyFreshCoffeeBranch.entity;

import com.example.dailyFreshCoffeeBranch.dto.BoardDto;
import com.example.dailyFreshCoffeeBranch.dto.CommentDto;
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

    public BoardDto toDto() {
        List<CommentDto> commentDtoList = new ArrayList();
        if (comments!=null) {
            commentDtoList = comments.stream().map(Comment::toDto).collect(Collectors.toList());
        }

        return BoardDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .view(view)
                .writer(member.getName())
                .commentDtoList(commentDtoList)
                .createdTime(getCreatedTime())
                .build();
    }

    public void update(BoardDto boardDto) {
        title = boardDto.getTitle();
        content = boardDto.getContent();
    }

    public void increaseView() {
        this.view += 1;
    }
}