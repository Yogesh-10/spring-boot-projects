package com.springboot.blog.exception;

import com.springboot.blog.payload.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //Handle specific exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException exception,
                                                                  WebRequest webRequest){
        ErrorMessage errorMessage = new ErrorMessage(new Date(), HttpStatus.NOT_FOUND,
                exception.getMessage(), webRequest.getDescription(false));

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogApiException.class)
    public ResponseEntity<ErrorMessage> blogAPIException(BlogApiException exception,
                                                                  WebRequest webRequest){
        ErrorMessage errorMessage = new ErrorMessage(new Date(), HttpStatus.BAD_REQUEST,
                exception.getMessage(), webRequest.getDescription(false));

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    //Handle global exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGlobalException(Exception exception,
                                                         WebRequest webRequest){
        ErrorMessage errorMessage = new ErrorMessage(new Date(), HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(), webRequest.getDescription(false));

        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //validation Exception
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        Map<String, String> body = new HashMap<>();

        ex.getBindingResult().getAllErrors()
                .forEach(error -> {
                    String fieldName = ((FieldError)error).getField();
                    String message = error.getDefaultMessage();
                    body.put(fieldName, message);
                });

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}
