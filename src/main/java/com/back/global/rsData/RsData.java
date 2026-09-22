package com.back.global.rsData;

import com.back.standard.ResultType.ResultType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RsData<T> implements ResultType {
  private final String resultCode; // 200-1 (기본성공), 200-2(보내기 성공, 파일 첨부성공 등)
  private final String msg;
  private final T data;

  public RsData(String resultCode, String msg) { // 이건 data 가 없는 경우 
    this(resultCode, msg, null);
  }
}