package org.mascord.main.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class mongodb {
    private MongoClient mongoClient;
    private MongoDatabase database;

    public void connect(String uri, String dbName) {
        mongoClient = MongoClients.create(uri);
        database = mongoClient.getDatabase(dbName);

        System.out.println("MongoDB 연결 성공");
    }
    public MongoDatabase getDatabase() {
        return database;
    }

    public void disconnect() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("MongoDB 연결 해제");
        }
    }
}
