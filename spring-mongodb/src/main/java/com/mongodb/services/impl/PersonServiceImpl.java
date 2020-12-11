package com.mongodb.services.impl;

import com.mongodb.daos.PersonDao;
import com.mongodb.models.documents.Person;
import com.mongodb.services.PersonService;
import com.mongodb.services.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

  @Autowired
  private PersonDao personDao;

  @Autowired
  private SequenceGeneratorService sequenceGeneratorService;

  @Override
  public List<Person> findAll() {
    return personDao.getAllPeople();
  }

  @Override
  public Person findById(String personId) {
    return personDao.getPersonById(personId);
  }

  @Override
  public void createPerson(Person person) {
//        person.setId(sequenceGeneratorService.generateSequence(Person.SEQUENCE_NAME, Integer.MIN_VALUE));
    personDao.save(person);
  }

  @Override
  public Person editPerson(Person person, String personId) {
    return personDao.update(person, personId);
  }

  @Override
  public boolean deletePerson(String personId) {
    return personDao.remove(personId);
  }

  @Override
  public Integer countSalarySum() {
    return personDao.salarySum();
  }
}
