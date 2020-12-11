package com.mongodb.daos;

import com.mongodb.models.documents.NavigationItem;

import java.util.List;

public interface NavigationItemDao {

  List<NavigationItem> getNavigationItems();

  NavigationItem getNavigationItemById(String id);

  NavigationItem getNavigationItemByTitle(String title);
}
