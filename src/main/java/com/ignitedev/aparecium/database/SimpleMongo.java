package com.ignitedev.aparecium.database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.concurrent.CompletableFuture;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bson.Document;

@Getter
public class SimpleMongo {

  private final MongoDBConnection mongoDBConnection;
  private final MongoDatabase database;

  private final Get get;
  private final Misc misc;
  private final Save save;

  public SimpleMongo(MongoDBConnection mongoDBConnection, MongoDatabase database) {
    this.mongoDBConnection = mongoDBConnection;
    this.database = database;
    this.get = new Get();
    this.misc = new Misc(database);
    this.save = new Save(mongoDBConnection);
  }

  /**
   * @implNote getting DBObjects, sync or async.
   */
  public static class Get {

    /**
     * @param collection collection object accessible by SimpleMongo#Misc#getCollection
     * @param identifierObject BasicDBObject with tag '_id'
     */
    public FindIterable<Document> getObjectSync(
        MongoCollection<Document> collection, Document identifierObject) {

      return collection.find(identifierObject);
    }

    /**
     * @param collection collection object accessible by SimpleMongo#Misc#getCollection
     * @param identifierObject BasicDBObject with tag '_id'
     */
    public CompletableFuture<FindIterable<Document>> getObjectASync(
        MongoCollection<Document> collection, Document identifierObject) {

      return CompletableFuture.supplyAsync(() -> getObjectSync(collection, identifierObject));
    }
  }

  /**
   * @implNote Misc methods for database management
   */
  @RequiredArgsConstructor
  public static class Misc {

    private final MongoDatabase database;

    public MongoCollection<Document> getCollection(String name) {

      return database.getCollection(name);
    }
  }

  /**
   * @implNote Saving DBObjects, async or async
   */
  @RequiredArgsConstructor
  public static class Save {

    private final MongoDBConnection mongoDBConnection;

    /**
     * @param collection collection object accessible by SimpleMongo#Misc#getCollection
     * @param identifierObject BasicDBObject with tag `_id`
     * @param dbObject accessible by #new BasicDBObject
     */
    public void saveObjectAsync(
        MongoCollection<Document> collection, Document identifierObject, Document dbObject) {

      CompletableFuture.runAsync(
          () -> saveObjectSync(collection, identifierObject, dbObject),
          mongoDBConnection.getExecutorService());
    }

    /**
     * @param collection collection object accessible by SimpleMongo#Misc#getCollection
     * @param identifierObject BasicDBObject with tag `_id`
     * @param dbObject accessible by #new BasicDBObject
     */
    public void saveObjectSync(
        MongoCollection<Document> collection, Document identifierObject, Document dbObject) {

      FindIterable<Document> one = collection.find(identifierObject);
      Document first = one.first();

      if (first == null || first.isEmpty()) {
        collection.insertOne(dbObject);
        return;
      }
      collection.updateOne(first, dbObject);
    }
  }
}
