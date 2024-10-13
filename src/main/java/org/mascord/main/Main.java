package org.mascord.main;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.mascord.main.commands.Whisper;
import org.mascord.main.listeners.PlayerJoinListener;
import org.mascord.main.logger.MascordLogger;
import org.mascord.main.utils.ScoreBoard;
import org.mascord.main.utils.MongoDB;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // MongoDB 싱글톤 인스턴스 가져오기
        MongoDB mongoManager = MongoDB.getInstance();
        mongoManager.connect("mongodb+srv://mascord:DefaultPassword@mascord.iob4z.mongodb.net/", "mydb");

        // 이벤트 등록
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);

        // ScoreBoard 생성 및 초기화
        new ScoreBoard(this);

        // 명령어 등록
        getCommand("귓").setExecutor(new Whisper());
    }

    @Override
    public void onDisable() {
        // MongoDB 연결 해제
        MongoDB mongoManager = MongoDB.getInstance();
        mongoManager.disconnect();
        MascordLogger.info("플러그인 비활성화 및 MongoDB 연결 해제 완료");
    }
}
