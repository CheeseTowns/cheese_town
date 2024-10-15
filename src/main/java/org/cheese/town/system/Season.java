package org.cheese.town.system;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Season {
    private static Season instance;
    private int currentDay;
    private SeasonType currentSeason;
    private final int DAYS_PER_SEASON = 90;
    private final Plugin plugin;

    private Season(Plugin plugin) {
        this.plugin = plugin;
        this.currentDay = plugin.getConfig().getInt("currentDay", 0);
        this.currentSeason = SeasonType.valueOf(plugin.getConfig().getString("currentSeason", SeasonType.SPRING.name()));

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
                /*
                조건문이 world.getTime() == 0 이면 빠르게 변화하는 틱이 1초 마다 한번씩 실행하는 if문에 걸리지 않을 수 있어서
                world.getTime() < 20으로 변경하여 20틱(1초) 조금 더 여유롭게 시간을 확인하도록 수정
                * */
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
        plugin.getConfig().set("currentDay", currentDay);
        plugin.getConfig().set("currentSeason", currentSeason.toString());
        plugin.saveConfig();
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
        };
    }

    public String getCurrentSeasonColor() {
        return switch (currentSeason) {
            case SPRING -> "§a";
            case SUMMER -> "§e";
            case AUTUMN -> "§6";
            case WINTER -> "§b";
        };
    }

    public int getCurrentDay() {
        return currentDay;
    }
}