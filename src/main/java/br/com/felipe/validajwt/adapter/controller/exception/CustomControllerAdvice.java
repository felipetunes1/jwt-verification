package br.com.felipe.validajwt.adapter.controller.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomControllerAdvice extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler({ ValidateJwtHttpException.class })
    public ResponseEntity<?> validaJwtException(ValidateJwtHttpException input){
        return ResponseEntity.status(412).body(input.errors);
    }
    
}
