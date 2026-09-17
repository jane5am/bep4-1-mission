package com.back.boundedContext.member.app;

import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.member.out.MemberRepository;
import com.back.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberJoinUseCase {

  private final MemberRepository memberRepository;

  public Member join(String username, String password, String nickname) {
    findByUsername(username).ifPresent(m -> {
      throw new DomainException("409-1", "이미 존재하는 username 입니다.");
    });

    return memberRepository.save(new Member(username, password, nickname));
  }

  public Optional<Member> findByUsername(String username) {
    return memberRepository.findByUsername(username);
  }

}