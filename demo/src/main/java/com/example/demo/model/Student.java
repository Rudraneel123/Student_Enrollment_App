package com.example.demo.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //It tells PostgreSQL:
    //"YOU automatically generate the next id — don't make me do it manually!"
    private Integer id;
    @NotBlank(message ="Name cannot be empty!" )
    private String name;
    @NotNull(message = "Age cannot be empty!")
    @Min(value=1 ,message = "Age cannot be less than 1")
    @Max(value = 50,message = "Age cannot be more than 50")
    private Integer age;

    @ManyToOne
    @JoinColumn(name="department_id")
    @JsonBackReference
    private Department department;
    //JPA/Spring needs this to create an empty Student first then fills the fields from database or json

    public Student() {}

    // contructor
    //parameterised constructor,think of it as a prefilled form,most likely will be used in Service files
    public Student(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;

    }

    // getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Department getDepartment() {return department;}
    public void setDepartment(Department department) {this.department=department;}

}
