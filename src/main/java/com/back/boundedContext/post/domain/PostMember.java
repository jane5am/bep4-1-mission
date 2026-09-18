package com.back.boundedContext.post.domain;

import com.back.shared.member.domain.ReplicaMember;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name="POST_MEMBER")
public class PostMember extends ReplicaMember {
  public PostMember(int id, LocalDateTime createDate, LocalDateTime modifyDate, String username, String password, String nickname, int activityScore) {
    super(id, createDate, modifyDate, username, password, nickname, activityScore); // super를 통해 부모 필드로 가서 생성자 생성
  }

}