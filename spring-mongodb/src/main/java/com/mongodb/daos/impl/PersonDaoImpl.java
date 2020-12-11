package com.mongodb.daos.impl;

import com.mongodb.daos.PersonDao;
import com.mongodb.models.documents.Person;
import com.mongodb.models.util.Summary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonDaoImpl implements PersonDao {

  @Autowired
  private MongoOperations mongoOperations;

  @Override
  public List<Person> getAllPeople() {
    Query query = new Query();
    query.fields().exclude("dad").exclude("mom");
    return mongoOperations.find(query, Person.class);
  }

  @Override
  public Person getPersonById(String personId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(personId));
    return mongoOperations.findOne(query, Person.class);
  }

  @Override
  public void save(Person person) {
    mongoOperations.save(person);
  }

  @Override
  public Person update(Person person, String personId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(personId));
    Update update = new Update();
    update.set("name", person.getName());
    update.set("birthDate", person.getBirthDate());
    update.set("dad", person.getDad());
    update.set("mom", person.getMom());
    update.set("children", person.getChildren());
    update.set("salary", person.getSalary());

    return mongoOperations.findAndModify(query, update, Person.class);
  }

  @Override
  public boolean remove(String personId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(personId));
    Person personDeleted = mongoOperations.findAndRemove(query, Person.class);
    return personDeleted != null;
  }

  @Override
  public Integer salarySum() {
    Aggregation aggregation = Aggregation
        .newAggregation(Aggregation.group().sum("salary").as("salarySum"));
    AggregationResults<Summary> summaryAggregationResults = mongoOperations
        .aggregate(aggregation, Person.class, Summary.class);
    Summary summary = summaryAggregationResults.getUniqueMappedResult();
    return summary.getSalarySum();
  }
}
