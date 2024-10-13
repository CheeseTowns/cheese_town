package org.mascord.main.api;

import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.mascord.main.logger.MascordLogger;
import com.mongodb.client.MongoCollection;
import org.mascord.main.utils.MongoDB;

import java.util.UUID;

public class UserAPI {
    private final MongoDB mongoManager;

    public UserAPI() {
        this.mongoManager = MongoDB.getInstance();

        MascordLogger.info("UserAPI 인스턴스 생성됨.");
    }

    // MongoDB에서 플레이어 데이터를 가져오는 메서드
    public Document getPlayerData(UUID playerUUID) {
        MongoCollection<Document> collection = mongoManager.getDatabase().getCollection("players");
        return collection.find(Filters.eq("uuid", playerUUID.toString())).first();
    }
}
