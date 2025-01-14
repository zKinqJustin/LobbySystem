package de.zkinqjustin.LobbySystem.commands;

import de.zkinqjustin.LobbySystem.utils.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import de.zkinqjustin.LobbySystem.LobbySystem;

public class HideCommand implements CommandExecutor {

    private final LobbySystem plugin;

    public HideCommand(LobbySystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;
        boolean isHidden = !player.hasMetadata("hidden");

        if (isHidden) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (!onlinePlayer.hasPermission("lobbysystem.seehidden") || player.hasPermission("lobbysystem.stayhidden")) {
                    onlinePlayer.hidePlayer(plugin, player);
                }
            }
            player.setMetadata("hidden", new FixedMetadataValue(plugin, true));
            player.sendMessage(ColorUtil.colorize(plugin.getConfigManager().getConfig().getString("messages.hide_on")));
        } else {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.showPlayer(plugin, player);
            }
            player.removeMetadata("hidden", plugin);
            player.sendMessage(ColorUtil.colorize(plugin.getConfigManager().getConfig().getString("messages.hide_off")));
        }

        return true;
    }
}

