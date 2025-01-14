package de.zkinqjustin.LobbySystem.listeners;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import de.zkinqjustin.LobbySystem.LobbySystem;
import de.zkinqjustin.LobbySystem.utils.ColorUtil;

public class PlayerListener implements Listener {

    private final LobbySystem plugin;

    public PlayerListener(LobbySystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        teleportToSpawn(player);

        // Give compass and hide player item
        player.getInventory().setItem(1, plugin.createCompassItem());
        player.getInventory().setItem(7, plugin.createHidePlayerItem());

        // Hide players who are in hidden mode
        for (Player onlinePlayer : player.getServer().getOnlinePlayers()) {
            if (onlinePlayer.hasMetadata("hidden") &&
                    (!player.hasPermission("lobbysystem.seehidden") || onlinePlayer.hasPermission("lobbysystem.stayhidden"))) {
                player.hidePlayer(plugin, onlinePlayer);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!plugin.getLobbyManager().isInLobby(player) && !player.hasPermission("lobbysystem.bypass")) {
            teleportToSpawn(player);
            player.sendMessage(ColorUtil.colorize(plugin.getConfigManager().getConfig().getString("messages.teleported_to_spawn")));
        }
    }

    private void teleportToSpawn(Player player) {
        World world = plugin.getServer().getWorld(plugin.getConfigManager().getConfig().getString("spawn.world"));
        if (world == null) {
            world = plugin.getServer().getWorlds().get(0);
        }

        Location spawnLocation = new Location(
                world,
                plugin.getConfigManager().getConfig().getDouble("spawn.x"),
                plugin.getConfigManager().getConfig().getDouble("spawn.y"),
                plugin.getConfigManager().getConfig().getDouble("spawn.z"),
                (float) plugin.getConfigManager().getConfig().getDouble("spawn.yaw"),
                (float) plugin.getConfigManager().getConfig().getDouble("spawn.pitch")
        );

        player.teleport(spawnLocation);
    }
}

