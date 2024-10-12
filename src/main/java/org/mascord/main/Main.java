package org.mascord.main;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.mascord.main.listeners.PlayerJoinListener;
import org.mascord.main.logger.MascordLogger;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // MascordLogger 인스턴스 초기화
        MascordLogger.init(getLogger());

        MascordLogger.info("플러그인이 활성화되었습니다.");

        // 이벤트 등록
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        MascordLogger.info("플러그인이 비활성화되었습니다.");
    }
}
