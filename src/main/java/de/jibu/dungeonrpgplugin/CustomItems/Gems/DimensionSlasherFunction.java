package de.jibu.dungeonrpgplugin.CustomItems.Gems;

import de.jibu.dungeonrpgplugin.JUtils.BlockMetaDatas;
import de.jibu.dungeonrpgplugin.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.*;

public class DimensionSlasherFunction implements Listener {

    public static final int COOLDOWN = 15 * 20;

    public final Map<UUID, Integer> cooldown = new HashMap<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInOffHand();
        if (item != null && item.getType() == Material.DIAMOND_SWORD && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta.hasDisplayName() && meta.getLore().contains("§6Ability: Dimensional Slash §e§lAUTOMATIC")) {
                if (!isOnCooldown(player)) {
                    World world = Bukkit.getWorld("DungeonVoidWorld");
                    if (event.getPlayer().getWorld().equals(world)) {
                        useItem(player);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInOffHand();
        if (item != null && item.getType() == Material.DIAMOND_SWORD && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta.hasDisplayName() && meta.getLore().contains("§6Ability: Dimensional Slash §e§lAUTOMATIC")) {
                if (!isOnCooldown(player)) {
                    World world = Bukkit.getWorld("DungeonVoidWorld");
                    if (event.getPlayer().getWorld().equals(world)) {
                        Player movedPlayer = event.getPlayer();
                        if (player.equals(movedPlayer)) {
                            makeSphere(player);
                        }
                    }
                }
            }
        }
    }



    private void makeSphere(Player player) {
        Location location = player.getLocation().add(0, 3, 0);
        for (double i = 0; i <= Math.PI; i += Math.PI / 20) { // 10 being the amount of circles.
            double radius = Math.sin(i) * 15; // we get the current radius
            double y = Math.cos(i) * 15; // we get the current y value.
            for (double a = 0; a < Math.PI * 2; a += Math.PI / 20) {
                double x = Math.cos(a) * radius; // x-coordinate
                double z = Math.sin(a) * radius; // z-coordinate
                location.add(x, y /* from the previous code */, z); // 'location' is our center.
                location.getWorld().spawnParticle(Particle.SONIC_BOOM, location, 15, 0, 0, 0, 20 * 20);
                location.subtract(x, y, z);
            }
        }
    }

    private void useItem(Player player) {
        if (player.getGameMode() != GameMode.CREATIVE) {
            startCooldown(player);
        }
        Location eyeLocation = player.getEyeLocation().add(0, -0.1, 0);

        // Blickrichtung des Spielers
        org.bukkit.util.Vector direction = eyeLocation.getDirection();

        // Länge des Slashes in Blöcken
        double length = 15.0;

        // Breite des Slashes am Anfang und Ende
        double initialWidth = 1; // Anfangsbreite
        double finalWidth = 10; // Endbreite

        // Schrittweite für die Breitenzunahme pro Block
        double widthIncrement = (finalWidth - initialWidth) / length;

        // Erzeuge den Slash entlang seiner Länge
        for (double i = 0; i < length; i += 0.1) {
            // Berechne die aktuelle Breite des Slashes
            double currentWidth = initialWidth + widthIncrement * i;

            // Berechne die Position entlang des Slashes
            Vector offset = direction.clone().multiply(i);
            Location particleLocation = eyeLocation.clone().add(offset);

            // Erzeuge die Partikel an der aktuellen Position
            player.getWorld().spawnParticle(Particle.REDSTONE, particleLocation, 10, currentWidth / 2, 0.0, currentWidth / 2, new Particle.DustOptions(Color.AQUA, 2.5f));
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WARDEN_SONIC_BOOM, 1, 2);

            // Zerstöre Blöcke im Weg des Slashes
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        Location targetLocation = particleLocation.clone().add(x, y, z);
                        if (!targetLocation.getBlock().getType().isAir()) {
                            player.breakBlock(targetLocation.getBlock());
//                            Block block = targetLocation.getBlock();
//                            player.sendBlockChange(targetLocation, Material.AIR, (byte) 0);
//                            block.getState().setBlockData(Bukkit.createBlockData(Material.AIR));
                            targetLocation.getWorld().playSound(targetLocation, Sound.BLOCK_STONE_BREAK, 1, 1);
                        } else {
                            if (targetLocation.getBlockY() == player.getLocation().getBlockY()) {
                                // Teleportiere den Spieler zur targetLocation
                                player.teleport(targetLocation);
                            }
                        }
                    }
                }
            }

            // Füge Schaden allen Mobs im Weg des Slashes hinzu
            for (Entity entity : player.getNearbyEntities(currentWidth + 2, currentWidth + 2, currentWidth + 2)) {
                if (entity instanceof LivingEntity && !(entity instanceof Player)) {
                    ((LivingEntity) entity).damage(((LivingEntity) entity).getMaxHealth(), player);
                }
            }
        }

    }

    public boolean isOnCooldown(Player player) {
        return cooldown.containsKey(player.getUniqueId());
    }

    public void startCooldown(Player player) {
        cooldown.put(player.getUniqueId(), COOLDOWN);
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            cooldown.remove(player.getUniqueId());
        }, COOLDOWN);
    }
}