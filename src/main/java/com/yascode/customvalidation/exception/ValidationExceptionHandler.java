package com.yascode.customvalidation.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> notValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, Map<String, List<String>>> result = new HashMap<>();

        ex.getAllErrors().forEach(error -> {
            String fieldError = Stream.of(error.getCodes()[0].split("\\.")).reduce((first, second) -> second).orElse("");

            result.computeIfAbsent("errors", key -> new HashMap<>())
                    .computeIfAbsent(fieldError, key -> new ArrayList<>())
                    .add(error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(result);
    }

    /*@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> notValid(MethodArgumentNotValidException ex, HttpServletRequest request) {

        Map<String, Map<String, List<String>>> result = new HashMap<>();

        Map<String, List<String>> errors = new HashMap<>();

        ex.getAllErrors().forEach(error -> {
            String[] split = error.getCodes()[0].split("\\.");

            String fieldError = split[split.length - 1];

            if(errors.get(fieldError) == null) {
                List<String> defaultMessage = new ArrayList<>();
                defaultMessage.add(error.getDefaultMessage());
                errors.put(fieldError, defaultMessage);
            } else {
                List<String> defaultMessages = errors.get(fieldError);
                defaultMessages.add(error.getDefaultMessage());
            }
        });

        result.put("errors", errors);

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }*/



}
