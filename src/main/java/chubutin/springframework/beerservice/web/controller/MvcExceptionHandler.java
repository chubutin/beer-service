package chubutin.springframework.beerservice.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@EnableWebMvc
@RestControllerAdvice
public class MvcExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List> constraintViolationExceptionHandler(ConstraintViolationException e){
        List<String> errors = new ArrayList<>(e.getConstraintViolations().size());
        e.getConstraintViolations().forEach(constraintViolation -> {
            errors.add(constraintViolation.getPropertyPath() + " : " + constraintViolation.getMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {

        List<String> errors = new ArrayList<>();

        List<FieldError> fieldErrors = e.getBindingResult().getAllErrors().stream()
                .map(FieldError.class::cast)
                .collect(Collectors.toList());

        fieldErrors.forEach(fieldError -> {

            errors.add( String.format("Bad Request %s : %s : Rejected value : ---> %s"
                    ,fieldError.getField()
                    ,fieldError.getDefaultMessage()
                    ,fieldError.getRejectedValue()));
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity generalErrorHandler(Exception e){
        System.out.println("General handler error");
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}

