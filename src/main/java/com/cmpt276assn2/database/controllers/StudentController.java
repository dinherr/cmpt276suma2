package com.cmpt276assn2.database.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.BindingResult;
import com.cmpt276assn2.database.models.Student;
import com.cmpt276assn2.database.models.StudentRepo;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class StudentController {

    @Autowired
    private StudentRepo studentRepo;
    @GetMapping("/students/view")
    public String getAllStudents(Model model){
        System.out.println("Getting all students...");
        // getting students from database
        List<Student> students =studentRepo.findAll();
        //end of db call
        model.addAttribute("stdb", students);
         return "students/showAll";
    }

    @PostMapping("students/addStudent")
    public String addStudent(@RequestParam Map<String, String> newStudent, HttpServletResponse response){
        System.out.println("adding student...");
        String newName = newStudent.get("name");
        int newWeight = Integer.parseInt(newStudent.get("weight"));
        int newHeight = Integer.parseInt(newStudent.get("height"));
        String newHairColour = newStudent.get("hairColour");
        String newMajor = newStudent.get("major");
        int newYear = Integer.parseInt(newStudent.get("year"));
        float newGPA = Float.parseFloat(newStudent.get("gpa"));
        studentRepo.save(new Student(newName, newWeight, newHeight, newHairColour, newMajor, newYear, newGPA));
        response.setStatus(201);
        return "students/addedStudent";
    }

    @GetMapping("students/editStudent/{uid}")
    public String editStudentForm(@PathVariable("uid") int uid, Model model){
        Student student = studentRepo.findById(uid)
            .orElseThrow(() -> new IllegalArgumentException("This user does not exist!"));
        model.addAttribute("student", student);     
        return "students/editStudent";
    }

    @PostMapping("/students/updated/{uid}")
    public String updateStudent(@PathVariable("uid") int uid, @Validated Student student,BindingResult result){
        System.out.println("updating student...");
        if (result.hasErrors()){
            return "students/editStudent";
        }
        
        student.setUid(uid);
        studentRepo.save(student);
        return "students/updated";
    }

    @GetMapping("students/expelStudent/{uid}")
    public String expelThisStudent(@PathVariable("uid") int uid){
        System.out.println("STUDENT CHOSEN...");
        studentRepo.deleteById(uid);
        return "students/expelled";
    }
}
