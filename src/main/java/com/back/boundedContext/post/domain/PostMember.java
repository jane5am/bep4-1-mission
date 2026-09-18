package com.back.boundedContext.post.domain;

import com.back.shared.domain.ReplicaMember;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name="POST_MEMBER")
public class PostMember extends ReplicaMember {
  public PostMember(String username, String password, String nickname) {
    super(username, password, nickname); // super를 통해 부모 필드로 가서 생성자 생성
  }
}