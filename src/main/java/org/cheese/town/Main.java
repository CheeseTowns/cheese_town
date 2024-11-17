package org.cheese.town;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.cheese.town.commands.Whisper;
import org.cheese.town.listeners.PlayerJoinListener;
import org.cheese.town.system.Season;
import org.cheese.town.utils.MongoDB;
import org.cheese.town.utils.ScoreBoard;

public final class Main extends JavaPlugin implements Listener {
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        // MongoDB 연결
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
        System.out.println("플러그인 비활성화 및 MongoDB 연결 해제 완료");
    }

    public static Main getInstance() {
        return instance;
    }
}
