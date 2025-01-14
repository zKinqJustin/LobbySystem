package de.zkinqjustin.LobbySystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import de.zkinqjustin.LobbySystem.LobbySystem;
import de.zkinqjustin.LobbySystem.utils.ColorUtil;

public class SetWarpCommand implements CommandExecutor {

    private final LobbySystem plugin;

    public SetWarpCommand(LobbySystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ColorUtil.colorize("&cThis command can only be used by players."));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("lobbysystem.setwarp")) {
            player.sendMessage(ColorUtil.colorize("&cYou don't have permission to use this command."));
            return true;
        }

        if (args.length < 1) {
            player.sendMessage(ColorUtil.colorize("&cUsage: /setwarp <name>"));
            return true;
        }

        String warpName = args[0].toLowerCase();
        plugin.getWarpManager().saveWarp(warpName, player.getLocation());
        player.sendMessage(ColorUtil.colorize("&aWarp '" + warpName + "' has been set."));

        return true;
    }
}

