package com.avinya.application.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.avinya.application.model.ExceptionResponse;

import jakarta.validation.ValidationException;

/**
 * @implNote This is Advice class to handle exception thrown by application and
 *           wrap into custom message
 *
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(CustomerRewardProgramException.class)
  public final ResponseEntity<ExceptionResponse>
    handleCustomerRewardProgramExceptions(final CustomerRewardProgramException exception, final WebRequest request) {
    final ExceptionResponse exceptionResponse =
      new ExceptionResponse(LocalDateTime.now(), exception.getMessage(), request.getDescription(false));

    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ValidationException.class)
  public final ResponseEntity<ExceptionResponse> handleValidationExceptions(final ValidationException exception,
    final WebRequest request) {
    final ExceptionResponse exceptionResponse =
      new ExceptionResponse(LocalDateTime.now(), exception.getMessage(), request.getDescription(false));

    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CustomerNotFoundException.class)
  public final ResponseEntity<ExceptionResponse>
    handleCustomerNotFoundException(final CustomerNotFoundException exception, final WebRequest request) {
    final ExceptionResponse exceptionResponse =
      new ExceptionResponse(LocalDateTime.now(), exception.getMessage(), request.getDescription(false));

    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public final ResponseEntity<ExceptionResponse>
    handleUsernameNotFoundException(final UsernameNotFoundException exception, final WebRequest request) {
    final ExceptionResponse exceptionResponse =
      new ExceptionResponse(LocalDateTime.now(), exception.getMessage(), request.getDescription(false));

    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }

}
