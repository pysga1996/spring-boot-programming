package com.mongodb.services;

import com.mongodb.models.documents.NavigationItem;

import java.util.List;

public interface NavigationItemService {

  List<NavigationItem> findAll();

  NavigationItem findById(String id);

  NavigationItem findByTitle(String title);
}
