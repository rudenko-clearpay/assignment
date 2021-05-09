package com.clearpay.coin.configuration;

import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.WriteConcernResolver;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class RepositoryConfiguration extends AbstractMongoClientConfiguration implements RepositoryRestConfigurer {

    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.port}")
    private String port;

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://" + host + ":" + port);
    }

    /**
     * Need this for Optimistic Locking
     */
    @Bean
    public WriteConcernResolver writeConcernResolver() {
        return action -> WriteConcern.ACKNOWLEDGED;
    }

    @Override
    protected String getDatabaseName() {
        return database;
    }

    /**
     * If removed, indexes won't be created automatically
     */
    @Override
    protected boolean autoIndexCreation() {
        return true;
    }
}
