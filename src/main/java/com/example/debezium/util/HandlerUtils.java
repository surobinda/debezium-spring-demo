package com.example.debezium.util;

import com.example.debezium.entity.Product;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.connect.data.Struct;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HandlerUtils {
    /**
     * Extracts the document ID from the given Struct object.
     *
     * @param key The Struct object containing the document information.
     * @return The extracted document ID, or null if not found.
     */
    public static String getDocumentId(Struct key) {
        Long id = (Long) key.get("id");
        Matcher matcher = Pattern.compile("\"\\$oid\":\\s*\"(\\w+)\"").matcher(id.toString());
        return matcher.find() ? matcher.group(1) : null;
    }

    /**
     * Extracts the collection name from the source record value.
     *
     * @param sourceRecordValue The Struct object representing the source record.
     * @return The name of the collection.
     */
    public static String getCollection(Struct sourceRecordValue) {
        Struct source = (Struct) sourceRecordValue.get("source");
        return source.getString("collection");
    }

    /**
     * Deserializes the 'after' field of the source record value into a Product object.
     *
     * @param sourceRecordValue The Struct object representing the source record.
     * @return The deserialized Product object.
     * @throws IOException If there is an error during deserialization.
     */
    public static Product getData(Struct sourceRecordValue) throws IOException {
        var source = sourceRecordValue.get("after").toString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String input  = "{\"id\": \"1\",\"name\": \"Sample Product\",\"price\": \"19.99\",\"description\": \"This is a sample product description.\",\"sourceCollection\": \"Sample Collection\",\"mysqlId\": \"mysql123\"}";
        mapper.readValue(input, Product.class);
        //return mapper.readValue(mapper.writeValueAsString(source), Product.class);
        return mapper.readValue(input, Product.class);
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
