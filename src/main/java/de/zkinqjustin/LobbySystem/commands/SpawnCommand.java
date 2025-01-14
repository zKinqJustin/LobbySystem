package de.zkinqjustin.LobbySystem.commands;

import de.zkinqjustin.LobbySystem.utils.ColorUtil;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import de.zkinqjustin.LobbySystem.LobbySystem;

public class SpawnCommand implements CommandExecutor {

    private final LobbySystem plugin;

    public SpawnCommand(LobbySystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;
        Location spawnLocation = new Location(
                player.getWorld(),
                plugin.getConfigManager().getConfig().getDouble("spawn.x"),
                plugin.getConfigManager().getConfig().getDouble("spawn.y"),
                plugin.getConfigManager().getConfig().getDouble("spawn.z")
        );

        player.teleport(spawnLocation);
        player.sendMessage(ColorUtil.colorize(plugin.getConfigManager().getConfig().getString("messages.spawn_teleport")));

        return true;
    }
}

