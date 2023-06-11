package com.cmpt276assn2.database.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cmpt276assn2.database.models.Student;

@Controller
public class StudentController {
    @GetMapping("/students/view")
    public String getAllStudents(Model model){
        System.out.println("Getting all students...");
        // to do: addingdb
        List<Student> students = new ArrayList<>();
        students.add(new Student("skinny", 0, 0, "black",
         "slaying", 0, 0));
        //end of db ccall
        model.addAttribute("stdb", students);
         return "students/showAll";
    }
}
