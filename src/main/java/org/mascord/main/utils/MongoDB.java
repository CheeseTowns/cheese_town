package org.mascord.main.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

public class MongoDB {
    private static MongoDB instance; // 싱글톤 인스턴스
    private MongoClient mongoClient;
    private MongoDatabase database;

    // private 생성자: 외부에서 인스턴스 생성을 막기 위해 사용
    private MongoDB() {}

    // 싱글톤 인스턴스를 반환하는 메서드
    public static MongoDB getInstance() {
        if (instance == null) {
            synchronized (MongoDB.class) {
                if (instance == null) {
                    instance = new MongoDB();
                }
            }
        }
        return instance;
    }

    public void connect(String uri, String dbName) {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(uri);
            database = mongoClient.getDatabase(dbName);
            System.out.println("MongoDB 연결 성공");
        }
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public void disconnect() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null; // 다음 연결을 위해 null로 설정
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