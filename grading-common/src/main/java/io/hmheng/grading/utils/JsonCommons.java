package io.hmheng.grading.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class JsonCommons {
  public static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime arg0, JsonGenerator arg1, SerializerProvider arg2)
      throws IOException, JsonProcessingException {
      arg1.writeString(arg0.toString());
    }
  }

  public static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser arg0, DeserializationContext arg1)
      throws IOException, JsonProcessingException {
      return LocalDateTime.parse(arg0.getText());
    }
  }

  public static class LocalDateTimeDeserializerISOFormat extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser arg0, DeserializationContext arg1)
        throws IOException, JsonProcessingException {
      return LocalDateTime.parse(arg0.getText() , DateTimeFormatter.ISO_DATE_TIME);
    }
  }

  public static class LocalDateTimeSerializerISOFormat extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime arg0, JsonGenerator arg1, SerializerProvider arg2)
        throws IOException, JsonProcessingException {

      String seralized = arg0.format(DateTimeFormatter.ISO_DATE_TIME);

      arg1.writeString(seralized);
    }
  }

  public static class LocalDateTimeSerializerLearnosityStatusFormat extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime arg0, JsonGenerator arg1, SerializerProvider arg2)
        throws IOException, JsonProcessingException {

      String seralized = arg0.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

      arg1.writeString(seralized);
    }

  }

  public static <T> String createJsonOutput(T output) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(output);
  }
}
