package com.example.board.author.domain;

import com.example.board.author.dtos.AuthorListRes;
import com.example.board.common.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@ToString
@AllArgsConstructor
@Builder
@Entity
public class Author extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20, nullable = false)
    private String name;
    @Column(length = 30, unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
//    enum은 기본적으로 숫자값으로 db에 들어감으로, 별도록 STRING 지정 필요
    @Enumerated(EnumType.STRING)
    private Role role;

    public Author(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public AuthorListRes listDtoFromEntity(){
        return AuthorListRes.builder().id(this.id).email(this.email).name(this.name).build();
    }
}
