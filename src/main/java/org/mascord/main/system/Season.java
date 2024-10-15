package org.mascord.main.system;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.World;

import java.io.File;
import java.io.IOException;

public class Season {
    private static Season instance;
    private int currentDay;
    private SeasonType currentSeason;
    private final int DAYS_PER_SEASON = 90;
    private final Plugin plugin;
    private final FileConfiguration customConfig;
    private final File customConfigFile;

    private Season(Plugin plugin) {
        this.plugin = plugin;
        this.customConfigFile = new File(plugin.getDataFolder(), "seasons.yml"); // seasons.yml 파일 생성
        this.customConfig = YamlConfiguration.loadConfiguration(customConfigFile);

        this.currentDay = customConfig.getInt("currentDay", 0);
        this.currentSeason = SeasonType.valueOf(customConfig.getString("currentSeason", SeasonType.SPRING.name()));

        // 스케줄링을 설정
        scheduleSeasonUpdate();
    }

    public static Season getInstance(Plugin plugin) {
        if (instance == null) {
            instance = new Season(plugin);
        }
        return instance;
    }

    private void scheduleSeasonUpdate() {
        new BukkitRunnable() {
            @Override
            public void run() {
                World world = Bukkit.getWorlds().getFirst(); // 기본 월드 사용
                if (world.getTime() < 0 || world.getTime() < 20) {
                    updateDayAndSeason();
                }
            }
        }.runTaskTimer(plugin, 0L, 20L); // 매 1초마다 실행하여 시간을 확인
    }

    public void updateDayAndSeason() {
        currentDay++;
        if (currentDay > DAYS_PER_SEASON) {
            currentDay = 1;
            switchSeason();

            // 계절 변경 메시지를 브로드캐스트
            broadcastSeasonChange();
        }

        // 날짜와 계절을 저장
        saveSeasonData();
    }

    // 계절을 바꿈
    public void switchSeason() {
        switch (currentSeason) {
            case SPRING:
                currentSeason = SeasonType.SUMMER;
                break;
            case SUMMER:
                currentSeason = SeasonType.AUTUMN;
                break;
            case AUTUMN:
                currentSeason = SeasonType.WINTER;
                break;
            case WINTER:
                currentSeason = SeasonType.SPRING;
                break;
        }
    }

    private void broadcastSeasonChange() {
        // 계절 변경 메시지를 브로드캐스트
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage("§e계절이 변경되었습니다: " + getCurrentSeasonName());
        }
    }

    private void saveSeasonData() {
        customConfig.set("currentDay", currentDay);
        customConfig.set("currentSeason", currentSeason.toString());
        try {
            customConfig.save(customConfigFile); // seasons.yml에 저장
        } catch (IOException e) {
            System.out.println("§c[Season] seasons.yml 파일에 저장하는 중 오류가 발생했습니다.");
        }
    }

    public enum SeasonType {
        SPRING, SUMMER, AUTUMN, WINTER
    }

    public String getCurrentSeasonName() {
        return switch (currentSeason) {
            case SPRING -> "봄";
            case SUMMER -> "여름";
            case AUTUMN -> "가을";
            case WINTER -> "겨울";
            default -> "계절을 불러오지 못함";
        };
    }

    public String getCurrentSeasonColor() {
        return switch (currentSeason) {
            case SPRING -> "§a";
            case SUMMER -> "§e";
            case AUTUMN -> "§6";
            case WINTER -> "§b";
            default -> "§c";
        };
    }

    public int getCurrentDay() {
        return currentDay;
    }
}
