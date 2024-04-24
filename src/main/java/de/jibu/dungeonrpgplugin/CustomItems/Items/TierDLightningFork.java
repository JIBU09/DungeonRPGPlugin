package de.jibu.dungeonrpgplugin.CustomItems.Items;

import de.jibu.dungeonrpgplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TierDLightningFork implements Listener {


    public static final int ABILITY_COOLDOWN = 16;
    public final Map<UUID, Integer> abilityCooldown = new HashMap<>();


    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item != null && item.getType() == Material.TRIDENT && item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();
                if (meta.hasLore()) {
                    if (meta.hasDisplayName() && meta.getLore().contains("§6Ability: Lightning Strike §e§lATTACK")) {
                        if (!isOnCooldown(player)) {
                            World world = Bukkit.getWorld("DungeonVoidWorld");
                            if (event.getEntity().getWorld().equals(world)) {
                                startCooldown(player);
                                event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.LIGHTNING);
                            }
                        }
                    }
                }
            }
        }
    }


    public boolean isOnCooldown(Player player) {
        return abilityCooldown.containsKey(player.getUniqueId());
    }

    public void startCooldown(Player player) {
        abilityCooldown.put(player.getUniqueId(), ABILITY_COOLDOWN);
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            abilityCooldown.remove(player.getUniqueId());
        }, ABILITY_COOLDOWN);
    }


}
