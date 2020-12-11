package com.mongodb.controllers;

import com.mongodb.models.documents.Person;
import com.mongodb.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/family")
public class PersonController {

  private PersonService personService;

  @Autowired
  public void setPersonService(PersonService personService) {
    this.personService = personService;
  }

  @GetMapping("/list")
  public ResponseEntity<List<Person>> familyMemberList() {
    try {
      List<Person> memberList = personService.findAll();
      if (memberList.size() > 0) {
        return new ResponseEntity<>(memberList, HttpStatus.OK);
      } else {
        return new ResponseEntity<>(memberList, HttpStatus.NO_CONTENT);
      }
    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/detail")
  public ResponseEntity<Person> familyMemberDetail(@RequestParam("id") String personId) {
    try {
      Person member = personService.findById(personId);
      if (member != null) {
        return new ResponseEntity<>(member, HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/create")
  public ResponseEntity<Void> saveFamilyMember(@RequestBody Person person) {
    try {
      personService.createPerson(person);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (DuplicateKeyException ex) {
      return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/edit")
  public ResponseEntity<Person> editFamilyMember(@Valid @RequestBody Person person,
      @RequestParam("id") String personId) {
    try {
      Person originalPerson = personService.editPerson(person, personId);
      if (originalPerson != null) {
        return new ResponseEntity<>(originalPerson, HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Void> deleteFamilyMember(@RequestParam("id") String personId) {
    try {
      boolean personDeleted = personService.deletePerson(personId);
      if (personDeleted) {
        return new ResponseEntity<>(HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/salary-sum")
  public ResponseEntity<Integer> salarySum() {
    try {
      Integer salarySum = personService.countSalarySum();
      return new ResponseEntity<>(salarySum, HttpStatus.OK);
    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
