package org.mascord.main.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

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

    public void saveUserData(String uuid, String playerName, int money, int cheese) {
        MongoCollection<Document> collection = database.getCollection("players");

        Document playerData = new Document("uuid", uuid)
                .append("playerName", playerName)
                .append("money", money)
                .append("cheese", cheese);

        collection.insertOne(playerData); // 데이터를 컬렉션에 저장
        System.out.println("유저 데이터 저장 완료: " + playerName);
    }
}
