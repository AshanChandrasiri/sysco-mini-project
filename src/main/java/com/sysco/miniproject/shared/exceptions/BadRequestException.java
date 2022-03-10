package com.sysco.miniproject.shared.exceptions;


import static com.sysco.miniproject.shared.Constants.NETWORK_ERROR_CODES.BAD_REQUEST;

public class BadRequestException extends GeneralException {

    public BadRequestException(String message, Object... args) {
        super(BAD_REQUEST.getValue(), String.format(message, args));
    }
}

