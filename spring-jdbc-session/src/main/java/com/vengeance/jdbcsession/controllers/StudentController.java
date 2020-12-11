package com.vengeance.jdbcsession.controllers;

import java.io.Serializable;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {

    @GetMapping("/get")
    public ResponseEntity<Student> getStudent(HttpSession session) {
      if (session.getAttribute("student") != null) {
        return ResponseEntity.ok().body((Student) (session.getAttribute("student")));
      } else {
        return ResponseEntity.of(Optional.empty());
      }
    }

    @PostMapping("/set")
    public ResponseEntity<Object> index(@RequestBody Student student, HttpSession session) {
        session.setAttribute("student", student);
        return ResponseEntity.of(Optional.empty());
    }

    static class Student implements Serializable {

        private String name;
        private short age;

        public Student(String name, short age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public short getAge() {
            return age;
        }

        public void setAge(short age) {
            this.age = age;
        }
    }
}
