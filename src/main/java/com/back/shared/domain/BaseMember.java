package com.back.shared.domain;

import com.back.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static lombok.AccessLevel.PROTECTED;

@MappedSuperclass
@Getter
@Setter(value = PROTECTED)
@NoArgsConstructor
public abstract class BaseMember extends BaseEntity {
  @Column(unique = true)
  private String username;
  private String password;
  private String nickname;
  private int activityScore;

  // activityScore 제거된 버젼
  public BaseMember(String username, String password, String nickname) {
    this.username = username;
    this.password = password;
    this.nickname = nickname;
  }
}