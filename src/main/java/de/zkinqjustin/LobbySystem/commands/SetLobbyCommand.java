package de.zkinqjustin.LobbySystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import de.zkinqjustin.LobbySystem.LobbySystem;

public class SetLobbyCommand implements CommandExecutor {

    private final LobbySystem plugin;

    public SetLobbyCommand(LobbySystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("lobbysystem.setlobby")) {
            player.sendMessage(plugin.getConfigManager().getConfig().getString("messages.no_permission"));
            return true;
        }

        if (plugin.getLobbyManager().getPos1() == null || plugin.getLobbyManager().getPos2() == null) {
            player.sendMessage(plugin.getConfigManager().getConfig().getString("messages.set_pos_first"));
            return true;
        }

        plugin.getLobbyManager().setLobbyArea();
        player.sendMessage(plugin.getConfigManager().getConfig().getString("messages.lobby_set"));

        return true;
    }
}

