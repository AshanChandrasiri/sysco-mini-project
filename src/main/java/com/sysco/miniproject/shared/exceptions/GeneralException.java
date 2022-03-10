package com.sysco.miniproject.shared.exceptions;


import com.sysco.miniproject.shared.Constants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneralException extends RuntimeException {

    private int code;

    public GeneralException(String message) {
        super(message);
        this.code = Constants.NETWORK_ERROR_CODES.SERVER_ERROR.getValue();
    }

    public GeneralException(int code, String message) {
        super(message);
        this.code = code;
    }


}
