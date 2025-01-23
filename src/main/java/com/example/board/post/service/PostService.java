package com.example.board.post.service;

import com.example.board.author.domain.Author;
import com.example.board.author.repository.AuthorRepository;
import com.example.board.post.domain.Post;
import com.example.board.post.dtos.PostDetailRes;
import com.example.board.post.dtos.PostListRes;
import com.example.board.post.dtos.PostSaveReq;
import com.example.board.post.dtos.PostUpdateReq;
import com.example.board.post.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;
    public PostService(PostRepository postRepository, AuthorRepository authorRepository) {
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
    }

    public void save(PostSaveReq postSaveReq){
        Author author = authorRepository.findByEmail(postSaveReq.getEmail())
                .orElseThrow(()->new EntityNotFoundException("author is not found"));
        LocalDateTime appointmentTime = null;
        if(postSaveReq.getAppointment().equals("Y")) {
            if (postSaveReq.getAppointmentTime().isEmpty() || postSaveReq.getAppointmentTime()==null) {
                throw new IllegalArgumentException("시간이 비어져 있습니다.");
            } else {
                appointmentTime = LocalDateTime.parse(postSaveReq.getAppointmentTime());
                LocalDateTime now = LocalDateTime.now();
                if (appointmentTime.isBefore(now)) {
                    throw new IllegalArgumentException("시간이 과거입니다.");
                }
            }
            postRepository.save(postSaveReq.toEntity(author, appointmentTime));
        }
    }

    public List<PostListRes> findAll(){
        return postRepository.findAll().stream().map(p->p.postListFromEntity()).collect(Collectors.toList());
    }

    public Page<PostListRes> findAllPaging(Pageable pageable){
        Page<Post> pagePosts = postRepository.findAllByAppointment(pageable, "N");
        return pagePosts.map(a->a.postListFromEntity());
    }

    public List<PostListRes> listFetchJoin(){
////        일반 join : author를 join해서 post를 조회하긴 하나, author의 데이터는 실제 참조할때 쿼리가 N+1번 발생
//        List<Post> postList = postRepository.findAllJoin();

//        fetch join : author를 join 해서 조회하고 author의 데이터도 join 시점에서 가져옴, 쿼리 1번 발생
        List<Post> postList = postRepository.findAllFetchJoin();
        return postList.stream()
                .map(p->p.postListFromEntity()).collect(Collectors.toList());
    }

    public PostDetailRes findById(Long id){
        Post post = postRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("post is not exist"));
        return post.postDetailFromEntity();
    }

    public void update(Long id, PostUpdateReq postUpdateReq){
        Post post = postRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("post is not exist"));
        post.updatePost(postUpdateReq);
    }

    public void delete(Long id){
        postRepository.deleteById(id);
    }
}
