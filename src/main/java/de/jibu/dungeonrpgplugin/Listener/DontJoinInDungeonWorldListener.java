package de.jibu.dungeonrpgplugin.Listener;

import de.jibu.dungeonrpgplugin.Config.InventoryConfig;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

public class DontJoinInDungeonWorldListener implements Listener {


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.SURVIVAL || event.getPlayer().getGameMode() == GameMode.ADVENTURE) {
            World world = Bukkit.getWorld("DungeonVoidWorld");
            if (event.getPlayer().getWorld() == world) {
                InventoryConfig inventoryConfig = new InventoryConfig(event.getPlayer());
                event.getPlayer().getInventory().clear();
                inventoryConfig.restoreInventory(event.getPlayer());
                event.getPlayer().teleport(Objects.requireNonNull(Bukkit.getWorld("world")).getSpawnLocation());
                event.getPlayer().sendMessage("§bSince you logged out in a dungeon you got teleported to world spawn!");
            }
        }
    }
}
