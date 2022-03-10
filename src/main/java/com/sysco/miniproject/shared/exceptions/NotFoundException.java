package com.sysco.miniproject.shared.exceptions;

import static com.sysco.miniproject.shared.Constants.NETWORK_ERROR_CODES.NOT_FOUND;

public class NotFoundException extends GeneralException {

    public NotFoundException(String message, Object... args) {
        super(NOT_FOUND.getValue(), String.format(message, args));
    }
}