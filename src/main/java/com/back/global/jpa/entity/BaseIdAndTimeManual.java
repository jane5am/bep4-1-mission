package com.back.global.jpa.entity;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public abstract class BaseIdAndTimeManual extends BaseEntity {

  // @GeneratedValue 빼기
  @Id
  private int id;
  // 날짜 자동 생성 수정, 어노테이션 빼기
  private LocalDateTime createDate;
  private LocalDateTime modifyDate;
}