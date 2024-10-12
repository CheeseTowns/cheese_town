package org.mascord.main.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.mascord.main.logger.MascordLogger;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        MascordLogger.info("플레이어 " + e.getPlayer().getName() + "님이 서버에 접속하였습니다.");
        // 입장 메시지를 숨김
        e.joinMessage(null);
    }
}
