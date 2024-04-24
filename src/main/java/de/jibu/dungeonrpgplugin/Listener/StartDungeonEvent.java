package de.jibu.dungeonrpgplugin.Listener;

import de.jibu.dungeonrpgplugin.Config.InventoryConfig;
import de.jibu.dungeonrpgplugin.CustomItems.Items.TierDItems;
import de.jibu.dungeonrpgplugin.Generator.RoomSpawner;
import de.jibu.dungeonrpgplugin.JUtils.BlockMetaDatas;
import de.jibu.dungeonrpgplugin.Config.DungeonLocationConfig;
import de.jibu.dungeonrpgplugin.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class StartDungeonEvent implements Listener {

    DungeonLocationConfig dungeonLocationConfig = new DungeonLocationConfig();
    private final RoomSpawner roomSpawner;


    private final int START_COOLDOWN = 5 * 20;
    private int time = 5;
    private final Map<UUID, Integer> startCooldown = new HashMap<>();
    private final Map<UUID, Integer> cooldowns = new HashMap<>();

    Map<Integer, List<Player>> dungeons = new HashMap<>();

    int newX = 0;
    int newZ = 0;

    public StartDungeonEvent(RoomSpawner roomSpawner) {
        this.roomSpawner = roomSpawner;
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getClickedBlock() != null) {
            if (player.isSneaking()) {
                if (BlockMetaDatas.hasMetaData(Objects.requireNonNull(event.getClickedBlock()).getState(), "dungeonStartBlock")) {
                    if (!isOnStartCooldown(player)) {
                        startStartCooldown(player);
                        String portalName = dungeonLocationConfig.getPortalNameByLocation(event.getClickedBlock().getLocation().add(0, 1, 0));
                        String tier = dungeonLocationConfig.getPortalTier(portalName);
                        if (tier != null) {
                            World blockWorld = event.getClickedBlock().getWorld();
                            if (blockWorld != null) {
                                if (player.getWorld() == blockWorld) {
                                    Bukkit.getOnlinePlayers().forEach(player1 -> player1.setSneaking(true));
                                    player.sendTitle("§6Generating Dungeon", "§9This might take a sec...");
                                    roomSpawner.generateRooms(getHighestAirBlockLocation().add(0, 10, 0), tier);
                                    startCountdown(player);

                                    Bukkit.getOnlinePlayers().stream()
                                            .filter(player1 -> player1.isSneaking())
                                            .filter(player1 -> player1.getWorld() == blockWorld)
                                            .filter(player1 -> player1.getLocation().distanceSquared(event.getClickedBlock().getLocation()) >= 5)
                                            .forEach(player1 -> {
                                                player1.sendTitle("§6Generating Dungeon", "§9This might take a sec...");
                                                startCountdown(player1);
                                            });
                                }
                            }
                        }
                    }
                }
            }
        }
    }



    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (BlockMetaDatas.hasMetaData(event.getBlock().getState(), "StartDungeonGlass")) {
            removeAdjacentGlass(event.getBlock());
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

                    if (adjacentBlock.getType() == Material.GLASS) {
                        adjacentBlock.setType(Material.AIR);
                        BlockMetaDatas.removeMetaData(adjacentBlock.getState(), "StartDungeonGlass");

                        removeAdjacentGlass(adjacentBlock);
                    }
                }
            }
        }
    }


    public void givePlayerItemOnDungeonStart(Player player) {
        if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
            if (player.getInventory().isEmpty()) {
                player.getInventory().addItem(TierDItems.defaultDagger());
                player.sendMessage("§7Since your inventory is empty you got the starting dagger!");
            }
        }
    }

    private boolean isOnStartCooldown(Player player) {
        return startCooldown.containsKey(player.getUniqueId());
    }

    private void startStartCooldown(Player player) {
        startCooldown.put(player.getUniqueId(), START_COOLDOWN);
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            startCooldown.remove(player.getUniqueId());
        }, START_COOLDOWN);
    }

    public Location getHighestAirBlockLocation() {
        World world = Bukkit.getWorld("DungeonVoidWorld");

        assert world != null;

        if (world.getHighestBlockYAt(newX, newZ) > 250) {
            newX = newX + 1000;
            newZ = newZ + 1000;
            int maxY = world.getHighestBlockYAt(newX, newZ);
            return new Location(world, newX, maxY, newZ);
        } else {
            int maxY = world.getHighestBlockYAt(newX, newZ);
            return new Location(world, newX, maxY, newX);
        }
    }


    private void startCountdown(Player player) {
        InventoryConfig inventoryConfig = new InventoryConfig(player);
        World world = Bukkit.getWorld("DungeonVoidWorld");

        BukkitRunnable runnable = new BukkitRunnable() {
            int time = 5;

            @Override
            public void run() {
                if (time > 0) {
                    player.sendTitle("§6" + time, "§9Starting in...", 0, 20, 10);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 2, 1);
                }

                if (time == 0) {
                    if (world != null) {
                        inventoryConfig.saveInventory(player);
                        player.teleport(roomSpawner.playerDungeonSpawn);
                        inventoryConfig.loadCustomInventory(player);
                        givePlayerItemOnDungeonStart(player);
                    }
                    cancel();
                    return;
                }

                time--;
            }
        };

        runnable.runTaskTimer(Main.getInstance(), 0, 20);
    }


}


