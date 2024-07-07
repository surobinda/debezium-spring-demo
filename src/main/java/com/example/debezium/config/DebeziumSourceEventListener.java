package com.example.debezium.config;

import com.example.debezium.entity.Product;
import com.example.debezium.service.ProductService;
import com.example.debezium.util.HandlerUtils;
import io.debezium.config.Configuration;
import io.debezium.embedded.Connect;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import io.debezium.engine.format.ChangeEventFormat;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class DebeziumSourceEventListener {

    //This will be used to run the engine asynchronously
    private final Executor executor;

    //DebeziumEngine serves as an easy-to-use wrapper around any Debezium connector
    private final DebeziumEngine<RecordChangeEvent<SourceRecord>> debeziumEngine;

    //Inject product service
    private final ProductService productService;


    public DebeziumSourceEventListener(
            Configuration customerConnector, ProductService productService) {
        //Create a new single-threaded executor.
        this.executor = Executors.newSingleThreadExecutor();

        //Create a new DebeziumEngine instance.
        this.debeziumEngine =
                DebeziumEngine.create(ChangeEventFormat.of(Connect.class))
                        .using(customerConnector.asProperties())
                        .notifying(this::handleChangeEvent)
                        .build();

        //Set the product service.
        this.productService = productService;
    }

    private void handleChangeEvent(RecordChangeEvent<SourceRecord> sourceRecordRecordChangeEvent) {
        System.out.println("Got one event to handle");

        /*SourceRecord sourceRecord = sourceRecordRecordChangeEvent.record();
        Struct sourceRecordKey = (Struct) sourceRecord.key();
        Struct sourceRecordValue = (Struct) sourceRecord.value();
        if (sourceRecordValue != null) {
            try {
                String operation = HandlerUtils.getOperation(sourceRecordValue);
                String documentId = HandlerUtils.getDocumentId(sourceRecordKey);
                Product product = HandlerUtils.getData(sourceRecordValue);
                productService.handleEvent(operation, documentId, product);
                log.info("DocumentId : {} , Operation : {}", documentId, operation);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }*/
    }

    @PostConstruct
    private void start() {
        this.executor.execute(debeziumEngine);
    }

    @PreDestroy
    private void stop() throws IOException {
        if (this.debeziumEngine != null) {
            this.debeziumEngine.close();
        }
    }
}
