package com.example.board.author.dtos;

import com.example.board.author.domain.Author;
import com.example.board.author.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthorSaveReq {
    @NotEmpty
    private String name;
    @NotEmpty
    private String email;
    @Size(min = 8)
    @NotEmpty
    private String password;
//    사용자가 string으로 입력해도 Role 클래스로 자동 변환
//    ex) ADMIN, USER 등으로 입력시 Enum 클래스로 변환
    private Role role = Role.USER;

    public Author toEntity(){
        return Author.builder().name(this.name).email(this.email).password(this.password).role(this.role).build();
    }
}
