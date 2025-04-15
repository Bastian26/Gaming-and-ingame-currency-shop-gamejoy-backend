package com.gamejoy.domain.common.dto.api;

@Deprecated
public class ErrorDto {

    private String errorMessage;

    public ErrorDto(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
