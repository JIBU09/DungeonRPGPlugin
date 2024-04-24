package de.jibu.dungeonrpgplugin.Listener;

import de.jibu.dungeonrpgplugin.Generator.RoomSpawner;
import de.jibu.dungeonrpgplugin.JUtils.BlockMetaDatas;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Objects;

public class DungeonBuffChestFunction implements Listener {

    private final RoomSpawner roomSpawner;

    StartDungeonEvent startDungeonEvent = new StartDungeonEvent(null);

    public DungeonBuffChestFunction(RoomSpawner roomSpawner) {
        this.roomSpawner = roomSpawner;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getClickedBlock() != null) {
            if (event.getClickedBlock().getType().equals(Material.CHEST)) {
                if (BlockMetaDatas.hasMetaData(Objects.requireNonNull(event.getClickedBlock()).getState(), "DungeonBuffChest")) {
                    player.closeInventory();
                    player.playSound(event.getClickedBlock().getLocation(), Sound.BLOCK_CHEST_OPEN, 1,1);
                    playBuffEffect(event.getClickedBlock().getLocation().add(0.5,0,0.5));
                    Location location = event.getClickedBlock().getLocation().add(1,0,0);
                    event.getClickedBlock().setType(location.getBlock().getType());

                }
            }
        }
    }

    public static void playBuffEffect(Location chestLocation) {
        World world = chestLocation.getWorld();

        Particle.DustOptions particleOptions = new Particle.DustOptions(Color.LIME, 5);

        for (int i = 0; i < 30; i++) {
            double offsetX = Math.random() * 2 - 1;
            double offsetY = Math.random() * 2 - 1;
            double offsetZ = Math.random() * 2 - 1;

            Location particleLocation = chestLocation.clone().add(offsetX, offsetY, offsetZ);

            world.spawnParticle(Particle.REDSTONE, particleLocation, 1, particleOptions);
        }
    }

}
