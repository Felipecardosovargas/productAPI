package com.teach.productAPI.exeptions;

public class GlobalExceptionHandler extends RuntimeException {
  public GlobalExceptionHandler(String message) {
    super(message);
  }
}
