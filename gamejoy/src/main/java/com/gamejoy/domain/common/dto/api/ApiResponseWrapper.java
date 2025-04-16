package com.gamejoy.domain.common.dto.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Generic wrapper for successful API responses.
 *
 * <p>This class is used to standardize successful responses across the API.
 * It contains the actual data payload, an optional success message, and
 * a success flag that is always set to true.</p>
 */
@Data
@Schema(description = "Standard API response for successful requests")
public class ApiResponseWrapper<T> {

    // Optional - could be removed
    @Schema(description = "Indicates whether the request was successful", example = "true")
    private final boolean success = true;

    @Schema(description = "Data object of the response")
    private T data;

    @Schema(description = "Optional message", example = "User created successfully")
    private String message;

    public ApiResponseWrapper(T data, String message) {
        this.data = data;
        this.message = message;
    }
}
