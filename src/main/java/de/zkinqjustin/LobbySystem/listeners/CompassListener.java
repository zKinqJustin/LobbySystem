package de.zkinqjustin.LobbySystem.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import de.zkinqjustin.LobbySystem.LobbySystem;

import java.util.List;
import java.util.Map;

public class CompassListener implements Listener {

    private final LobbySystem plugin;

    public CompassListener(LobbySystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item != null && item.getType() == Material.COMPASS &&
                (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            event.setCancelled(true);
            openCompassGUI(player);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("Teleporter")) {
            event.setCancelled(true);
            if (event.getCurrentItem() != null) {
                Player player = (Player) event.getWhoClicked();
                int slot = event.getRawSlot();
                List<Map<String, Object>> items = plugin.getConfigManager().getCompassItems();
                for (Map<String, Object> item : items) {
                    if (Integer.parseInt((String) item.get("slot")) == slot) {
                        player.performCommand((String) item.get("command"));
                        player.closeInventory();
                        break;
                    }
                }
            }
        }
    }

    private void openCompassGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 27, "Teleporter");

        List<Map<String, Object>> items = plugin.getConfigManager().getCompassItems();

        for (Map<String, Object> item : items) {
            ItemStack guiItem = new ItemStack(Material.valueOf((String) item.get("material")));
            ItemMeta meta = guiItem.getItemMeta();
            meta.setDisplayName((String) item.get("name"));
            guiItem.setItemMeta(meta);
            gui.setItem(Integer.parseInt((String) item.get("slot")), guiItem);
        }

        player.openInventory(gui);
    }
}

