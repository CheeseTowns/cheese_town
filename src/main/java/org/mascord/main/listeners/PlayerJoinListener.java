package org.mascord.main.listeners;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.mascord.main.utils.mongodb;
import org.mascord.main.logger.MascordLogger;

public class PlayerJoinListener implements Listener {
    private final mongodb mongoManager;

    // 생성자에서 mongodb 매니저를 주입받음
    public PlayerJoinListener(mongodb mongoManager) {
        this.mongoManager = mongoManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // 플레이어가 접속했을 때 로깅
        MascordLogger.info("플레이어 " + event.getPlayer().getName() + "님이 서버에 접속하였습니다.");
        
        // 입장 메시지를 숨김
        event.joinMessage(null);

        // 플레이어 정보를 MongoDB에 저장
        String playerName = event.getPlayer().getName();
        String playerUUID = event.getPlayer().getUniqueId().toString();
        int initialMoney = 0;
        int initialCheese = 0;

        // MongoDB 컬렉션 가져오기
        MongoCollection<Document> collection = mongoManager.getDatabase().getCollection("players");

        // UUID로 기존 유저가 있는지 확인하고, 없으면 새로 저장
        if (collection.find(Filters.eq("uuid", playerUUID)).first() == null) {
            mongoManager.saveUserData(playerUUID, playerName, initialMoney, initialCheese);
            MascordLogger.info("새 유저 데이터 저장: " + playerName);
        } else {
            MascordLogger.info("유저 데이터가 이미 존재합니다: " + playerName);
        }
    }
}