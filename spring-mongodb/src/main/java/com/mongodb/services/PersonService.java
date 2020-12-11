package com.mongodb.services;

import com.mongodb.models.documents.Person;

import java.util.List;

public interface PersonService {

  List<Person> findAll();

  Person findById(String personId);

  void createPerson(Person person);

  Person editPerson(Person person, String personId);

  boolean deletePerson(String personId);

  Integer countSalarySum();
}
