package com.back.standard.ResultType;

public interface ResultType {
  String getResultCode();

  String getMsg();

  default <T> T getData() {
    return null;
  }
}