package com.example.demo.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.HashMap;
import java.util.Map;



@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(StudentException.class)
    public ResponseEntity<String> handleStudentNotFound(StudentException ex) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ex.getMessage()); //ex is the ambulance that carries the exception and all its information to the handler! 
    }


//validation error handling
@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<Map<String,String>> handleValidationErrors(MethodArgumentNotValidException ex) {
    Map<String,String> errors = new HashMap<>();

    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
        errors.put(error.getField(),error.getDefaultMessage());
    }
    return ResponseEntity
    .status(HttpStatus.BAD_REQUEST)
    .body(errors);
}
}
