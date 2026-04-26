package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.model.Department;
import com.example.demo.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    // Constructor injection
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // GET all departments
    @GetMapping
    public ResponseEntity<ApiResponse<List<Department>>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(new ApiResponse<>(200, "Departments fetched successfully!", departments));
    }

    // GET department by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Department>> getDepartmentById(@PathVariable Integer id) {
        Department department = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Department fetched successfully!", department));
    }

    // POST - add department
    @PostMapping
    public ResponseEntity<ApiResponse<Department>> addDepartment(@RequestBody Department department) {
        Department saved = departmentService.addDepartment(department.getName());
        return ResponseEntity.ok(new ApiResponse<>(201, "Department created successfully!", saved));
    }

    // DELETE - remove department
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteDepartment(@PathVariable Integer id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Department deleted successfully!", null));
    }
}