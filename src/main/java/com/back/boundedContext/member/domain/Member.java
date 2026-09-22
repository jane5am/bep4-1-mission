package com.back.boundedContext.member.domain;

import com.back.shared.member.domain.SourceMember;
import com.back.shared.member.dto.MemberDto;
import com.back.shared.member.event.MemberModifiedEvent;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name="MEMBER_MEMBER")
public class Member extends SourceMember {

    public Member(String username, String password, String nickname) {
      super(username, password, nickname);
    }

  public MemberDto toDto() {
    return new MemberDto(
            getId(),
            getCreateDate(),
            getModifyDate(),
            getUsername(),
            getNickname(),
            getActivityScore()
    );
  }

  public int increaseActivityScore(int amount) {
      if (amount == 0) return getActivityScore(); // 0 점일때는 굳이 이벤트 발행해서 그 0점을 복사하는 불필요한 작업 없애기위함

      setActivityScore(getActivityScore() + amount);
      publishEvent(
              new MemberModifiedEvent(toDto())
      );
      return getActivityScore();
    }
}