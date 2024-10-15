package org.mascord.main.utils;

import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.mascord.main.Main;
import fr.mrmicky.fastboard.FastBoard;
import org.mascord.main.api.UserAPI;
import org.mascord.main.system.Season;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreBoard implements Listener {
    UserAPI UserAPI = new UserAPI();
    Season season = Season.getInstance(Main.getInstance());
    private final Map<UUID, FastBoard> boards = new HashMap<>();
    private final String[] animatedTitles = {
            "§e치", "§e치즈", "§e치즈 타", "§e치즈 타운",
            "§e치즈 타운", "§e치즈 타운", "§e치즈 타운",
            "§f치§e즈 타운", "§e치§f즈 타§e운", "§e치즈 타§f운",
            "§f치즈 타운", "§e치즈 타운", "§f치즈 타운", "§e치즈 타운",
            "§e치즈 타운", "§e치즈 타운"
    };

    public ScoreBoard(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);

        // 플레이어 보드 업데이트 스케줄러
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (FastBoard board : boards.values()) {
                Player player = board.getPlayer();
                UUID playerUUID = player.getUniqueId();

                // MongoDB에서 해당 플레이어의 money와 cheese 값 가져오기
                Document playerData = UserAPI.getPlayerData(playerUUID);

                if (playerData != null) {
                    int money = playerData.getInteger("money", 0);  // 기본값 0
                    int cheese = playerData.getInteger("cheese", 0);  // 기본값 0

                    // 스코어보드 업데이트
                    updateBoard(board,
                            "",
                            "上 §e" + Format.NumberCommaFormat(cheese) + "치즈",     // MongoDB에서 가져온 cheese 값
                            "我 " + Format.NumberCommaFormat(money) + "원",  // MongoDB에서 가져온 money 값
                            "",
                            "安 접속자: " + Bukkit.getOnlinePlayers().size() + " 명",
                            "",
                            "升 월드: 야생 채널#1",
                            "",
                            season.getCurrentSeasonColor() + season.getCurrentSeasonName() + " " + season.getCurrentDay() + "일 &8|&7 이벤트 없음"
                    );
                }
            }
        }, 0L, 30L);

        // 타이틀 애니메이션을 위한 스케줄러
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (FastBoard board : boards.values()) {
                animateBoardTitle(board);
            }
        }, 0L, 12L);  // 8틱마다 타이틀이 바뀌도록 설정
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        FastBoard board = new FastBoard(player);

        boards.put(player.getUniqueId(), board);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        FastBoard board = boards.remove(player.getUniqueId());

        if (board != null) {
            board.delete();
        }
    }

    // 일반적인 메서드로 변경
    public void updateBoard(FastBoard board, String... lines) {
        for (int a = 0; a < lines.length; ++a) {
            lines[a] = ChatColor.translateAlternateColorCodes('&', lines[a]);
        }

        board.updateLines(lines);
    }

    // 타이틀 애니메이션 메서드
    private int titleIndex = 0;

    public void animateBoardTitle(FastBoard board) {
        String title = animatedTitles[titleIndex];
        board.updateTitle(title);

        // 타이틀 배열의 끝까지 도달하면 다시 처음으로
        titleIndex = (titleIndex + 1) % animatedTitles.length;
    }
}