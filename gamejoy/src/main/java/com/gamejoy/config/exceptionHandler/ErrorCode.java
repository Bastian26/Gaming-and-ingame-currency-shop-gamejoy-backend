package com.gamejoy.config.exceptionHandler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

  USER_NOT_FOUND(HttpStatus.NOT_FOUND,"USER_NOT_FOUND"),                // 404 - NOT FOUND - doesn't exist
  USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "USER_ALREADY_EXISTS"),      // 409 - CONFLICT - request was correct, but ressource conflict
  INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "INVALID_PASSWORD"),         // 400 - BAD_REQUEST - insert invalid data (password)
  VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "VALIDATION_FAILED");       // 400 - BAD_REQUEST - insert invalid data (failed validation of some kind)

  private final HttpStatus httpStatus;
  private final String code;

  ErrorCode(HttpStatus httpStatus, String code) {
    this.httpStatus = httpStatus;
    this.code = code;
  }
}
