package com.gamejoy.domain.common.dto.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "Error response for unsuccessful requests")
public class ApiError {

    @Schema(description = "HTTP status code", example = "404")
    private int httpStatusCode;

    @Schema(description = "Error message", example = "User not found")
    private String message;

    @Schema(description = "Validation error list", example = "Validation failed")
    private List<String> validationErrors;

    @Schema(description = "Additional error details", example = "uri=/api/v1/users/1")
    private String details;

    @Schema(description = "Timestamp of the error", example = "2024-10-19T12:34:56.789Z")
    private String timestamp;

    @Schema(description = "Unique error code", example = "USER_NOT_FOUND")
    private String errorCode;
}

