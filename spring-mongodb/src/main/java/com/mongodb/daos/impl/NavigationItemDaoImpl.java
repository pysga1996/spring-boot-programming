package com.mongodb.daos.impl;

import com.mongodb.daos.NavigationItemDao;
import com.mongodb.models.documents.NavigationItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NavigationItemDaoImpl implements NavigationItemDao {

  private MongoOperations mongoOperations;

  @Autowired
  public void setMongoOperations(MongoOperations mongoOperations) {
    this.mongoOperations = mongoOperations;
  }

  @Override
  public List<NavigationItem> getNavigationItems() {
    return mongoOperations.findAll(NavigationItem.class);
  }

  @Override
  public NavigationItem getNavigationItemById(String id) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(id));
    return mongoOperations.findOne(query, NavigationItem.class);
  }

  @Override
  public NavigationItem getNavigationItemByTitle(String title) {
    Query query = new Query();
    query.addCriteria(Criteria.where("title").is(title));
    return mongoOperations.findOne(query, NavigationItem.class);
  }

}
