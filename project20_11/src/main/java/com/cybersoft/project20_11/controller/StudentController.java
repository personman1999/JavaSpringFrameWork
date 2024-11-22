package com.cybersoft.project20_11.controller;

import com.cybersoft.project20_11.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private List<Student> studentList = new ArrayList<>();


    public StudentController() {
        studentList.add(new Student("John", 20));
        studentList.add(new Student("Jane", 22));
    }

    // Thêm sinh viên bằng @RequestParam
    @PostMapping("/addParam")
    public ResponseEntity<?> addStudent(@RequestParam String name, @RequestParam int age) {
        Student newStudent = new Student(name, age);
        studentList.add(newStudent);
        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }

    // Thêm sinh viên bằng @PathVariable
    @PostMapping("/add/{name}/{age}")
    public ResponseEntity<?> addStudentWithPathVariable(@PathVariable String name, @PathVariable int age) {
        Student newStudent = new Student(name, age);
        studentList.add(newStudent);
        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }

    // Thêm sinh viên bằng @RequestBody
    @PostMapping("/addBody")
    public ResponseEntity<?> addStudentWithRequestBody(@RequestBody Student student) {
        studentList.add(student);
        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }

    // Lấy danh sách sinh viên
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }

}
