package com.example.board.author.service;

import com.example.board.author.domain.Author;
import com.example.board.author.dtos.AuthorDetailRes;
import com.example.board.author.dtos.AuthorListRes;
import com.example.board.author.dtos.AuthorSaveReq;
import com.example.board.author.dtos.AuthorUpdateReq;
import com.example.board.author.repository.AuthorRepository;
import com.example.board.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final PostRepository postRepository;
    public AuthorService(AuthorRepository authorRepository, PostRepository postRepository) {
        this.authorRepository = authorRepository;
        this.postRepository = postRepository;
    }

    public void save(AuthorSaveReq authorSaveReq){
        if (authorRepository.findByEmail(authorSaveReq.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 이메일입니다");
        }
        authorRepository.save(authorSaveReq.toEntity());
////        cascade를 활용하지 않고, 별도로 post 데이터 만드는 경우
//        postRepository.save(Post.builder().title("반갑습니다.").contents("처음 뵙겠습니다.").author(author).build());

////        cascade를 활용해서, post 데이터를 함께 만드는 경우
//        Author author = Author.builder()
//                .name(authorSaveReq.getName())
//                .email(authorSaveReq.getEmail())
//                .password(authorSaveReq.getPassword())
//                .role(authorSaveReq.getRole())
//                .build();
////        post를 생성하는 시점에 author가 아직 db에 저정되지 않은것으로 보여지나, jpa가 author와 post를 저장하는 시점에 선후관계를 맞춰줌
//        author.getPosts().add(Post.builder().title("반갑습니다 1.").contents("처음 뵙겠습니다.").author(author).build());
//        author.getPosts().add(Post.builder().title("반갑습니다 2.").contents("처음 뵙겠습니다.").author(author).build());
//        authorRepository.save(author);
    }

    public List<AuthorListRes> findAll(){
        return authorRepository.findAll().stream().map(a->a.listDtoFromEntity()).collect(Collectors.toList());
    }

    public void delete(Long id){
        Author author = authorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("author is not found"));
        authorRepository.delete(author);
    }

    public AuthorDetailRes findById(Long id){
        Author author = authorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("author is not found"));
        return author.detailFromEntity();
    }

    public void update(Long id, AuthorUpdateReq authorUpdateReq){
        Author author = authorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("author is not found"));
        author.updateProfile(authorUpdateReq);
//        기존 객체에 변경이 발생할 경우, 별도의 save 없이도 jpa가 엔티티의 변경을 자동 인지하고, 변경사항을 DB에 반영
//        이를 dirtychecking이라 부르고, 반드시 transactional 어노테이션이 있을 경우 동작
//        authorRepository.save(author);
    }
}
