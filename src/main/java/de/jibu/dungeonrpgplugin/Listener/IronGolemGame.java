package de.jibu.dungeonrpgplugin.Listener;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class IronGolemGame implements Listener, CommandExecutor {

    public boolean gameActive = false;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!gameActive) {
            gameActive = true;
            Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 1, 1));
            Bukkit.getOnlinePlayers().forEach(player -> player.sendTitle("§8GAME §aON", ""));
            Bukkit.getOnlinePlayers().forEach(player -> player.setGameMode(GameMode.ADVENTURE));
            Bukkit.getOnlinePlayers().forEach(player -> player.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 99999 * 20, 100, true, false, false)));
            Bukkit.getOnlinePlayers().forEach(player -> player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 99999 * 20, 100, true, false, false)));
            Bukkit.getOnlinePlayers().forEach(player -> spawnTheWarden(player, 50));
        } else {
            deactivate();
        }
        return false;
    }


    private void spawnTheWarden(Player player, int count) {
        for (int i = 0; i < count; i++) {
            Warden warden = (Warden) player.getWorld().spawnEntity(player.getLocation(), EntityType.WARDEN);
            warden.addScoreboardTag("gamesWarden");
        }
    }


    private void deactivate() {
        gameActive = false;
        Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.ENTITY_WARDEN_SONIC_BOOM, 1, 1));
        Bukkit.getOnlinePlayers().forEach(player -> player.sendTitle("§8GAME §cOFF", ""));
        Bukkit.getOnlinePlayers().forEach(player -> player.setGameMode(GameMode.SPECTATOR));
        Bukkit.getOnlinePlayers().forEach(player -> player.removePotionEffect(PotionEffectType.DARKNESS));
        Bukkit.getOnlinePlayers().forEach(player -> player.removePotionEffect(PotionEffectType.SATURATION));
        Bukkit.getWorlds().forEach(world -> {
            for (Entity entity : world.getEntitiesByClasses(LivingEntity.class)) {
                if (entity instanceof LivingEntity && entity.getScoreboardTags().contains("gamesWarden")) {
                    entity.remove();
                }
            }
        });

    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            if (event.getEntity() instanceof Player) {
                if (gameActive) {
                    event.setDamage(event.getDamage() * 1000);
                    Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.ENTITY_WITHER_DEATH, 1, 1));
                    Bukkit.getOnlinePlayers().forEach(player -> player.sendTitle("§6KILL", "§9" + event.getEntity().getName() + " §8died!"));
                    deactivate();
                }
            }
        } else {
            if (event.getEntity() instanceof Player) {
                Player player = (Player) event.getEntity();
                player.setHealth(20);
            }
        }
    }


    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (gameActive) {
            event.setCancelled(true);
            event.setFoodLevel(20);
        }
    }
}
