package com.gamejoy.domain.common.dto.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Standard API response for successful requests")
public class ApiResponseWrapper<T> {

    @Schema(description = "Indicates whether the request was successful", example = "true")
    private boolean success = true;

    @Schema(description = "Data object of the response")
    private T data;

    @Schema(description = "Optional message", example = "User created successfully")
    private String message;

    public ApiResponseWrapper(T data, String message) {
        this.data = data;
        this.message = message;
    }
}
