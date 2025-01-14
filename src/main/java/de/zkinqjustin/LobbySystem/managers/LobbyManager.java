package de.zkinqjustin.LobbySystem.managers;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import de.zkinqjustin.LobbySystem.LobbySystem;

public class LobbyManager {

    private final LobbySystem plugin;
    private Location pos1;
    private Location pos2;
    private Location minLobby;
    private Location maxLobby;

    public LobbyManager(LobbySystem plugin) {
        this.plugin = plugin;
        loadLobbyArea();
    }

    public void loadLobbyArea() {
        World world = plugin.getServer().getWorlds().get(0);
        double x1 = plugin.getConfigManager().getConfig().getDouble("lobby.pos1.x");
        double y1 = plugin.getConfigManager().getConfig().getDouble("lobby.pos1.y");
        double z1 = plugin.getConfigManager().getConfig().getDouble("lobby.pos1.z");
        double x2 = plugin.getConfigManager().getConfig().getDouble("lobby.pos2.x");
        double y2 = plugin.getConfigManager().getConfig().getDouble("lobby.pos2.y");
        double z2 = plugin.getConfigManager().getConfig().getDouble("lobby.pos2.z");

        pos1 = new Location(world, x1, y1, z1);
        pos2 = new Location(world, x2, y2, z2);

        setLobbyArea();
    }

    public void setPos1(Location loc) {
        pos1 = loc;
        plugin.getConfigManager().getConfig().set("lobby.pos1.x", loc.getX());
        plugin.getConfigManager().getConfig().set("lobby.pos1.y", loc.getY());
        plugin.getConfigManager().getConfig().set("lobby.pos1.z", loc.getZ());
        plugin.getConfigManager().saveConfig();
    }

    public void setPos2(Location loc) {
        pos2 = loc;
        plugin.getConfigManager().getConfig().set("lobby.pos2.x", loc.getX());
        plugin.getConfigManager().getConfig().set("lobby.pos2.y", loc.getY());
        plugin.getConfigManager().getConfig().set("lobby.pos2.z", loc.getZ());
        plugin.getConfigManager().saveConfig();
    }

    public void setLobbyArea() {
        if (pos1 != null && pos2 != null) {
            minLobby = new Location(pos1.getWorld(),
                    Math.min(pos1.getX(), pos2.getX()),
                    Math.min(pos1.getY(), pos2.getY()),
                    Math.min(pos1.getZ(), pos2.getZ()));
            maxLobby = new Location(pos1.getWorld(),
                    Math.max(pos1.getX(), pos2.getX()),
                    Math.max(pos1.getY(), pos2.getY()),
                    Math.max(pos1.getZ(), pos2.getZ()));
        }
    }

    public boolean isInLobby(Player player) {
        if (minLobby == null || maxLobby == null || player.hasPermission("lobbysystem.bypass")) return true;
        Location loc = player.getLocation();
        return loc.getX() >= minLobby.getX() && loc.getX() <= maxLobby.getX() &&
                loc.getY() >= minLobby.getY() && loc.getY() <= maxLobby.getY() &&
                loc.getZ() >= minLobby.getZ() && loc.getZ() <= maxLobby.getZ();
    }

    public Location getPos1() {
        return pos1;
    }

    public Location getPos2() {
        return pos2;
    }
}

