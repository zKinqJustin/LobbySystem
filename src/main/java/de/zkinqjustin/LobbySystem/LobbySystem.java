package de.zkinqjustin.LobbySystem;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import de.zkinqjustin.LobbySystem.commands.*;
import de.zkinqjustin.LobbySystem.listeners.*;
import de.zkinqjustin.LobbySystem.managers.*;
import de.zkinqjustin.LobbySystem.utils.ColorUtil;

public class LobbySystem extends JavaPlugin {

    private static LobbySystem instance;
    private ConfigManager configManager;
    private LobbyManager lobbyManager;
    private WarpManager warpManager;

    @Override
    public void onEnable() {
        instance = this;

        // Initialize managers
        this.configManager = new ConfigManager(this);
        this.lobbyManager = new LobbyManager(this);
        this.warpManager = new WarpManager(this);

        // Register commands
        getCommand("spawn").setExecutor(new SpawnCommand(this));
        getCommand("setspawn").setExecutor(new SetSpawnCommand(this));
        getCommand("pos1").setExecutor(new Pos1Command(this));
        getCommand("pos2").setExecutor(new Pos2Command(this));
        getCommand("setlobby").setExecutor(new SetLobbyCommand(this));
        getCommand("hide").setExecutor(new HideCommand(this));
        getCommand("warp").setExecutor(new WarpCommand(this));
        getCommand("setwarp").setExecutor(new SetWarpCommand(this));
        getCommand("delwarp").setExecutor(new DelWarpCommand(this));

        // Register listeners
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
        Bukkit.getPluginManager().registerEvents(new CompassListener(this), this);
        Bukkit.getPluginManager().registerEvents(new HideListener(this), this);

        // Start scoreboard updater
        new ScoreboardManager(this).runTaskTimer(this, 0L, 20L);

        getLogger().info("LobbySystem has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("LobbySystem has been disabled!");
    }

    public static LobbySystem getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public LobbyManager getLobbyManager() {
        return lobbyManager;
    }

    public WarpManager getWarpManager() {
        return warpManager;
    }

    public ItemStack createCompassItem() {
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta meta = compass.getItemMeta();
        meta.setDisplayName(ColorUtil.colorize(configManager.getConfig().getString("items.compass_name", "&6Teleporter")));
        compass.setItemMeta(meta);
        return compass;
    }

    public ItemStack createHidePlayerItem() {
        ItemStack blazeRod = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = blazeRod.getItemMeta();
        meta.setDisplayName(ColorUtil.colorize(configManager.getConfig().getString("items.hide_player_name", "&eHide Players")));
        blazeRod.setItemMeta(meta);
        return blazeRod;
    }
}

