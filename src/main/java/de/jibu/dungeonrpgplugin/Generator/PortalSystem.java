package de.jibu.dungeonrpgplugin.Generator;

import de.jibu.dungeonrpgplugin.Config.AmountConfig;
import de.jibu.dungeonrpgplugin.Config.DefaultConfig;
import de.jibu.dungeonrpgplugin.Config.InventoryConfig;
import de.jibu.dungeonrpgplugin.CustomItems.ItemManager;
import de.jibu.dungeonrpgplugin.CustomItems.Items.TierDItems;
import de.jibu.dungeonrpgplugin.JUtils.BlockMetaDatas;
import de.jibu.dungeonrpgplugin.Config.DungeonLocationConfig;
import de.jibu.dungeonrpgplugin.JUtils.ItemBuilder;
import de.jibu.dungeonrpgplugin.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;

import java.util.*;

public class PortalSystem implements Listener, CommandExecutor {

    private final Random random = new Random();

    Player player;
    Inventory inventorySetupInv = Bukkit.createInventory(player, 54, "§6§lDungeon Inventory");

    private final int setupBlockCooldownValue = 10;
    public final Map<UUID, Integer> setupBlockCooldown = new HashMap<>();

    private final int TELEPORTCOOLDOWN = 10 * 20;
    public final Map<UUID, Integer> teleportCooldownMap = new HashMap<>();

    DefaultConfig defaultConfig;
    double spawnChance = 0.001;

    DungeonLocationConfig dungeonLocationConfig = new DungeonLocationConfig();

    AmountConfig amountConfig = new AmountConfig();

    ItemManager itemManager = new ItemManager();

    @EventHandler
    public void onChunkPopulate(ChunkPopulateEvent event) {
        World world = Bukkit.getWorld("DungeonVoidWorld");
        if (event.getWorld() != world) {
            if (shouldPlaceEndGateWay()) {
                int x = event.getChunk().getX() * 16 + random.nextInt(16);
                int z = event.getChunk().getZ() * 16 + random.nextInt(16);
                int y = getHighestY(event.getWorld(), x, z);
                placeEndGateWay(event.getWorld(), x, y, z);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        createDungeonPortal(player);
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null) {
            if (BlockMetaDatas.hasMetaData(Objects.requireNonNull(event.getClickedBlock()).getState(), "dungeonStartBlock")) {
                if (!isOnSetupBlockCooldown(event.getPlayer())) {
                    if (!event.getPlayer().isSneaking()) {
                        startSetupBlockCooldown(event.getPlayer());
                        dungeonSetupInventory(event.getClickedBlock(), event.getPlayer());
                    }
                }
            }
        }
    }


    private String getPortalIDFromName(String args) {
        String firstName = args.replace("§6§lDungeon Setup §7| §8", "");
        String withoutColors = firstName.replaceAll("§.", "");

        String extractedNumber = withoutColors.replaceAll("[^0-9]", "");

        return "p" + extractedNumber;
    }


    private int getPageNumberFromTitle(String title) {
        String part1 = title.replace("§9§lTeleporter §7| §8Page ", "");
        return Integer.parseInt(part1);
    }

    private int getSlotFromTitle(String title) {
        String part1 = title.replace("§6§lItem Selection §7| §8", "");
        int slot = Integer.parseInt(part1) - 1;
        return slot;
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        InventoryConfig inventoryConfig = new InventoryConfig(player);
        player.sendMessage(event.getSlot() + "");
        if (event.getWhoClicked() instanceof Player) {
            if (event.getView().getTitle().contains("§6§lDungeon Setup §7| ")) {
                if (event.getClickedInventory() != null) {
                    if (event.getCurrentItem() != null) {
                        event.setCancelled(true);
                        if (event.getCurrentItem().getType() == Material.BARREL &&
                                event.getCurrentItem().hasItemMeta() &&
                                event.getCurrentItem().getItemMeta().hasDisplayName() &&
                                event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Dungeon Inventory")) {
                            dungeonBarrelInventory(player);
                        } else if (event.getCurrentItem().getType() == Material.BARRIER &&
                                event.getCurrentItem().hasItemMeta() &&
                                event.getCurrentItem().getItemMeta().hasDisplayName() &&
                                event.getCurrentItem().getItemMeta().getDisplayName().equals("§cClose")) {
                            player.closeInventory();
                        } else if (event.getCurrentItem().getType() == Material.ENDER_PEARL &&
                                event.getCurrentItem().hasItemMeta() &&
                                event.getCurrentItem().getItemMeta().hasDisplayName() &&
                                event.getCurrentItem().getItemMeta().getDisplayName().equals("§9Teleporter")) {
                            teleportMenuInv(player, 1);
                        } else if (event.getCurrentItem().getType() == Material.LIME_STAINED_GLASS &&
                                event.getCurrentItem().hasItemMeta() &&
                                event.getCurrentItem().getItemMeta().hasDisplayName() &&
                                event.getCurrentItem().getItemMeta().getDisplayName().equals("§aPublic Portal")) {
                            dungeonLocationConfig.setPublicPortal(getPortalIDFromName(event.getView().getTitle()), true);
                            player.closeInventory();
                        } else if (event.getCurrentItem().getType() == Material.RED_STAINED_GLASS &&
                                event.getCurrentItem().hasItemMeta() &&
                                event.getCurrentItem().getItemMeta().hasDisplayName() &&
                                event.getCurrentItem().getItemMeta().getDisplayName().equals("§cPrivate Portal")) {
                            dungeonLocationConfig.setPublicPortal(getPortalIDFromName(event.getView().getTitle()), false);
                            player.closeInventory();
                        }
                    }
                }
            }
            if (event.getView().getTitle().contains("§9§lTeleporter")) {
                if (event.getClickedInventory() != null) {
                    if (event.getCurrentItem() != null) {
                        event.setCancelled(true);
                        int pageNumber = getPageNumberFromTitle(event.getView().getTitle());
                        int portalAmount = amountConfig.getPortalAmount();
                        if (event.getCurrentItem().getType() != Material.BARRIER && event.getCurrentItem().getType() != Material.ARROW && event.getCurrentItem().getType() != Material.SPECTRAL_ARROW &&
                                event.getCurrentItem().getType() != Material.BLACK_STAINED_GLASS_PANE && event.getCurrentItem().getType() != Material.GRAY_STAINED_GLASS_PANE &&
                                event.getCurrentItem().hasItemMeta() &&
                                event.getCurrentItem().getItemMeta().hasDisplayName()) {
                            Location location = dungeonLocationConfig.getPortalLocation(getPortalIDFromName(event.getCurrentItem().getItemMeta().getDisplayName()));
                            if (!isOnTeleportCooldown(player)) {
                                if (location != null) {
                                    player.sendMessage("§9Teleporting...");
                                    player.teleport(location.add(0.5, 0.5, 0.5));
                                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                                } else {
                                    player.sendMessage("§cError: Location not found!");
                                }
                            }
                        } else if (event.getCurrentItem().getType() == Material.BARRIER &&
                                event.getCurrentItem().hasItemMeta() &&
                                event.getCurrentItem().getItemMeta().hasDisplayName() &&
                                event.getCurrentItem().getItemMeta().getDisplayName().equals("§cClose")) {
                            player.closeInventory();
                        } else if (event.getCurrentItem().getType() == Material.ARROW &&
                                event.getCurrentItem().hasItemMeta() &&
                                event.getCurrentItem().getItemMeta().hasDisplayName() &&
                                event.getCurrentItem().getItemMeta().getDisplayName().equals("§7Next page")) {
                            teleportMenuInv(player, pageNumber + 1);
                        } else if (event.getCurrentItem().getType() == Material.SPECTRAL_ARROW &&
                                event.getCurrentItem().hasItemMeta() &&
                                event.getCurrentItem().getItemMeta().hasDisplayName() &&
                                event.getCurrentItem().getItemMeta().getDisplayName().equals("§7Last Page")) {
                            if (pageNumber > 1) {
                                teleportMenuInv(player, pageNumber - 1);

                            }
                        }
                    }
                }
            }
            if (event.getView().getTitle().contains("§6§lDungeon Inventory")) {
                if (event.getClickedInventory() != null) {
                    if (event.getCurrentItem() != null) {
                        event.setCancelled(true);
                        if (event.getCurrentItem().getType() == Material.BARRIER &&
                                event.getCurrentItem().hasItemMeta() &&
                                event.getCurrentItem().getItemMeta().hasDisplayName() &&
                                event.getCurrentItem().getItemMeta().getDisplayName().equals("§cClose")) {
                            inventoryConfig.saveCustomInventory(player, event.getInventory());
                            player.closeInventory();
                        }
                        if (event.getCurrentItem().getType() == Material.RED_STAINED_GLASS_PANE |
                                event.getCurrentItem().getType() == Material.GRAY_STAINED_GLASS_PANE &&
                                event.getCurrentItem().hasItemMeta() &&
                                event.getCurrentItem().getItemMeta().hasDisplayName()) {
                            itemManager.openCustomItemsInventoryFromPlayer(player, event.getSlot());
                        }
                    }
                }
            }
            if (event.getView().getTitle().contains("§6§lItem Selection")) {
                if (event.getClickedInventory() != null) {
                    if (event.getCurrentItem() != null) {
                        event.setCancelled(true);
                        inventorySetupInv.setItem(getSlotFromTitle(event.getView().getTitle()), event.getCurrentItem());
                        player.openInventory(inventorySetupInv);
                    }
                }
            }
        }
    }

    private void dungeonBarrelInventory(Player player) {
        for (int i = 0; i < inventorySetupInv.getSize(); i++) {
            inventorySetupInv.setItem(i, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("§8Inventory").addLoreLine("§c").addLoreLine("§7Click to select").getItemStack());
        }
        for (int i = 0; i <= 8; i++) {
            inventorySetupInv.setItem(i, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setName("§cHotbar").addLoreLine("§c").addLoreLine("§7Click to select").getItemStack());
        }
        for (int i = 36; i <= 53; i++) {
            inventorySetupInv.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName("§7").getItemStack());
        }

        inventorySetupInv.setItem(45, new ItemBuilder(Material.CYAN_STAINED_GLASS_PANE).setName("§bArmor Boots").addLoreLine("§c").addLoreLine("§7Click to select").getItemStack());
        inventorySetupInv.setItem(46, new ItemBuilder(Material.CYAN_STAINED_GLASS_PANE).setName("§bArmor Leggings").addLoreLine("§c").addLoreLine("§7Click to select").getItemStack());
        inventorySetupInv.setItem(47, new ItemBuilder(Material.CYAN_STAINED_GLASS_PANE).setName("§bArmor Chestplate").addLoreLine("§c").addLoreLine("§7Click to select").getItemStack());
        inventorySetupInv.setItem(48, new ItemBuilder(Material.CYAN_STAINED_GLASS_PANE).setName("§bArmor Helmet").addLoreLine("§c").addLoreLine("§7Click to select").getItemStack());
        inventorySetupInv.setItem(49, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setName("§aGemstone").addLoreLine("§c").addLoreLine("§7Click to select").getItemStack());
        inventorySetupInv.setItem(51, new ItemBuilder(Material.MAGENTA_GLAZED_TERRACOTTA).setName("§6Load preset").addLoreLine("§c").addLoreLine("§7Click to select!").getItemStack());
        inventorySetupInv.setItem(52, new ItemBuilder(Material.BARRIER).setName("§cClose").addLoreLine("§c").addLoreLine("§7Click to close").getItemStack());
        inventorySetupInv.setItem(53, new ItemBuilder(Material.KNOWLEDGE_BOOK).setName("§6Save as preset").addLoreLine("§c").addLoreLine("§7Click to the preset slot!").getItemStack());
        player.openInventory(inventorySetupInv);
    }

    private void teleportMenuInv(Player player, int startPageNumber) {
        int portalAmount = amountConfig.getPortalAmount();
        int maxItemsPerPage = 45;

        int pageNumber = (int) Math.ceil((double) portalAmount / maxItemsPerPage);

        Inventory teleporterMenuInventory = Bukkit.createInventory(null, 54, "§9§lTeleporter §7| §8Page " + startPageNumber);

        for (int i = (startPageNumber - 1) * maxItemsPerPage; i < startPageNumber * maxItemsPerPage && i < portalAmount; i++) {
            String tier = dungeonLocationConfig.getPortalTier("p" + (i + 1));
            Location location = dungeonLocationConfig.getPortalLocation("p" + (i + 1));

            if (!dungeonLocationConfig.isPublic("p" + (i + 1))) {
                if (location != null) {
                    String world = Objects.requireNonNull(location.getWorld()).getName();
                    switch (tier) {
                        case "s":
                            teleporterMenuInventory.setItem(i - (startPageNumber - 1) * maxItemsPerPage, new ItemBuilder(Material.PURPLE_STAINED_GLASS).setName("§5Portal " + (i + 1)).addLoreLine("§c")
                                    .addLoreLine("§7Tier: §d" + tier).addLoreLine("§7Location:").addLoreLine("  §7World: §a" + world).addLoreLine("  §7X: §a" + location.getX())
                                    .addLoreLine("  §7Y: §a" + location.getY()).addLoreLine("  §7Z: §a" + location.getZ()).getItemStack());
                            break;
                        case "a":
                            teleporterMenuInventory.setItem(i - (startPageNumber - 1) * maxItemsPerPage, new ItemBuilder(Material.RED_STAINED_GLASS).setName("§cPortal " + (i + 1)).addLoreLine("§c")
                                    .addLoreLine("§7Tier: §6" + tier).addLoreLine("§7Location:").addLoreLine("  §7World: §a" + world).addLoreLine("  §7X: §a" + location.getX())
                                    .addLoreLine("  §7Y: §a" + location.getY()).addLoreLine("  §7Z: §a" + location.getZ()).getItemStack());
                            break;
                        case "b":
                            teleporterMenuInventory.setItem(i - (startPageNumber - 1) * maxItemsPerPage, new ItemBuilder(Material.BLACK_STAINED_GLASS).setName("§8Portal " + (i + 1)).addLoreLine("§c")
                                    .addLoreLine("§7Tier: §7" + tier).addLoreLine("§7Location:").addLoreLine("  §7World: §a" + world).addLoreLine("  §7X: §a" + location.getX())
                                    .addLoreLine("  §7Y: §a" + location.getY()).addLoreLine("  §7Z: §a" + location.getZ()).getItemStack());
                            break;
                        case "c":
                            teleporterMenuInventory.setItem(i - (startPageNumber - 1) * maxItemsPerPage, new ItemBuilder(Material.LIME_STAINED_GLASS).setName("§aPortal " + (i + 1)).addLoreLine("§c")
                                    .addLoreLine("§7Tier: §2" + tier).addLoreLine("§7Location:").addLoreLine("  §7World: §a" + world).addLoreLine("  §7X: §a" + location.getX())
                                    .addLoreLine("  §7Y: §a" + location.getY()).addLoreLine("  §7Z: §a" + location.getZ()).getItemStack());
                            break;
                        case "d":
                            teleporterMenuInventory.setItem(i - (startPageNumber - 1) * maxItemsPerPage, new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS).setName("§bPortal " + (i + 1)).addLoreLine("§c")
                                    .addLoreLine("§7Tier: §3" + tier).addLoreLine("§7Location:").addLoreLine("  §7World: §a" + world).addLoreLine("  §7X: §a" + location.getX())
                                    .addLoreLine("  §7Y: §a" + location.getY()).addLoreLine("  §7Z: §a" + location.getZ()).getItemStack());
                            break;

                    }
                } else {
                    teleporterMenuInventory.setItem(i - (startPageNumber - 1) * maxItemsPerPage, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setName("§cError: Location not found!").getItemStack());
                }

            } else {
                teleporterMenuInventory.setItem(i - (startPageNumber - 1) * maxItemsPerPage, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName("§7").getItemStack());
            }
        }
        for (int i = 0; i < teleporterMenuInventory.getSize(); i++) {
            if (teleporterMenuInventory.getItem(i) == null) {
                teleporterMenuInventory.setItem(i, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("§8").getItemStack());
            }
        }
        for (int i = 45; i < teleporterMenuInventory.getSize(); i++) {
            teleporterMenuInventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName("§7").getItemStack());
        }
        teleporterMenuInventory.setItem(45, new ItemBuilder(Material.SPECTRAL_ARROW).setName("§7Last Page").getItemStack());
        teleporterMenuInventory.setItem(49, new ItemBuilder(Material.BARRIER).setName("§cClose").addLoreLine("§c").addLoreLine("§7Click to close").getItemStack());
        if (Objects.requireNonNull(teleporterMenuInventory.getItem(44)).getType() != Material.GRAY_STAINED_GLASS_PANE) {
            teleporterMenuInventory.setItem(53, new ItemBuilder(Material.ARROW).setName("§7Next page").getItemStack());
        }
        player.openInventory(teleporterMenuInventory);
    }

    public boolean isOnSetupBlockCooldown(Player player) {
        return setupBlockCooldown.containsKey(player.getUniqueId());
    }

    public void startSetupBlockCooldown(Player player) {
        setupBlockCooldown.put(player.getUniqueId(), setupBlockCooldownValue);
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            setupBlockCooldown.remove(player.getUniqueId());
        }, setupBlockCooldownValue);
    }

    public boolean isOnTeleportCooldown(Player player) {
        return teleportCooldownMap.containsKey(player.getUniqueId());
    }

    public void startTeleportCooldown(Player player) {
        teleportCooldownMap.put(player.getUniqueId(), TELEPORTCOOLDOWN);
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            teleportCooldownMap.remove(player.getUniqueId());
        }, TELEPORTCOOLDOWN);
    }

    private void dungeonSetupInventory(Block block, Player player) {
        String portalName = dungeonLocationConfig.getPortalNameByLocation(block.getLocation().add(0, 1, 0));
        Inventory hubInv = Bukkit.createInventory(null, 27, "§6§lDungeon Setup §7| §8" + portalName);
        for (int i = 0; i < hubInv.getSize(); i++) {
            hubInv.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName("§7").getItemStack());
        }

        hubInv.setItem(11, new ItemBuilder(Material.BARREL).setName("§9Dungeon Inventory").addLoreLine("§c").addLoreLine("§7Click to setup your inventory").addLoreLine("§7for the coming dungeon!").getItemStack());
        hubInv.setItem(13, new ItemBuilder(Material.ANVIL).setName("§9Item Merger").addLoreLine("§c").addLoreLine("§7Click to merge items").getItemStack());
        hubInv.setItem(15, new ItemBuilder(Material.CRAFTING_TABLE).setName("§6Crafter").addLoreLine("§c").addLoreLine("§7Click to open the crafter").getItemStack());
        hubInv.setItem(22, new ItemBuilder(Material.BARRIER).setName("§cClose").addLoreLine("§c").addLoreLine("§7Click to close").getItemStack());
        if (portalName != null) {
            if (!dungeonLocationConfig.isPublic(portalName)) {
                hubInv.setItem(18, new ItemBuilder(Material.LIME_STAINED_GLASS).setName("§aPublic Portal").addLoreLine("§c").addLoreLine("§aClick to change to private").addLoreLine("§7").addLoreLine("§7Set permission for teleporting").addLoreLine("§7to this portal").getItemStack());
                hubInv.setItem(26, new ItemBuilder(Material.ENDER_PEARL).setName("§9Teleporter").addLoreLine("§c").addLoreLine("§7Click to teleport to other").addLoreLine("§7public portals").getItemStack());
            } else {
                hubInv.setItem(18, new ItemBuilder(Material.RED_STAINED_GLASS).setName("§cPrivate Portal").addLoreLine("§c").addLoreLine("§cClick to change to public").addLoreLine("§7").addLoreLine("§7Set permission for teleporting").addLoreLine("§7to this portal").getItemStack());
            }
        }
        player.openInventory(hubInv);
    }


    public void createDungeonPortal(Player player) {
        Block gateWay = player.getLocation().getBlock();
        BlockState gateWayState = player.getLocation().getBlock().getState();
        if (BlockMetaDatas.hasMetaData(gateWayState, "dungeonGateWay")) {
            player.teleport(gateWay.getLocation().add(0.5, 1, 0.5));
            BlockMetaDatas.removeMetaData(gateWayState, "dungeonGateWay");
            BlockMetaDatas.addMetaData(gateWay.getState(), "dungeonStartBlock");
            amountConfig.addPortalAmount();

            int radius = 6;
            int height = 5;

            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    for (int y = -1; y < height - 1; y++) {
                        Location blockLocation = player.getLocation().clone().add(x, y, z);
                        Block block = player.getWorld().getBlockAt(blockLocation);

                        if (player.getLocation().distance(blockLocation) <= radius) {
                            block.setType(Material.AIR);

                        }
                    }
                }
            }


            Location playerLocation = player.getLocation();

            int zufall = random.nextInt(5);
            switch (zufall) {
                case 0:
                    activatePortalParticlesS(gateWay);
                    placePortalGlassCircle(Material.MAGENTA_STAINED_GLASS, playerLocation);
                    gateWay.setType(Material.PURPLE_STAINED_GLASS);
                    dungeonLocationConfig.setPortalLocation("p" + amountConfig.getPortalAmount(), gateWay.getLocation().add(0, 1, 0));
                    dungeonLocationConfig.setPortalTier("p" + amountConfig.getPortalAmount(), "s");
                    break;
                case 1:
                    activatePortalParticlesA(gateWay);
                    placePortalGlassCircle(Material.ORANGE_STAINED_GLASS, playerLocation);
                    gateWay.setType(Material.RED_STAINED_GLASS);
                    dungeonLocationConfig.setPortalLocation("p" + amountConfig.getPortalAmount(), gateWay.getLocation().add(0, 1, 0));
                    dungeonLocationConfig.setPortalTier("p" + amountConfig.getPortalAmount(), "a");
                    break;
                case 2:
                    activatePortalParticlesB(gateWay);
                    placePortalGlassCircle(Material.GRAY_STAINED_GLASS, playerLocation);
                    gateWay.setType(Material.BLACK_STAINED_GLASS);
                    dungeonLocationConfig.setPortalLocation("p" + amountConfig.getPortalAmount(), gateWay.getLocation().add(0, 1, 0));
                    dungeonLocationConfig.setPortalTier("p" + amountConfig.getPortalAmount(), "b");
                    break;
                case 3:
                    activatePortalParticlesC(gateWay);
                    placePortalGlassCircle(Material.GREEN_STAINED_GLASS, playerLocation);
                    gateWay.setType(Material.LIME_STAINED_GLASS);
                    dungeonLocationConfig.setPortalLocation("p" + amountConfig.getPortalAmount(), gateWay.getLocation().add(0, 1, 0));
                    dungeonLocationConfig.setPortalTier("p" + amountConfig.getPortalAmount(), "c");
                    break;
                case 4:
                    activatePortalParticlesD(gateWay);
                    placePortalGlassCircle(Material.CYAN_STAINED_GLASS, playerLocation);
                    gateWay.setType(Material.LIGHT_BLUE_STAINED_GLASS);
                    dungeonLocationConfig.setPortalLocation("p" + amountConfig.getPortalAmount(), gateWay.getLocation().add(0, 1, 0));
                    dungeonLocationConfig.setPortalTier("p" + amountConfig.getPortalAmount(), "d");
                    break;


            }
            PluginManager pm = Bukkit.getPluginManager();
            pm.registerEvents(new PortalSystem(), Main.getInstance());
        }
    }

    private void placePortalGlassCircle(Material material, Location location) {
        int radius = 6;
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for (int y = -1; y <= -1; y++) {
                    Location blockLocation = location.clone().add(x, y, z);
                    Block block = Objects.requireNonNull(location.getWorld()).getBlockAt(blockLocation);

                    double distanceSquared = Math.pow(x, 2) + Math.pow(z, 2);

                    if (distanceSquared <= Math.pow(radius, 2)) {
                        block.setType(material);
                    }
                }
            }
        }
    }

    private boolean shouldPlaceEndGateWay() {
        return random.nextDouble() < spawnChance;
    }

    public void placeEndGateWay(World world, int x, int y, int z) {
        Block gateWay = world.getBlockAt(x, y + 1, z);
        gateWay.setType(Material.END_GATEWAY);
        BlockMetaDatas.addMetaData(gateWay.getState(), "dungeonGateWay");
        gateWay.getWorld().strikeLightningEffect(gateWay.getLocation());
    }

    private int getHighestY(World world, int x, int z) {
        return world.getHighestBlockYAt(x, z);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            placeEndGateWay(((Player) sender).getWorld(), (int) ((Player) sender).getLocation().getX(), (int) ((Player) sender).getLocation().getY(), (int) ((Player) sender).getLocation().getZ());
            sender.sendMessage("§aDungeon GateWay Placed!");
        }
        return false;
    }


    public static void createParticleCircle(Location centerLocation, int radius, Particle.
            DustOptions dustOptions) {
        World world = centerLocation.getWorld();

        for (int y = 0; y < 5; y++) {
            for (double theta = 0; theta < 2 * Math.PI; theta += Math.PI / 16) {
                double x = Math.cos(theta) * radius;
                double z = Math.sin(theta) * radius;

                Location particleLocation = centerLocation.clone().add(x, y, z);
                world.spawnParticle(Particle.REDSTONE, particleLocation, 10, 0, 0, 0, 1, dustOptions);
            }
        }
    }

    public static void activatePortalParticlesS(Block block) {
        Location blockLocation = block.getLocation().add(0.5, 0, 0.5);

        Particle.DustOptions magentaDustOptions = new Particle.DustOptions(org.bukkit.Color.FUCHSIA, 4);
        Particle.DustOptions purpleDustOptions = new Particle.DustOptions(org.bukkit.Color.PURPLE, 4);

        theSchedulerPart(blockLocation, magentaDustOptions, purpleDustOptions);
    }

    public static void theSchedulerPart(Location blockLocation, Particle.DustOptions
            magentaDustOptions, Particle.DustOptions purpleDustOptions) {
        Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {
            World blockWorld = blockLocation.getWorld();
            if (blockWorld != null) {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    if (player.getWorld() == blockWorld && player.getLocation().distanceSquared(blockLocation) <= 100) {
                        createParticleCircle(blockLocation, 5, magentaDustOptions);
                    } else {
                        for (int y = 0; y < 5; y++) {
                            for (double theta = 0; theta < 2 * Math.PI; theta += Math.PI / 16) {
                                double x = Math.cos(theta) * 5;
                                double z = Math.sin(theta) * 5;

                                Location particleLocation = blockLocation.clone().add(x, y, z);
                                blockWorld.spawnParticle(Particle.REDSTONE, particleLocation, 10, 0, 0, 0, 1, purpleDustOptions);
                            }
                        }
                    }
                });
            }
        }, 0L, 20L);
    }


    public static void activatePortalParticlesA(Block block) {
        Location blockLocation = block.getLocation().add(0.5, 0, 0.5);

        Particle.DustOptions magentaDustOptions = new Particle.DustOptions(Color.RED, 4);
        Particle.DustOptions purpleDustOptions = new Particle.DustOptions(Color.ORANGE, 4);

        theSchedulerPart(blockLocation, magentaDustOptions, purpleDustOptions);

    }


    public static void activatePortalParticlesB(Block block) {
        Location blockLocation = block.getLocation().add(0.5, 0, 0.5);

        Particle.DustOptions magentaDustOptions = new Particle.DustOptions(org.bukkit.Color.BLACK, 4);
        Particle.DustOptions purpleDustOptions = new Particle.DustOptions(Color.GRAY, 4);

        theSchedulerPart(blockLocation, magentaDustOptions, purpleDustOptions);
    }

    public static void activatePortalParticlesC(Block block) {
        Location blockLocation = block.getLocation().add(0.5, 0, 0.5);

        Particle.DustOptions magentaDustOptions = new Particle.DustOptions(Color.GREEN, 4);
        Particle.DustOptions purpleDustOptions = new Particle.DustOptions(Color.LIME, 4);

        theSchedulerPart(blockLocation, magentaDustOptions, purpleDustOptions);
    }

    public static void activatePortalParticlesD(Block block) {
        Location blockLocation = block.getLocation().add(0.5, 0, 0.5);

        Particle.DustOptions magentaDustOptions = new Particle.DustOptions(Color.fromRGB(0, 204, 255), 4);
        Particle.DustOptions purpleDustOptions = new Particle.DustOptions(Color.AQUA, 4);

        theSchedulerPart(blockLocation, magentaDustOptions, purpleDustOptions);
    }
}



