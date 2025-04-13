package com.gamejoy.config.exceptionHandler;

import com.gamejoy.domain.general.dto.ApiError;
import com.gamejoy.domain.general.dto.ErrorDto;
import com.gamejoy.domain.general.exceptions.AppException;
import com.gamejoy.domain.user.exceptions.UserAlreadyExistsException;
import com.gamejoy.domain.user.exceptions.UserNotFoundException;
import com.gamejoy.domain.user.exceptions.InvalidPasswordException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @Deprecated
    @ExceptionHandler(value = {AppException.class })
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(AppException exception) {
        return ResponseEntity.status(exception.getHttpStatus())
                .body(new ErrorDto(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex,
                                                               HttpServletRequest request) {
        String message = "Validation failed";
        String details = String.format("Requested URI: %s", request.getRequestURI());
        String errorCode = "VALIDATION_FAILED";
        String timestamp = Instant.now().toString();

        // Create a validation error list
        List<String> validationErrors = ex.getBindingResult().getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());

        log.error("Validation failed: {}, Request Details: {}", validationErrors, details, ex);

        return new ResponseEntity<>(new ApiError(
                400,
                message,
                validationErrors,
                details,
                timestamp,
                errorCode
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException exception,
                                                       HttpServletRequest request) {
        // Alternative with ServletWebRequest - request.getDescription(false) (shows URI path with more information)
        return createApiError(exception, request, 404, HttpStatus.NOT_FOUND, "USER_NOT_FOUND");
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiError> handleInvalidPassword(InvalidPasswordException exception, HttpServletRequest request) {
        return createApiError(exception, request, 400, HttpStatus.BAD_REQUEST, "INVALID_PASSWORD");
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleUserAlreadyExists(UserNotFoundException exception,
                                                       HttpServletRequest request) {
        // Alternative with ServletWebRequest - request.getDescription(false) (shows URI path with more information)
        return createApiError(exception, request, 409, HttpStatus.UNPROCESSABLE_ENTITY, "USER_ALREADY_EXISTS");
    }

    private ResponseEntity<ApiError> createApiError(Exception exception, HttpServletRequest request,
                                                    int statusCode, HttpStatus httpStatus, String errorCode) {
        String details = String.format("Requested URI: %s", request.getRequestURI());
        String timestamp = Instant.now().toString();
        log.error("Exception occurred: {}, Request Details: {}", exception.getMessage(), details, exception);

        return new ResponseEntity<>(new ApiError(
                statusCode,
                exception.getMessage(),
                Collections.<String>emptyList(),
                details,
                timestamp,
                errorCode
        ), httpStatus);
    }
}
