package org.mascord.main.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.mascord.main.api.UserAPI;
import org.mascord.main.logger.MascordLogger;

import java.util.UUID;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        UserAPI UserAPI = new UserAPI();
        // 플레이어가 접속했을 때 로깅
        MascordLogger.info("플레이어 " + event.getPlayer().getName() + "님이 서버에 접속하였습니다.");
        
        // 입장 메시지를 숨김
        event.joinMessage(null);

        // 플레이어 정보를 MongoDB에 저장
        String playerName = event.getPlayer().getName();
        UUID playerUUID = event.getPlayer().getUniqueId();

        // UUID로 기존 유저가 있는지 확인하고, 없으면 새로 저장
        if (UserAPI.isPlayerExists(playerUUID)) {
            new UserAPI().initializePlayerData(playerUUID, playerName);
            MascordLogger.info("새 유저 데이터 저장: " + playerName);
        } else {
            MascordLogger.info("유저 데이터가 이미 존재합니다: " + playerName);
        }
    }
}