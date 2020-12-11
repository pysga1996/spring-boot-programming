package com.mongodb.events;

import com.mongodb.models.documents.User;

public interface CustomEvent {

  String getAppUrl();

  User getUser();
}
