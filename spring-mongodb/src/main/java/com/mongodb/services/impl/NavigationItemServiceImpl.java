package com.mongodb.services.impl;

import com.mongodb.daos.NavigationItemDao;
import com.mongodb.models.documents.NavigationItem;
import com.mongodb.services.NavigationItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NavigationItemServiceImpl implements NavigationItemService {

  private NavigationItemDao navigationItemDao;

  @Autowired
  public void setNavigationItemDao(NavigationItemDao navigationItemDao) {
    this.navigationItemDao = navigationItemDao;
  }

  @Override
  public List<NavigationItem> findAll() {
    return navigationItemDao.getNavigationItems();
  }

  @Override
  public NavigationItem findById(String id) {
    return navigationItemDao.getNavigationItemById(id);
  }

  @Override
  public NavigationItem findByTitle(String title) {
    return navigationItemDao.getNavigationItemByTitle(title);
  }
}
