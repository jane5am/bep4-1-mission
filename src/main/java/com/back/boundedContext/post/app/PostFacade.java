package com.back.boundedContext.post.app;

import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.post.domain.Post;
import com.back.boundedContext.post.out.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostFacade {

  private final PostRepository postRepository;
  private final PostWriteUseCase postWriteUseCase;

  // 엄연히 따지면 얘네도 유즈케이스가 맞는데 너무 작은기능이라 여기다가 했음
  public long count() {
    return postRepository.count();
  }

  public Optional<Post> findById(int id) {
    return postRepository.findById(id);
  }

  public Post write(Member author, String title, String content){
    return postWriteUseCase.write(author, title, content);
  }

}
