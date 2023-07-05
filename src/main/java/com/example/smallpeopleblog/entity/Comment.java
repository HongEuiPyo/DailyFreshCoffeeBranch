package com.example.smallpeopleblog.entity;

import com.example.smallpeopleblog.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "comment")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Comment extends BaseEntity {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public CommentDto toDto() {
        return CommentDto.builder()
                .id(id)
                .content(content)
                .writer(member.getName())
                .writerEmail(member.getEmail())
                .createdTime(getCreatedTime())
                .build();
    }

    public Comment update(CommentDto commentDto) {
        this.content = commentDto.getContent();
        return this;
    }
}
