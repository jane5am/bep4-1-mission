package com.back.boundedContext.member.domain;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class MemberPolicy {

  // 이런 정책은 별도의 클래스로 분리해야 한다.
  private static final int PASSWORD_CHANGE_DAYS = 90;

  public Duration getNeedToChangePasswordPeriod() {
    return Duration.ofDays(PASSWORD_CHANGE_DAYS);
  }

  public int getNeedToChangePasswordDays() {
    return PASSWORD_CHANGE_DAYS;
  }

  public boolean isNeedToChangePassword(LocalDateTime lastChangeDate) {
    if (lastChangeDate == null) return true;

    return lastChangeDate.plusDays(PASSWORD_CHANGE_DAYS)
            .isBefore(LocalDateTime.now());
  }

}
