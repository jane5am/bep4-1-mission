package com.back.boundedContext.member.in;

import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.member.app.MemberService;
import com.back.shared.post.event.PostCommentCreatedEvent;
import com.back.shared.post.event.PostCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;
import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

@Component
@RequiredArgsConstructor
public class MemberEventListener {

  private final MemberService memberService;

  // 동일한 이름으로 해서 오버로드 형태로 구현

  // 글작성 시 점수 3점을 올린다
  @TransactionalEventListener(phase = AFTER_COMMIT) // 리스너로서 기능하기위한 어노테이션
  @Transactional(propagation = REQUIRES_NEW)
  public void handle(PostCreatedEvent event) {
    Member member = memberService.findById(event.getPost().getAuthorId()).get();
    member.increaseActivityScore(3);
  }

  // 댓글작성 시 점수 1점을 올린다
  @TransactionalEventListener(phase = AFTER_COMMIT)
  @Transactional(propagation = REQUIRES_NEW)
  public void handle(PostCommentCreatedEvent event) {
    Member member = memberService.findById(event.getPostComment().getAuthorId()).get();
    member.increaseActivityScore(1);
  }
}
