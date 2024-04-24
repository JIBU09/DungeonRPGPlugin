package de.jibu.dungeonrpgplugin;

import de.jibu.dungeonrpgplugin.Commands.*;
import de.jibu.dungeonrpgplugin.Config.ItemConfig;
import de.jibu.dungeonrpgplugin.CustomItems.AddItemCommand;
import de.jibu.dungeonrpgplugin.CustomItems.Gems.DimensionSlasherFunction;
import de.jibu.dungeonrpgplugin.CustomItems.Gems.GemItems;
import de.jibu.dungeonrpgplugin.CustomItems.Gems.WaterGemFunction;
import de.jibu.dungeonrpgplugin.CustomItems.ItemManager;
import de.jibu.dungeonrpgplugin.CustomItems.Items.TierDItems;
import de.jibu.dungeonrpgplugin.CustomItems.Items.TierDLightningFork;
import de.jibu.dungeonrpgplugin.CustomItems.Items.TierSAstylisDominate;
import de.jibu.dungeonrpgplugin.CustomItems.Items.TierSItems;
import de.jibu.dungeonrpgplugin.CustomItems.ItemsCommand;
import de.jibu.dungeonrpgplugin.CustomItems.RemoveItemCommand;
import de.jibu.dungeonrpgplugin.Generator.*;
import de.jibu.dungeonrpgplugin.Config.AmountConfig;
import de.jibu.dungeonrpgplugin.JUtils.BlockMetaDatas;
import de.jibu.dungeonrpgplugin.Config.DungeonLocationConfig;
import de.jibu.dungeonrpgplugin.Listener.*;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;


public final class Main extends JavaPlugin {

    private final RandomCubesGenerator cubesGenerator = new RandomCubesGenerator();
    private final RoomTeleporter roomTeleporter = new RoomTeleporter();

    private final TierDItems tierDItems = new TierDItems();
    private final GemItems gemItems = new GemItems();

    AmountConfig amountConfig = new AmountConfig();

    ItemManager itemManager = new ItemManager();
    ItemConfig itemConfig = new ItemConfig();

    private final RoomSpawner roomSpawner = new RoomSpawner();

    private final WaterGemFunction waterGemFunction = new WaterGemFunction();
    private static Main instance;

    DungeonLocationConfig dungeonLocationConfig = new DungeonLocationConfig();



    @Override
    public void onEnable() {

        //Fake Mate
        instance = this;
        getLogger().info("§aDungeonPlugin aktiviert!");
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new Events(), this);
        pm.registerEvents(new BlockMetaDatas(), this);
        pm.registerEvents(new PortalSystem(), this);
        pm.registerEvents(new StartDungeonEvent(roomSpawner), this);
        pm.registerEvents(new DontJoinInDungeonWorldListener(), this);
        pm.registerEvents(new DungeonBuffChestFunction(roomSpawner), this);
        pm.registerEvents(new JIBUAutoTotem(), this);
        pm.registerEvents(new IronGolemGame(), this);
        pm.registerEvents(new TierDLightningFork(), this);
        pm.registerEvents(new WaterGemFunction(), this);
        pm.registerEvents(new DimensionSlasherFunction(), this);
        pm.registerEvents(new TierSAstylisDominate(), this);
        getCommand("spawndungeongateway").setExecutor(new PortalSystem());
        getCommand("teleportdungeongateway").setExecutor(new PortalTPCommand());
        getCommand("generatedungeon").setExecutor(new GenerateRoomsCommand(roomSpawner));
        getCommand("generatedungeonroom").setExecutor(new GenerateRoomCommand(roomSpawner));
        getCommand("spawnmob").setExecutor(new SpawnMobCommand());
        getCommand("game").setExecutor(new IronGolemGame());
        getCommand("items").setExecutor(new ItemsCommand());
        getCommand("items").setTabCompleter(new ItemsCommand());
        getCommand("additem").setExecutor(new AddItemCommand());
        getCommand("removeitem").setExecutor(new RemoveItemCommand());
        getCommand("debugitems").setExecutor(new GiveDebugItemsCommand());
        dungeonLocationConfig.loadPortalLocationsFromConfig();
        activatePortalParticles();
        erstelleVoidWorld();
        loadItemsInConfig();
    }
    @Override
    public void onDisable() {
        roomSpawner.clearRooms();
        waterGemFunction.removeWater();
        deleteDungeonWorld();
        getLogger().info("§cDungeonPlugin deaktiviert!");
        saveConfig();
        saveDefaultConfig();

    }

    public static Main getInstance() {
        return instance;
    }

    public void deleteDungeonWorld() {
        try {
            File dungeonVoidWorld = new File(Bukkit.getWorldContainer(), "DungeonVoidWorld");
            FileUtils.deleteDirectory(dungeonVoidWorld);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void erstelleVoidWorld() {
        try {
            File ffa = new File(Bukkit.getWorldContainer(), "DungeonVoidWorld");
            FileUtils.deleteDirectory(ffa);

            ffa.mkdirs();

            new File(ffa, "data").mkdirs();
            new File(ffa, "datapacks").mkdirs();
            new File(ffa, "playerdata").mkdirs();
            new File(ffa, "poi").mkdirs();
            new File(ffa, "region").mkdirs();


        } catch (IOException e) {
            e.printStackTrace();
        }


        WorldCreator worldCreator = new WorldCreator("DungeonVoidWorld");
        worldCreator.type(WorldType.FLAT);
        worldCreator.createWorld();
        World world = Bukkit.getWorld("DungeonVoidWorld");
        Bukkit.getWorld("DungeonVoidWorld").setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        Bukkit.getWorld("DungeonVoidWorld").setGameRule(GameRule.DO_TRADER_SPAWNING, false);
        Bukkit.getWorld("DungeonVoidWorld").setGameRule(GameRule.DO_PATROL_SPAWNING, false);
        Bukkit.getWorld("DungeonVoidWorld").setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        Bukkit.getWorld("DungeonVoidWorld").setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        Bukkit.getWorld("DungeonVoidWorld").setGameRule(GameRule.DO_MOB_SPAWNING, false);
    }

    public void loadItemsInConfig() {
        System.out.println("Loading items");

        //Gems
        itemConfig.addCustomItemStack(GemItems.waterGem());
        itemConfig.addCustomItemStack(GemItems.dimensionSlasher());
        //TierD
        itemConfig.addCustomItemStack(TierDItems.defaultDagger());
        itemConfig.addCustomItemStack(TierDItems.lightningFork());

        //TierS
        itemConfig.addCustomItemStack(TierSItems.astylisDominate());


        Main.getInstance().getCommand("items").setExecutor(new ItemsCommand());
        Main.getInstance().getCommand("items").setTabCompleter(new ItemsCommand());
        Main.getInstance().getCommand("removeitem").setExecutor(new RemoveItemCommand());
    }


    public void activatePortalParticles() {
        for (int i = 1; i < amountConfig.getPortalAmount() + 1; i++) {
            Location portalLocation = dungeonLocationConfig.getPortalLocation("p" + i);
            String tier = dungeonLocationConfig.getPortalTier("p" + i);
            if (portalLocation != null) {
                Location usingPortalLocation = portalLocation.subtract(0, 1, 0);
                if (tier != null) {
                    switch (tier) {
                        case "s":
                            PortalSystem.activatePortalParticlesS(usingPortalLocation.getBlock());
                            System.out.println("Tier: " + tier);
                            System.out.println("Portal Location: " + usingPortalLocation);
                            BlockMetaDatas.addMetaData(usingPortalLocation.getBlock().getState(), "dungeonStartBlock");
                            break;
                        case "a":
                            PortalSystem.activatePortalParticlesA(usingPortalLocation.getBlock());
                            System.out.println("§cTier: " + tier);
                            System.out.println("Portal Location: " + usingPortalLocation);
                            BlockMetaDatas.addMetaData(usingPortalLocation.getBlock().getState(), "dungeonStartBlock");
                            break;
                        case "b":
                            PortalSystem.activatePortalParticlesB(usingPortalLocation.getBlock());
                            System.out.println("Tier: " + tier);
                            System.out.println("Portal Location: " + usingPortalLocation);
                            BlockMetaDatas.addMetaData(usingPortalLocation.getBlock().getState(), "dungeonStartBlock");
                            break;
                        case "c":
                            PortalSystem.activatePortalParticlesC(usingPortalLocation.getBlock());
                            System.out.println("Tier: " + tier);
                            System.out.println("Portal Location: " + usingPortalLocation);
                            BlockMetaDatas.addMetaData(usingPortalLocation.getBlock().getState(), "dungeonStartBlock");
                            break;
                        case "d":
                            PortalSystem.activatePortalParticlesD(usingPortalLocation.getBlock());
                            System.out.println("Tier: " + tier);
                            System.out.println("Portal Location: " + usingPortalLocation);
                            BlockMetaDatas.addMetaData(usingPortalLocation.getBlock().getState(), "dungeonStartBlock");
                            break;
                    }
                    System.out.println(i);
                }
            }
        }
    }
}

