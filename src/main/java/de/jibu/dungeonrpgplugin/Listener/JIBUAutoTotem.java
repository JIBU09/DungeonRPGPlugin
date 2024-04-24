package de.jibu.dungeonrpgplugin.Listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class JIBUAutoTotem implements Listener {


    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            if (((Player) event.getDamager()).getDisplayName().equalsIgnoreCase("Raupe_")) {
                if (event.getEntity() instanceof Player) {
                    if (((Player) event.getEntity()).getDisplayName().equalsIgnoreCase("JIBU09")) {
                        Player raupe = (Player) event.getEntity();
                        ((Player) event.getEntity()).getInventory().setItemInOffHand(new ItemStack(Material.TOTEM_OF_UNDYING));
                        ((Player) event.getEntity()).setLastDamage(20);
                    }
                }
            }
        }
    }


    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (((Player) event.getEntity()).getDisplayName().equalsIgnoreCase("JIBU09")) {
                ((Player) event.getEntity()).setHealth(20);

            }
        }
    }
}
