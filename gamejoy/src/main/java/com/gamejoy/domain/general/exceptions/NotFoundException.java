package com.gamejoy.domain.general.exceptions;

@Deprecated
public class NotFoundException extends RuntimeException{

    public NotFoundException(String message) {
        super(message);
    }
}
