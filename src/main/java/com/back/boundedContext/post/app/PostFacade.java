package com.back.boundedContext.post.app;

import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.post.domain.Post;
import com.back.boundedContext.post.domain.PostMember;
import com.back.boundedContext.post.out.PostMemberRepository;
import com.back.boundedContext.post.out.PostRepository;
import com.back.global.rsData.RsData;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostFacade {

  private final PostRepository postRepository;
  private final PostWriteUseCase postWriteUseCase;
  private final PostMemberRepository postMemberRepository;

  // 엄연히 따지면 얘네도 유즈케이스가 맞는데 너무 작은기능이라 여기다가 했음
  @Transactional(readOnly = true)
  public long count() {
    return postRepository.count();
  }

  @Transactional(readOnly = true)
  public Optional<Post> findById(int id) {
    return postRepository.findById(id);
  }

  @Transactional
  public RsData<Post> write(Member author, String title, String content){
    return postWriteUseCase.write(author, title, content);
  }

  @Transactional
  public PostMember syncMember(MemberDto member) {
    PostMember _member = new PostMember(
            member.getId(),
            member.getCreateDate(),
            member.getModifyDate(),
            member.getUsername(),
            "",
            member.getNickname(),
            member.getActivityScore()
    );

    return postMemberRepository.save(_member);
  }
}
