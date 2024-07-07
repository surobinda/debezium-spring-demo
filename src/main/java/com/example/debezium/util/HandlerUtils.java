package com.example.debezium.util;

import com.example.debezium.entity.Product;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.json.JsonConverter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HandlerUtils {

    /**
     * Deserializes the 'after' field of the source record value into a Product object.
     *
     * @param sourceRecordValue The Struct object representing the source record.
     * @return The deserialized Product object.
     * @throws IOException If there is an error during deserialization.
     */
    public static Product getData(Struct sourceRecordValue) throws IOException {
        Struct source = (Struct) sourceRecordValue.get("after");

        // Configure the JsonConverter
        JsonConverter jsonConverter = new JsonConverter();
        Map<String, Object> configs = new HashMap<>();
        configs.put("schemas.enable", false); // Do not include schema in the output
        jsonConverter.configure(configs, false);

        // Convert the Struct to JSON bytes
        byte[] jsonBytes = jsonConverter.fromConnectData("dummyTopic", source.schema(), source);

        // Convert JSON bytes to String
        String jsonString = new String(jsonBytes);



        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,true);
        return mapper.readValue(jsonString, Product.class);

        /*String input  = "{\"id\": \"1\",\"name\": \"Sample Product\",\"price\": \"19.99\",\"description\": \"This is a sample product description.\"}";
        mapper.readValue(input, Product.class);
        return mapper.readValue(input, Product.class);*/
    }

    /**
     * Retrieves the operation type from the source record value.
     *
     * @param sourceRecordValue The Struct object representing the source record.
     * @return The operation type, such as "insert", "update", or "delete".
     */
    public static String getOperation(Struct sourceRecordValue) {
        return sourceRecordValue.getString("op");
    }
}
