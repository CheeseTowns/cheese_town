package org.mascord.main;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.mascord.main.commands.Whisper;
import org.mascord.main.listeners.PlayerJoinListener;
import org.mascord.main.logger.MascordLogger;
import org.mascord.main.utils.ScoreBoard;
import org.mascord.main.utils.mongodb;

public final class Main extends JavaPlugin implements Listener {
    private mongodb mongoManager;

    @Override
    public void onEnable() {
//        mongoManager = new mongodb();
//        mongoManager.connect("mongodb+srv://mascord:DefaultPassword@mascord.iob4z.mongodb.net/", "mascord");
        // MascordLogger 인스턴스 초기화
//        MascordLogger.init(getLogger());
//
//        MascordLogger.info("플러그인 활성화 및 MongoDB 연결 완료");

        // 이벤트 등록
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);

        new ScoreBoard(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (mongoManager != null) {
            mongoManager.disconnect();
        }
        MascordLogger.info("플러그인 비활성화 및 MongoDB 연결 해제 완료");
    }
}
