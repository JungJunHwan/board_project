package com.example.board.author.controller;

import com.example.board.author.dtos.AuthorListRes;
import com.example.board.author.dtos.AuthorSaveReq;
import com.example.board.author.service.AuthorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {
    private final AuthorService authorService;
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/create")
    public String authorCreatePost(@Valid AuthorSaveReq authorSaveReq){
        authorService.save(authorSaveReq);
        return "ok";
    }

    @GetMapping("/list")
    public List<AuthorListRes> authorList(){
        return authorService.findAll();
    }
}
