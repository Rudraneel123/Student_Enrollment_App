package com.example.demo.service;

import com.example.demo.Repository.DepartmentRepository;
import com.example.demo.Repository.StudentRepository;
import com.example.demo.exception.StudentException;
import com.example.demo.model.Department;
import com.example.demo.model.Student;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class StudentService {

    // Only StudentService can use this remote!(studentRepository),final is used so
    // that the remote never changes

    // logger
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    //
    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;

    // constructor injection
    public StudentService(StudentRepository studentRepository, DepartmentRepository departmentRepository) {
        this.studentRepository = studentRepository;
        this.departmentRepository = departmentRepository;
    }

    // GET STUDENTS
    public List<Student> getAllStudents() {
        logger.info("Fetching all students");
        List<Student> students = studentRepository.findAll();
        logger.info("Total students found: {}", students.size());
        return students;
    }

    // Get all students by pagination and sorting
    public Page<Student> getAllStudentsWithPagination(int page, int size, String sortBy) {
        logger.info("Fetching students - page: {}, size: {}, sortBy: {}\", page, size, sortBy");
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return studentRepository.findAll(pageable);
    }

    // GET STUDENT BY ID
    public Student getStudentById(Integer id) {
        logger.info("Fetching student with id: {}", id);
        return studentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Student with id {} not found!", id);
                    return new StudentException(id);
                });
    }

    // POST- ADD STUDENT
    public Student addStudent(String name, Integer age, Department department) {
        logger.info("Adding new student with name: {}", name);
        Student student = new Student(null, name, age);

        if (department != null && department.getId() != null) {
            Department fullDepartment = departmentRepository.findById(department.getId())
                    .orElseThrow(() -> {
                        logger.error("Department with id {} not found!", department);
                        return new StudentException(department.getId());
                    });
            student.setDepartment(fullDepartment);

        }
        Student saved = studentRepository.save(student);
        logger.info("Student saved successfully with id: {}");
        return saved;
    }

    // PUT - UPDATE STUDENT
    public Student updateStudent(Integer id, String name, Integer age) {
        logger.info("Updating student with id: {}", id);
        Student student = studentRepository.findById(id).orElseThrow(() -> {
            logger.error("Student with id {} not found for update!", id);
            return new StudentException(id);
        });
        student.setName(name);
        student.setAge(age);
        logger.info("Student updated successfully with id: {}", id);
        return studentRepository.save(student);
    }

    // DELETE - REMOVE A STUDENT
    public boolean deleteStudent(Integer id) {
         logger.info("Deleting student with id: {}", id);
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
             logger.info("Student deleted successfully with id: {}", id);
            return true;
        }
        logger.warn("Student with id {} not found for deletion!", id);
        return false;
    }

}
