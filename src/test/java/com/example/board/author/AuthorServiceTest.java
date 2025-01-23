package com.example.board.author;

import com.example.board.author.domain.Author;
import com.example.board.author.domain.Role;
import com.example.board.author.dtos.AuthorSaveReq;
import com.example.board.author.repository.AuthorRepository;
import com.example.board.author.service.AuthorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class AuthorServiceTest {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorService authorService;

    @Test
    public void authorSaveTest(){
        AuthorSaveReq authorSaveReq = new AuthorSaveReq("keum", "keum@naver.com", "1234", Role.ADMIN);
        authorService.save(authorSaveReq);

        Author author = authorRepository.findByEmail("keum@naver.com").orElse(null);
        Assertions.assertEquals(authorSaveReq.getEmail(), author.getEmail());
    }

    @Test
    public void findAllTest(){
        int beforeSize = authorService.findAll().size();
        authorRepository.save(Author.builder().name("aaa1").email("aaa1@naver.com").password("1234").build());
        authorRepository.save(Author.builder().name("aaa2").email("aaa2@naver.com").password("1234").build());
        authorRepository.save(Author.builder().name("aaa3").email("aaa3@naver.com").password("1234").build());
        int afterSize = authorService.findAll().size();
        Assertions.assertEquals(beforeSize+3, afterSize);
    }
}
