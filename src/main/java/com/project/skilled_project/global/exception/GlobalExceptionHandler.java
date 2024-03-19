package com.project.skilled_project.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j(topic = "GlobalExceptionHandler -> ")
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public void methodArgumentNotValidException(
      MethodArgumentNotValidException e
  ) {

    log.error("MethodArgumentNotValidException: ", e);
  }
}
