package com.mongodb.daos;

import com.mongodb.models.documents.Person;

import java.util.List;

public interface PersonDao {

  List<Person> getAllPeople();

  Person getPersonById(String personId);

  void save(Person person);

  Person update(Person person, String personId);

  boolean remove(String personId);

  Integer salarySum();
}
