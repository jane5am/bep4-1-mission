package com.back.boundedContext.post.app;

import com.back.boundedContext.member.app.MemberFacade;
import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.post.domain.Post;
import com.back.boundedContext.post.out.PostRepository;
import com.back.global.eventPublisher.EventPublisher;
import com.back.global.rsData.RsData;
import com.back.shared.post.dto.PostDto;
import com.back.shared.post.event.PostCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostWriteUseCase {

  private final PostRepository postRepository;
  private final EventPublisher eventPublisher;
  private final MemberFacade memberFacade; // 모듈간의 결합도가 생김!! 이러면 안됨

  public RsData<Post> write(Member author, String title, String content) {
    Post post = postRepository.save(new Post(author, title, content)); // 저장먼저!
    eventPublisher.publish(new PostCreatedEvent(new PostDto(post)));
    String randomSecureTip = memberFacade.getRandomSecureTip();

    return new RsData<>(
            "201-1",
            "%d번 글이 생성되었습니다. 보안 팁 : %s"
                    .formatted(post.getId(), randomSecureTip),
            post
    );
  }

}
