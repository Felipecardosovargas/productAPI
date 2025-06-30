package com.teach.productAPI.exeptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorDetails> handleResourceNotFoundExeption(ResourceNotFoundException ex, WebRequest request) {
    ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(),
            ex.getMessage(),
            request.getDescription(false),
            HttpStatus.NOT_FOUND.value()
    );
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage()));

    ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(),
            "Erro de validação",
            request.getDescription(false),
            HttpStatus.BAD_REQUEST.value(),
            errors
    );
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
    ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(),
            ex.getMessage(),
            request.getDescription(false),
            HttpStatus.INTERNAL_SERVER_ERROR.value()
    );
    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Data
  public static class ErrorDetails {
    private LocalDateTime timestamp;
    private String message;
    private String details;
    private int status;
    private Map<String, String> errors;

    public ErrorDetails(LocalDateTime timestamp, String message, String details, int status) {
      this.timestamp = timestamp;
      this.message = message;
      this.details = details;
      this.status = status;
    }

    public ErrorDetails(LocalDateTime timestamp, String message, String details, int status, Map<String, String> errors) {
      this.timestamp = timestamp;
      this.message = message;
      this.details = details;
      this.status = status;
      this.errors = errors;
    }
  }
}
