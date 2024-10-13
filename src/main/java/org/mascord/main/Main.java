package org.mascord.main;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.mascord.main.commands.Whisper;
import org.mascord.main.listeners.PlayerJoinListener;
import org.mascord.main.utils.MongoDB;
import org.mascord.main.utils.ScoreBoard;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
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
        System.out.println("플러그인 비활성화 및 MongoDB 연결 해제 완료");
    }
}
