package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.example.demo.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    // constructor injection
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // get all students
    @GetMapping
    public ResponseEntity<ApiResponse<List<Student>>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(new ApiResponse<>(200, "Students fetched successfully", students));
    }

    //get all students by pagination and sorting
    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Page<Student>>> getAllStudentsWithPagination(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "name") String sortBy) {
            Page<Student> students = studentService.getAllStudentsWithPagination(page, size, sortBy);
            return ResponseEntity.ok(new ApiResponse<>(200, "Students fetched successfully with pagination!",students));
        }
    

    // get student by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> getStudentById(@PathVariable Integer id) {
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Student fetched successfully", student));

    }

    // add a new student
    @PostMapping
    public ResponseEntity<ApiResponse<Student>> addStudent(@Valid @RequestBody Student student) {
        Student added = studentService.addStudent(student.getName(), student.getAge(),student.getDepartment());
        return ResponseEntity.ok(new ApiResponse<>(201, "Student created successfully", added));

    }

    // update a new student
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> updateStudent(@PathVariable Integer id,
            @Valid @RequestBody Student student) {
        Student updated = studentService.updateStudent(id, student.getName(), student.getAge());
        return ResponseEntity.ok(new ApiResponse<>(200, "Student update successfully", updated));

    }

    // remove a student
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> deleteStudent(@PathVariable Integer id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Student update successfully", null));

    }
}
