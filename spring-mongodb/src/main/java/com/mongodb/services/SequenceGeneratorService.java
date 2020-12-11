package com.mongodb.services;

public interface SequenceGeneratorService {

  Long generateSequence(String seqName, Long className);

  Integer generateSequence(String seqName, Integer className);
}
