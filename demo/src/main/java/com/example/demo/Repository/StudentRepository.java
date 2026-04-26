package com.example.demo.Repository;

import com.example.demo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
}

/*
 * JpaRepository<WHAT to store, HOW to find it>
 * ↓ ↓
 * Student Integer
 * (store Students) (find by Integer id)
 */