package org.mascord.main;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("[&bmascord&f] Mascord의 메인 플러그인이 로드되었습니다");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }

    @EventHandler
    public void anggimotti(PlayerMoveEvent a) {
        Player p = a.getPlayer();
        p.sendMessage("너 움직임");
    }
}
