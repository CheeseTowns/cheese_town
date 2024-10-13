package org.mascord.main.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Whisper implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        // 인자가 충분하지 않으면 사용법을 보여줌
        if (args.length < 2) {
            player.sendMessage("§c[귓속말]§r /귓 <플레이어> <메시지>");
            return false;
        }

        // 귓속말을 보낼 플레이어 찾기
        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage("§c[귓속말]§r 오류: " + args[0] + " 플레이어를 찾을 수 없습니다");
            return true;
        }

        // 메시지 조합
        StringBuilder message = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            message.append(args[i]).append(" ");
        }

        // 플레이어에게 귓속말 보내기
        player.sendMessage("§a[" + target.getName() + "에게 보낸 귓속말]§r " + message.toString().trim());
        target.sendMessage("§c[" + player.getName() + "에게서 받은 귓속말]§r " + message.toString().trim());

        return true;
    }
}
