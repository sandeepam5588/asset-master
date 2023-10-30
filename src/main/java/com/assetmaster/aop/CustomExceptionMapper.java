package com.assetmaster.aop;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionMapper extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ex.printStackTrace();

        List<String> messages = new ArrayList<>();
        messages.add(Error.messages.get(Error.S_001));

        List<String> codes = new ArrayList<>();
        codes.add(Error.S_001);

        ErrorResponse error = new ErrorResponse(codes, messages);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(RuntimeException ex, WebRequest request) {
        ex.printStackTrace();

        List<String> messages = new ArrayList<>();
        messages.add(Error.messages.get(ex.getLocalizedMessage()));

        List<String> codes = new ArrayList<>();
        codes.add(ex.getMessage());

        ErrorResponse error = new ErrorResponse(codes, messages);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public final ResponseEntity<Object> handleBusinessValidationException(RuntimeException ex, WebRequest request) {
        ex.printStackTrace();
        List<String> messages = new ArrayList<>();
        messages.add(Error.messages.get(ex.getLocalizedMessage()));

        List<String> codes = new ArrayList<>();
        codes.add(ex.getMessage());

        ErrorResponse error = new ErrorResponse(codes, messages);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ex.printStackTrace();

        List<String> messages = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            messages.add(Error.messages.get(error.getDefaultMessage()));
            errors.add(error.getDefaultMessage());
        }

        ErrorResponse error = new ErrorResponse(errors, messages);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
