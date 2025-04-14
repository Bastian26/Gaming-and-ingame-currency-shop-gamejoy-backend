package com.gamejoy.config.exceptionHandler;

import com.gamejoy.domain.general.dto.ApiError;
import com.gamejoy.domain.general.dto.ErrorDto;
import com.gamejoy.domain.general.exceptions.AppException;
import com.gamejoy.domain.user.exceptions.UserAlreadyExistsException;
import com.gamejoy.domain.user.exceptions.UserNotFoundException;
import com.gamejoy.domain.user.exceptions.InvalidPasswordException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Instant;
import java.util.*;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {AppException.class })
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(AppException exception) {
        return ResponseEntity.status(exception.getHttpStatus())
                .body(new ErrorDto(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException exception,
                                                               HttpServletRequest request) {
        return createErrorResponse(exception, request, ErrorCode.VALIDATION_FAILED, true);
    }

    // At the moment not used - but stays as example here
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException exception,
                                                       HttpServletRequest request) {
        // Alternative with ServletWebRequest - request.getDescription(false) (shows URI path with more information)
        return createErrorResponse(exception, request, ErrorCode.USER_NOT_FOUND, false);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiError> handleInvalidPassword(InvalidPasswordException exception, HttpServletRequest request) {
        return createErrorResponse(exception, request, ErrorCode.INVALID_PASSWORD, false);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleUserAlreadyExists(UserAlreadyExistsException exception,
                                                       HttpServletRequest request) {
        // Alternative with ServletWebRequest - request.getDescription(false) (shows URI path with more information)
        return createErrorResponse(exception, request, ErrorCode.USER_ALREADY_EXISTS, false);
    }

    private ResponseEntity<ApiError> createErrorResponse(Exception exception, HttpServletRequest request, ErrorCode errorCode,
      boolean failedValidation) {

        String details = String.format("Requested URI: %s", request.getRequestURI());
        String timestamp = Instant.now().toString();
        List<String> validationErrors = Collections.emptyList();

        if (failedValidation) {
            validationErrors = ((MethodArgumentNotValidException) exception).getBindingResult().getAllErrors().stream()
              .map(DefaultMessageSourceResolvable::getDefaultMessage)
              .toList();

            log.error("Validation failed: {}, Request Details: {}", validationErrors, details, exception);
        } else {
            log.error("Exception occurred: {}, Request Details: {}", exception.getMessage(), details, exception);
        }

        return new ResponseEntity<>(new ApiError(
                errorCode.getHttpStatus().value(),
                exception.getMessage(),
                validationErrors,
                details,
                timestamp,
                errorCode.getCode()
        ), errorCode.getHttpStatus());
    }
}

/**
 * Example return:
 * {
 *   "statusCode": 404,
 *   "message": "User not found",
 *   "validationErrors": [],
 *   "details": "Requested URI: /api/users/123",
 *   "timestamp": "2025-04-14T12:34:56Z",
 *   "errorCode": "USER_NOT_FOUND"
 * }
 */
