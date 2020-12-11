package com.mongodb.models.util;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "database_sequences")
public class DatabaseSequence {

  @Id
  private String id;

  private long seq;

  private int intSeq;
}
