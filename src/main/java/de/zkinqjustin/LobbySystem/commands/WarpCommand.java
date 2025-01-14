package de.zkinqjustin.LobbySystem.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import de.zkinqjustin.LobbySystem.LobbySystem;
import de.zkinqjustin.LobbySystem.utils.ColorUtil;

public class WarpCommand implements CommandExecutor {

    private final LobbySystem plugin;

    public WarpCommand(LobbySystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ColorUtil.colorize("&cThis command can only be used by players."));
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage(ColorUtil.colorize("&cUsage: /warp <name>"));
            return true;
        }

        String warpName = args[0].toLowerCase();
        Location warpLocation = plugin.getWarpManager().getWarp(warpName);

        if (warpLocation == null) {
            player.sendMessage(ColorUtil.colorize("&cWarp '" + warpName + "' does not exist."));
            return true;
        }

        player.teleport(warpLocation);
        player.sendMessage(ColorUtil.colorize("&aTeleported to warp '" + warpName + "'."));

        return true;
    }
}

