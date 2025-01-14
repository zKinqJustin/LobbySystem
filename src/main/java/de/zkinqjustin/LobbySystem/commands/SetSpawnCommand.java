package de.zkinqjustin.LobbySystem.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import de.zkinqjustin.LobbySystem.LobbySystem;

public class SetSpawnCommand implements CommandExecutor {

    private final LobbySystem plugin;

    public SetSpawnCommand(LobbySystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;
        Location location = player.getLocation();

        plugin.getConfigManager().getConfig().set("spawn.x", location.getX());
        plugin.getConfigManager().getConfig().set("spawn.y", location.getY());
        plugin.getConfigManager().getConfig().set("spawn.z", location.getZ());
        plugin.getConfigManager().getConfig().set("spawn.yaw", location.getYaw());
        plugin.getConfigManager().getConfig().set("spawn.pitch", location.getPitch());
        plugin.getConfigManager().getConfig().set("spawn.world", location.getWorld().getName());
        plugin.getConfigManager().saveConfig();

        String message = plugin.getConfigManager().getConfig().getString("messages.spawn_set")
                .replace("{x}", String.format("%.2f", location.getX()))
                .replace("{y}", String.format("%.2f", location.getY()))
                .replace("{z}", String.format("%.2f", location.getZ()));

        player.sendMessage(de.zkinqjustin.LobbySystem.utils.ColorUtil.colorize(message));

        return true;
    }
}

