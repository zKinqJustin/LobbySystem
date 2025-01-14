package de.zkinqjustin.LobbySystem.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import de.zkinqjustin.LobbySystem.LobbySystem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigManager {

    private final LobbySystem plugin;
    private FileConfiguration config;
    private File configFile;

    public ConfigManager(LobbySystem plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    public void loadConfig() {
        if (configFile == null) {
            configFile = new File(plugin.getDataFolder(), "config.yml");
        }
        config = YamlConfiguration.loadConfiguration(configFile);

        // Set default values
        config.addDefault("spawn.x", 0);
        config.addDefault("spawn.y", 64);
        config.addDefault("spawn.z", 0);
        config.addDefault("spawn.yaw", 0);
        config.addDefault("spawn.pitch", 0);
        config.addDefault("spawn.world", "world");
        config.addDefault("lobby.pos1.x", 0);
        config.addDefault("lobby.pos1.y", 0);
        config.addDefault("lobby.pos1.z", 0);
        config.addDefault("lobby.pos2.x", 0);
        config.addDefault("lobby.pos2.y", 0);
        config.addDefault("lobby.pos2.z", 0);
        config.addDefault("messages.prefix", "&7[&bLobby&7] ");
        config.addDefault("messages.spawn_teleport", "&aYou have been teleported to spawn!");
        config.addDefault("messages.spawn_set", "&aSpawn point set to &e{x}, {y}, {z}");
        config.addDefault("messages.pos_set", "&aPosition &e{pos} &aset to &e{x}, {y}, {z}");
        config.addDefault("messages.hide_on", "&aYou are now hidden from other players!");
        config.addDefault("messages.hide_off", "&aYou are now visible to other players!");
        config.addDefault("messages.no_permission", "&cYou don't have permission to use this command!");
        config.addDefault("messages.set_pos_first", "&cPlease set both pos1 and pos2 before setting the lobby area!");
        config.addDefault("messages.lobby_set", "&aLobby area has been set successfully!");
        config.addDefault("messages.teleported_to_spawn", "&aYou have been teleported to spawn as you left the lobby area!");
        config.addDefault("compass.items", new ArrayList<>(Arrays.asList(
                "name:Teleporter;material:COMPASS;slot:0",
                "name:Minigames;material:DIAMOND_SWORD;slot:1",
                "name:Shop;material:EMERALD;slot:2"
        )));
        config.addDefault("compass.commands", new ArrayList<>(Arrays.asList(
                "warp teleporter",
                "warp minigames",
                "warp shop"
        )));
        config.addDefault("items.compass_name", "§6Teleporter");
        config.addDefault("items.hide_player_name", "§eHide Players");

        config.options().copyDefaults(true);
        saveConfig();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save config to " + configFile);
        }
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public List<Map<String, Object>> getCompassItems() {
        List<Map<String, Object>> items = new ArrayList<>();
        List<String> itemStrings = config.getStringList("compass.items");
        List<String> commands = config.getStringList("compass.commands");

        for (int i = 0; i < itemStrings.size() && i < commands.size(); i++) {
            Map<String, Object> item = new HashMap<>();
            String[] parts = itemStrings.get(i).split(";");
            for (String part : parts) {
                String[] keyValue = part.split(":");
                if (keyValue.length == 2) {
                    item.put(keyValue[0], keyValue[1]);
                }
            }
            item.put("command", commands.get(i));
            items.add(item);
        }

        return items;
    }
}

