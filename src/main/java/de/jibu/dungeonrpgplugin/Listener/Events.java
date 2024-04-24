package de.jibu.dungeonrpgplugin.Listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class Events implements Listener {


    private String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1).toLowerCase();
    }


    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            updateHealthDisplay((LivingEntity) event.getEntity());
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getDamager().getScoreboardTags().contains("DungeonMobS")) {
                ((Player) event.getEntity()).setNoDamageTicks(0);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
    }


    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        updateHealthDisplay(event.getEntity());
    }

    private void updateHealthDisplay(LivingEntity entity) {
        double health = entity.getHealth();
        double maxHealth = entity.getMaxHealth();
        double healthPercentage = health / maxHealth;

        ChatColor healthColor;

        // Wähle die Farbe basierend auf dem prozentualen Anteil der Gesundheit
        if (healthPercentage > 0.66) {
            healthColor = ChatColor.GREEN;
        } else if (healthPercentage > 0.33) {
            healthColor = ChatColor.YELLOW;
        } else {
            healthColor = ChatColor.RED;
        }

        // Aktualisiere den Anzeigenamen mit der neuen Farbe
        if (entity.getScoreboardTags().contains("DungeonMobS")) {
            entity.setCustomNameVisible(false);
            String displayName = String.format("§8[§7TierS§8] §c%s " + healthColor + "%d§f/§a%d §c❤",
                    capitalizeFirstLetter(entity.getType().name()), // Erster Buchstabe groß
                    (int) entity.getHealth(), // Gesundheit als ganze Zahl
                    (int) entity.getMaxHealth()); // Maximale Gesundheit als ganze Zahl
            entity.setCustomName(displayName);
        } else if (entity.getScoreboardTags().contains("DungeonMobA")) {
            entity.setCustomNameVisible(false);
            String displayName = String.format("§8[§7TierA§8] §c%s " + healthColor + "%d§f/§a%d §c❤",
                    capitalizeFirstLetter(entity.getType().name()), // Erster Buchstabe groß
                    (int) entity.getHealth(), // Gesundheit als ganze Zahl
                    (int) entity.getMaxHealth()); // Maximale Gesundheit als ganze Zahl
            entity.setCustomName(displayName);
        } else if (entity.getScoreboardTags().contains("DungeonMobB")) {
            entity.setCustomNameVisible(false);
            String displayName = String.format("§8[§7TierB§8] §c%s " + healthColor + "%d§f/§a%d §c❤",
                    capitalizeFirstLetter(entity.getType().name()), // Erster Buchstabe groß
                    (int) entity.getHealth(), // Gesundheit als ganze Zahl
                    (int) entity.getMaxHealth()); // Maximale Gesundheit als ganze Zahl
            entity.setCustomName(displayName);
        }
    }
}
