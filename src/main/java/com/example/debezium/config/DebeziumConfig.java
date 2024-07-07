package com.example.debezium.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DebeziumConfig {
    @Bean
    public io.debezium.config.Configuration customerConnector() {
        return io.debezium.config.Configuration.create()
                .with("name", "customer-mysql-connector")
                .with("connector.class", "io.debezium.connector.mysql.MySqlConnector")
                .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
                .with("offset.storage.file.filename", "/tmp/offsets.dat")
                .with("offset.flush.interval.ms", "60000")
                .with("database.hostname", "localhost")
                .with("database.port", "3305")
                .with("database.user", "root")
                .with("database.password", "root")
                .with("database.dbname", "customerdb")
                .with("database.include.list", "customerdb")
                .with("include.schema.changes", "false")
                .with("topic.prefix", "topic")
                .with("schema.history.internal", "io.debezium.storage.file.history.FileSchemaHistory")
                .with("schema.history.internal.file.filename", "/tmp/schistory.dat")
                .with("database.server.id", "10181")
                .with("database.server.name", "customer-mysql-db-server")
                .with("database.history", "io.debezium.relational.history.FileDatabaseHistory")
                .with("database.history.file.filename", "/tmp/dbhistory.dat")
                .build();
        //configMap.put("topic.prefix", "sbd-mongodb-connector");
    }
}
