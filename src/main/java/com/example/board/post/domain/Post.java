package com.example.board.post.domain;

import com.example.board.author.domain.Author;
import com.example.board.common.domain.BaseTimeEntity;
import com.example.board.post.dtos.PostListRes;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@ToString
@AllArgsConstructor
@Builder
@Entity
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String title;
    @Column(length = 3000)
    private String contents;
    private String appointment;
    private LocalDateTime appointmentTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    public PostListRes postListfromEntity(){
        return PostListRes.builder().id(this.id).title(this.title).authorEmail(this.author.getEmail()).build();
    }
}
