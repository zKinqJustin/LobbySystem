package de.zkinqjustin.LobbySystem.managers;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import de.zkinqjustin.LobbySystem.LobbySystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WarpManager {

    private final LobbySystem plugin;
    private final Map<String, Location> warps;

    public WarpManager(LobbySystem plugin) {
        this.plugin = plugin;
        this.warps = new HashMap<>();
        loadWarps();
    }

    private void loadWarps() {
        ConfigurationSection warpsSection = plugin.getConfigManager().getConfig().getConfigurationSection("warps");
        if (warpsSection != null) {
            for (String warpName : warpsSection.getKeys(false)) {
                ConfigurationSection warpSection = warpsSection.getConfigurationSection(warpName);
                if (warpSection != null) {
                    Location location = new Location(
                            plugin.getServer().getWorld(warpSection.getString("world")),
                            warpSection.getDouble("x"),
                            warpSection.getDouble("y"),
                            warpSection.getDouble("z"),
                            (float) warpSection.getDouble("yaw"),
                            (float) warpSection.getDouble("pitch")
                    );
                    warps.put(warpName, location);
                }
            }
        }
    }

    public void saveWarp(String name, Location location) {
        warps.put(name, location);
        ConfigurationSection warpsSection = plugin.getConfigManager().getConfig().createSection("warps." + name);
        warpsSection.set("world", location.getWorld().getName());
        warpsSection.set("x", location.getX());
        warpsSection.set("y", location.getY());
        warpsSection.set("z", location.getZ());
        warpsSection.set("yaw", location.getYaw());
        warpsSection.set("pitch", location.getPitch());
        plugin.getConfigManager().saveConfig();
    }

    public boolean deleteWarp(String name) {
        if (warps.containsKey(name)) {
            warps.remove(name);
            plugin.getConfigManager().getConfig().set("warps." + name, null);
            plugin.getConfigManager().saveConfig();
            return true;
        }
        return false;
    }

    public Location getWarp(String name) {
        return warps.get(name);
    }

    public Set<String> getWarpNames() {
        return warps.keySet();
    }
}

