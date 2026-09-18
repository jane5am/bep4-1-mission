package com.back.global.jpa.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
public abstract class BaseManualIdAndTime extends BaseEntity {

  // id 자동 증감은 없음. 아래 에서 기존 생성자 함수를 넘겨받으면 그걸로 초기화되게 했음.
  @Id
  private int id;
  @CreatedDate
  private LocalDateTime createDate;
  @LastModifiedDate
  private LocalDateTime modifyDate;

  public BaseManualIdAndTime(int id) {
    this.id = id;
  }
}
