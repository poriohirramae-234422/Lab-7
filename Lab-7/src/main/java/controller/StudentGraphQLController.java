package com.porio.Lab7.controller;

import com.porio.Lab7.model.Student;
import com.porio.Lab7.service.StudentService;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class StudentGraphQLController {

    private final StudentService service;

    public StudentGraphQLController(StudentService service) {
        this.service = service;
    }

    @QueryMapping
    public List<Student> students() {
        return service.getAllStudents();
    }

    @MutationMapping
    public Student createStudent(String firstName, String lastName, int year, String course) {
        Student student = Student.builder()
                .firstName(firstName)
                .lastName(lastName)
                .year(year)
                .course(course)
                .build();
        return service.saveStudent(student);
    }
}