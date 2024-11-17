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

        String pluginVersion = instance.getDescription().getVersion();
        String gameVersion = instance.getDescription().getAPIVersion();

        System.out.println("\n\n\033[38;2;255;223;0m /$$$$$$  / $$   /$$ /$$$$$$$$ /$$$$$$$$  /$$$$$$  /$$$$$$$$ /$$$$$$$$ /$$$$$$  /$$      /$$ /$$   /$$\n" +
                "/ $$__  $$| $$  | $$| $$_____/| $$_____/ /$$__  $$| $$_____/|__  $$__//$$__  $$| $$  /$ | $$| $$$ | $$\n" +
                "\033[38;2;217;196;51m| $$  \\__/| $$  | $$| $$      | $$      | $$  \\__/| $$         | $$  | $$  \\ $$| $$ /$$$| $$| $$$$| $$\n" +
                "\033[38;2;179;169;102m| $$      | $$$$$$$$| $$$$$   | $$$$$   |  $$$$$$ | $$$$$      | $$  | $$  | $$| $$/$$ $$ $$| $$ $$ $$\n" +
                "\033[38;2;141;142;153m| $$      | $$__  $$| $$__/   | $$__/    \\____  $$| $$__/      | $$  | $$  | $$| $$$$_  $$$$| $$  $$$$\n" +
                "\033[38;2;103;165;204m| $$    $$| $$  | $$| $$      | $$       /$$  \\ $$| $$         | $$  | $$  | $$| $$$/ \\  $$$| $$\\  $$$\n" +
                "\033[38;2;65;188;255m|  $$$$$$/| $$  | $$| $$$$$$$$| $$$$$$$$|  $$$$$$/| $$$$$$$$   | $$  |  $$$$$$/| $$/   \\  $$| $$ \\  $$\n" +
                " \\______/ |__/  |__/|________/|________/ \\______/ |________/   |__/   \\______/ |__/     \\__/|__/  \\__/\033[0m\n\n" +
                "          \033[38;2;255;223;0m• Plugin Version: \033[38;2;65;188;255m" + pluginVersion + "\n" +
                "          \033[38;2;255;223;0m• Game Version: \033[38;2;65;188;255m" + gameVersion + "\033[0m");

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
