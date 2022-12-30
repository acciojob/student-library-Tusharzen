package com.example.library.studentlibrary.services;

import com.example.library.studentlibrary.models.Card;
import com.example.library.studentlibrary.models.Student;
import com.example.library.studentlibrary.repositories.CardRepository;
import com.example.library.studentlibrary.repositories.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {


    @Autowired
    CardService cardService4;

    @Autowired
    StudentRepository studentRepository4;

    public Student getDetailsByEmail(String email){
        Student student = studentRepository4.findByEmailId(email);

        return student;
    }

    public Student getDetailsById(int id){
        Student student = studentRepository4.findById(id).get();

        return student;
    }

    public void createStudent(Student student){

        studentRepository4.save(student) ;
    }

    public void updateStudent(Student student){

        studentRepository4.updateStudentDetails(student) ;
        // originalStudent.setAge(0);
        // originalStudent.setCard(null);
        // originalStudent.setCountry(null);
        // originalStudent.setCreatedOn(null);
        // originalStudent.setEmailId(null);
        // originalStudent.setName(null);
        // originalStudent.setUpdatedOn(null);
        // originalStudent.setId(0);

    }

    public void deleteStudent(int id){
        //Delete student and deactivate corresponding card
        cardService4.deactivateCard(id);
        studentRepository4.deleteById(id);
    }
}