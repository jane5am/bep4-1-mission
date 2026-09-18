package com.back.boundedContext.post.domain;

import com.back.global.jpa.entity.BaseIdAndTimeManual;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name="POST_MEMBER")
public class PostMember extends BaseIdAndTimeManual {

  @Column(unique = true)
  private String username;
  private String password;
  private String nickname;
  private int activityScore;

}