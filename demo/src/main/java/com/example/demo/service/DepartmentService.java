package com.example.demo.service;
import com.example.demo.Repository.DepartmentRepository;
import com.example.demo.exception.StudentException;
import com.example.demo.model.Department;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository  departmentRepository){
        this.departmentRepository=departmentRepository;
    }
     
      // GET all departments
      public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    //GET department BY ID
    public Department getDepartmentById(Integer id) {
        return departmentRepository.findById(id).orElseThrow(()-> new StudentException(id));
    }

    //POST- ADD department
    public Department addDepartment(String name) {
        Department department = new Department(null,name);
        return departmentRepository.save(department);
    }

    //DELETE - REMOVE department
    public boolean deleteDepartment(Integer id) {
        if (departmentRepository.existsById(id)){
            departmentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}


