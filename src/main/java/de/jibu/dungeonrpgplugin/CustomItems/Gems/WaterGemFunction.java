package de.jibu.dungeonrpgplugin.CustomItems.Gems;

import com.sun.jna.platform.win32.WinCrypt;
import de.jibu.dungeonrpgplugin.JUtils.BlockMetaDatas;
import de.jibu.dungeonrpgplugin.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.NumberFormat;
import java.util.*;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.text.NumberFormat;
import java.util.*;

public class WaterGemFunction implements Listener {

    public static final int COOLDOWN = 45 * 20;
    public static final int ABILITY_DURATION = 30 * 20;

    public final Map<UUID, Integer> cooldown = new HashMap<>();
    public final Map<UUID, Integer> duration = new HashMap<>();

    private final List<Block> waterBlocks = new ArrayList<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInOffHand();
        if (item != null && item.getType() == Material.HEART_OF_THE_SEA && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (player.isSneaking()) {
                if (meta.hasDisplayName() && meta.getLore().contains("§6Ability: Gemstone of the sea §e§lSNEAK RIGHT CLICK IN OFFHAND")) {
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
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInOffHand();
        if (item != null && item.getType() == Material.HEART_OF_THE_SEA && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (player.isInWater()) {
                if (meta.hasDisplayName() && meta.getLore().contains("§6Ability: Gemstone of the sea §e§lSNEAK RIGHTCLICK IN OFFHAND")) {
                    World world = Bukkit.getWorld("DungeonVoidWorld");
                    if (event.getPlayer().getWorld().equals(world)) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, 5 * 20, 1));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5 * 20, 1));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 5 * 20, 1));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 5 * 20, 1));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 5 * 20, 1));
                    }

                }
            }
        }
    }

    private void removeAdjacentGlass(Block centerBlock) {
        World world = centerBlock.getWorld();
        int x = centerBlock.getX();
        int y = centerBlock.getY();
        int z = centerBlock.getZ();

        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int yOffset = -1; yOffset <= 1; yOffset++) {
                for (int zOffset = -1; zOffset <= 1; zOffset++) {
                    if (xOffset == 0 && yOffset == 0 && zOffset == 0) {
                        continue;
                    }

                    Block adjacentBlock = world.getBlockAt(x + xOffset, y + yOffset, z + zOffset);

                    if (adjacentBlock.getType() == Material.VOID_AIR) {
                        adjacentBlock.setType(Material.WATER);
                        BlockMetaDatas.removeMetaData(adjacentBlock.getState(), "StartDungeonGlass");
                        removeAdjacentGlass(adjacentBlock);
                    }
                }
            }
        }
    }

    public void removeWater() {
        for (Block block : waterBlocks) {
            block.setType(Material.AIR);
        }
        waterBlocks.clear();
    }

    private void useItem(Player player) {
        if (player.getGameMode() != GameMode.CREATIVE) {
            startCooldown(player);
        }
        removeAdjacentGlass(player.getLocation().getBlock());
        player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_CONVERTED_TO_DROWNED, 1, 0.1f);

        duration.put(player.getUniqueId(), ABILITY_DURATION);
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            duration.remove(player.getUniqueId());
            removeWater();
        }, ABILITY_DURATION);

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



