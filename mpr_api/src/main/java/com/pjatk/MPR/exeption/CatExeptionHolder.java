package com.pjatk.MPR.exeption;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CatExeptionHolder extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CatNotFoundExeption.class)
    protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(CatAlreadyExistsExeption.class)
    protected ResponseEntity<String> handleBadRequest(RuntimeException ex, WebRequest request){
        return  ResponseEntity.badRequest().body(ex.getMessage());
    }


}
