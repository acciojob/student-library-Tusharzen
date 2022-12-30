package com.example.library.studentlibrary.controller;

import com.example.library.studentlibrary.models.Student;
import com.example.library.studentlibrary.services.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Add required annotations
@RestController
public class StudentController {

    //Add required annotations
    @Autowired
    StudentService studentService ;

    @GetMapping("/student/studentByEmail/")
    public ResponseEntity<String> getStudentByEmail(@RequestParam("email") String email){
        Student student = studentService.getDetailsByEmail(email) ;
        return new ResponseEntity<>(student.toString(), HttpStatus.OK);
        //"Student details printed successfully ."+
    }

    //Add required annotations
    @GetMapping("/student/studentById/")
    public ResponseEntity<String> getStudentById(@RequestParam("id") int id){
        Student student = studentService.getDetailsById(id);
        return new ResponseEntity<String>(student.toString(), HttpStatus.OK);
        //"Student details printed successfully "+ 
    }

    //Add required annotations
    @PostMapping("/student/")
    public ResponseEntity<String> createStudent(@RequestBody Student student){

        studentService.createStudent(student);
        return new ResponseEntity<String>("the student is successfully added to the system", HttpStatus.CREATED);
    }

    //Add required annotations
    @PutMapping("/student/")
    public ResponseEntity<String> updateStudent(@RequestBody Student student){

        studentService.updateStudent(student);
        return new ResponseEntity<>("student is updated", HttpStatus.ACCEPTED);
    }

    //Add required annotations
    @DeleteMapping("/student/")
    public ResponseEntity<String> deleteStudent(@RequestParam("id") int id){

        studentService.deleteStudent(id);
        return new ResponseEntity<String>("student is deleted", HttpStatus.ACCEPTED);
    }

}
