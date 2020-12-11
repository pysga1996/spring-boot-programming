package com.mongodb.exceptions;

public enum ErrorCode {
  USER_NOT_FOUND("1"),
  CURRENT_PASSWORD_FALSE("2"),
  NEW_PASSWORD_SAME_AS_OLD_PASSWORD("3");

  public final String label;

  ErrorCode(String label) {
    this.label = label;
  }
}
