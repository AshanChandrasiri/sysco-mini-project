package com.sysco.miniproject.shared.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorTranslator {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {

        String msg = ex.getBindingResult().getAllErrors().stream().map(oE -> {
            String fieldName = ((FieldError) oE).getField();
            String errorMessage = oE.getDefaultMessage();
            return  fieldName + " : " + errorMessage;
        }).reduce((s, s2) -> s + "," + s2).get();

        ErrorResponse response = generateError(HttpStatus.BAD_REQUEST.value(), msg);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(GeneralException ex) {

        ErrorResponse response = generateError(ex.getCode(),ex.getMessage());
        return ResponseEntity.status(ex.getCode()).body(response);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {

        ErrorResponse response =  generateError(0, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    private ErrorResponse generateError(int code, String msg) {
        return new ErrorResponse(code, msg);
    }

}
