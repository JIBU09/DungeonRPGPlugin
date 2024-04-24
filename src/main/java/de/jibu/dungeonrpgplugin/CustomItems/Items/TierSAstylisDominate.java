package de.jibu.dungeonrpgplugin.CustomItems.Items;

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

public class TierSAstylisDominate implements Listener {

    public static final int COOLDOWN = 60 * 20;

    public static final int ABILITY_DURATION = 15 * 20;
    public static int radius = 15;

    public final Map<UUID, Integer> duration = new HashMap<>();
    public final Map<UUID, Integer> cooldown = new HashMap<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item != null && item.getType() == Material.EMERALD && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta.hasDisplayName() && meta.getLore().contains("§6Ability: Astylis Conqueror §e§lRIGHT CLICK")) {
                if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    if (!isOnCooldown(player)) {
                        World world = Bukkit.getWorld("DungeonVoidWorld");
                        if (event.getPlayer().getWorld().equals(world)) {
                            useItem(player);
                        }
                    } else {
                        player.sendMessage("§cYou're still on cooldown.");
                    }
                }
            }
        }
    }

    private void useItem(Player player) {
        if (player.getGameMode() != GameMode.CREATIVE) {
            startCooldown(player);
        }


        Vector dir = player.getLocation().getDirection();

        Vector increment = dir.multiply(0.1);

        Location lastPoint = player.getLocation().add(0, 1, 0).add(dir.multiply(2.4));


        for (int i = 0; i < 20; i++) {
            player.getWorld().spawnParticle(Particle.SPELL_WITCH, player.getLocation(), i);
            player.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, player.getLocation(), 60);
            lastPoint = lastPoint.add(increment);
        }
        player.getLocation().getWorld().playSound(player.getLocation(), Sound.BLOCK_STONE_BREAK, 1000, 1);
        player.getLocation().getWorld().playSound(player.getLocation(), Sound.ITEM_TRIDENT_THUNDER, 10, 2);
        player.getLocation().getWorld().playSound(player.getLocation(), Sound.ITEM_TRIDENT_THUNDER, 10, 1);
        player.getLocation().getWorld().playSound(player.getLocation(), Sound.ITEM_TRIDENT_THUNDER, 1, 1);
        player.getLocation().getWorld().playSound(player.getLocation(), Sound.ITEM_TRIDENT_THUNDER, 10, 0.1f);
        player.getLocation().getWorld().playSound(player.getLocation(), Sound.AMBIENT_CAVE, 5, 2);
        player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_WARDEN_EMERGE, 1, 2);
        player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_WARDEN_SONIC_BOOM, 1, 0.1f);
        player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_WARDEN_DIG, 1, 1);
        player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_WARDEN_SONIC_BOOM, 1, 0.1f);

        for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
            if (entity instanceof LivingEntity) {
                if (!entity.isDead()) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    if (!(livingEntity instanceof Player)) {
                        livingEntity.setAI(false);
                        livingEntity.setSilent(true);
                        livingEntity.getWorld().playEffect(livingEntity.getLocation().add(0, 1,0), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
                        livingEntity.getWorld().playEffect(livingEntity.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
                        duration.put(player.getUniqueId(), ABILITY_DURATION);
                        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                            duration.remove(player.getUniqueId());
                            livingEntity.setAI(true);
                            livingEntity.setSilent(false);
                        }, ABILITY_DURATION);
                        livingEntity.damage(livingEntity.getMaxHealth() * 0.4, player);
                        livingEntity.setNoDamageTicks(0);
                    }
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
