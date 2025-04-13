package com.gamejoy.domain.general.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Standard API response for successful requests")
public class ApiResponseWrapper<T> {

    @Schema(description = "Indicates whether the request was successful", example = "true")
    private boolean success;

    @Schema(description = "Data object of the response")
    private T data;

    @Schema(description = "Optional message", example = "User created successfully")
    private String message;
}
