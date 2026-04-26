package com.example.demo.exception;

public class StudentException extends RuntimeException {
    public StudentException(Integer id) {
        super("Student with id "+ id + " not found");
    }
}
