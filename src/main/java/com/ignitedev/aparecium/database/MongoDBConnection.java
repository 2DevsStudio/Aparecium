package com.ignitedev.aparecium.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.Data;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Data
public class MongoDBConnection {

  private final MongoClient mongoClient;
  private final ExecutorService executorService;

  public MongoDBConnection(String connectionAddress) {
    this.mongoClient = MongoClients.create(connectionAddress);
    this.executorService = Executors.newSingleThreadExecutor();
  }

  public MongoDatabase getDatabase(String databaseName) {
    return this.mongoClient.getDatabase(databaseName);
  }
}
