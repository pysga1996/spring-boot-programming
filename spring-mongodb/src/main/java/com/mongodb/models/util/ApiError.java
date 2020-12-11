package com.mongodb.models.util;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
public class ApiError {

  @JsonProperty
  private HttpStatus status;

  @JsonProperty
  private String message;

  @JsonProperty
  private List<String> errors;

  public ApiError(HttpStatus status, String message, List<String> errors) {
    super();
    this.status = status;
    this.message = message;
    this.errors = errors;
  }

  public ApiError(HttpStatus status, String message, String error) {
    super();
    this.status = status;
    this.message = message;
    this.errors = Arrays.asList(error);
  }

  public ApiError(HttpStatus status, String message) {
    super();
    this.status = status;
    this.message = message;
  }
}
