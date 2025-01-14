package de.zkinqjustin.LobbySystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import de.zkinqjustin.LobbySystem.LobbySystem;

public class Pos2Command implements CommandExecutor {

    private final LobbySystem plugin;

    public Pos2Command(LobbySystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;
        plugin.getLobbyManager().setPos2(player.getLocation());

        String message = plugin.getConfigManager().getConfig().getString("messages.pos_set")
                .replace("{pos}", "2")
                .replace("{x}", String.valueOf(player.getLocation().getBlockX()))
                .replace("{y}", String.valueOf(player.getLocation().getBlockY()))
                .replace("{z}", String.valueOf(player.getLocation().getBlockZ()));

        player.sendMessage(de.zkinqjustin.LobbySystem.utils.ColorUtil.colorize(message));

        return true;
    }
}

