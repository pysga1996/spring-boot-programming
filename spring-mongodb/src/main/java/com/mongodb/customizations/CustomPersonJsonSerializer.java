package com.mongodb.customizations;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.mongodb.models.documents.Person;

import java.io.IOException;

public class CustomPersonJsonSerializer extends StdSerializer<Person> {

  public CustomPersonJsonSerializer() {
    this(null);
  }

  public CustomPersonJsonSerializer(Class<Person> t) {
    super(t);
  }

  @Override
  public void serialize(Person value, JsonGenerator jgen, SerializerProvider provider)
      throws IOException, JsonProcessingException {
    jgen.writeStartObject();
    jgen.writeStringField("id", value.getId());
    jgen.writeStringField("name", value.getName());
    jgen.writeEndObject();
  }
}
