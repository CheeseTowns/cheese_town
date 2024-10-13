package org.mascord.main.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDB {
    private static MongoDB instance; // 싱글톤 인스턴스
    private MongoClient mongoClient;
    private MongoDatabase database;

    // private 생성자: 외부에서 인스턴스 생성을 막기 위해 사용
    private MongoDB() {
        MongoDB mongoManager = MongoDB.getInstance();
        mongoManager.connect("mongodb+srv://mascord:DefaultPassword@mascord.iob4z.mongodb.net/", "mydb");
    }

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


}