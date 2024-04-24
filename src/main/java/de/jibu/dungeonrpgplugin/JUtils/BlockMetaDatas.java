package de.jibu.dungeonrpgplugin.JUtils;

import de.jibu.dungeonrpgplugin.Main;
import org.bukkit.GameMode;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BlockMetaDatas implements Listener {

    public static void addMetaData(BlockState state, String dataName) {
        state.setMetadata(dataName, new FixedMetadataValue(Main.getInstance(), 1));
    }

    public static void removeMetaData(BlockState state, String dataName) {
        state.removeMetadata(dataName, Main.getInstance());
    }

    public static boolean hasMetaData(BlockState state, String dataName) {
        return state.hasMetadata(dataName);
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getState().hasMetadata("noDrop")) {
            event.setDropItems(false);
            event.setCancelled(true);
        }
        if (event.getBlock().getState().hasMetadata("noBreakButDrop")) {
            if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(event.getBlock().getType()));
                event.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onBlockBurn(BlockBurnEvent event) {
        if (event.getBlock().getState().hasMetadata("noBreakButDrop")) {
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(event.getBlock().getType()));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockDropItem(BlockDropItemEvent event) {
        if (event.getBlock().getState().hasMetadata("noDrop")) {
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getPlayer().getWorld().getBlockAt(event.getPlayer().getLocation()).getState().hasMetadata("blood")) {
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20, 4, true, false, false));

        }
    }
}