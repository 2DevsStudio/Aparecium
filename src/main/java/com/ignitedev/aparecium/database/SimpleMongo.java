package com.ignitedev.aparecium.database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import java.util.concurrent.CompletableFuture;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bson.Document;

public class SimpleMongo {

  @Getter private final MongoDBConnection mongoDBConnection;
  @Getter private final MongoDatabase database;

  private final Get get;
  private final Misc misc;
  private final Save save;

  public SimpleMongo(MongoDBConnection mongoDBConnection, MongoDatabase database) {
    if (mongoDBConnection == null || database == null) {
      throw new IllegalArgumentException("mongoDBConnection and database cannot be null");
    }
    this.mongoDBConnection = mongoDBConnection;
    this.database = database;
    this.get = new Get();
    this.misc = new Misc(database);
    this.save = new Save(mongoDBConnection);
  }

  public Get get() {
    return this.get;
  }

  public Misc misc() {
    return this.misc;
  }

  public Save save() {
    return this.save;
  }

  /**
   * @implNote Getting DBObjects, sync or async.
   */
  public static class Get {

    /**
     * @param collection collection object accessible by SimpleMongo#Misc#getCollection
     * @param identifierObject Document with tag '_id'
     */
    public FindIterable<Document> getObjectSync(
        MongoCollection<Document> collection, Document identifierObject) {

      if (collection == null || identifierObject == null) {
        throw new IllegalArgumentException("collection and identifierObject cannot be null");
      }
      return collection.find(identifierObject);
    }

    /**
     * @param collection collection object accessible by SimpleMongo#Misc#getCollection
     * @param identifierObject Document with tag '_id'
     */
    public CompletableFuture<FindIterable<Document>> getObjectAsync(
        MongoCollection<Document> collection, Document identifierObject) {
      return CompletableFuture.supplyAsync(() -> getObjectSync(collection, identifierObject));
    }
  }

  /**
   * @implNote Helper methods for database management
   */
  @RequiredArgsConstructor
  public static class Misc {

    private final MongoDatabase database;

    public MongoCollection<Document> getCollection(String name) {
      if (name == null || name.isEmpty()) {
        throw new IllegalArgumentException("Collection name cannot be null or empty");
      }
      return database.getCollection(name);
    }
  }

  /**
   * @implNote Saving DBObjects, async or sync
   */
  @RequiredArgsConstructor
  public static class Save {

    private final MongoDBConnection mongoDBConnection;

    /**
     * @param collection collection object accessible by SimpleMongo#Misc#getCollection
     * @param identifierObject Document with tag `_id`
     * @param dbObject object to save
     */
    public void saveObjectAsync(
        MongoCollection<Document> collection, Document identifierObject, Document dbObject) {
      CompletableFuture.runAsync(
          () -> saveObjectSync(collection, identifierObject, dbObject),
          mongoDBConnection.getExecutorService());
    }

    /**
     * @param collection collection object accessible by SimpleMongo#Misc#getCollection
     * @param identifierObject Document with tag `_id`
     * @param dbObject object to save
     */
    public void saveObjectSync(
            MongoCollection<Document> collection, Document identifierObject, Document dbObject) {
      if (collection == null || identifierObject == null || dbObject == null
              || identifierObject.isEmpty() || dbObject.isEmpty()) {
        throw new IllegalArgumentException(
                "Collection, identifierObject and dbObject cannot be null or empty");
      }
      collection.updateOne(identifierObject, new Document("$set", dbObject), new UpdateOptions().upsert(true));
    }
  }
}
