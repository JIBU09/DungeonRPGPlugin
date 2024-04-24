package de.jibu.dungeonrpgplugin.Generator;

import de.jibu.dungeonrpgplugin.JUtils.BlockMetaDatas;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Smoker;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Chest;
import org.bukkit.block.data.type.ChiseledBookshelf;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;

import java.util.*;

public class RoomSpawner {


    private final Material[] SDungeonBlocks = {
            Material.PURPUR_BLOCK,
            Material.PURPUR_PILLAR,
            Material.STRIPPED_BIRCH_WOOD,
            Material.BIRCH_WOOD,
            Material.END_STONE,
            Material.END_STONE_BRICKS
    };
    private final Material[] SDungeonStairs = {
            Material.END_STONE_BRICK_STAIRS,
            Material.PURPUR_STAIRS
    };

    private final Material[] SDungeonWall = {
            Material.END_STONE_BRICK_WALL,
    };

    private final Material[] SDungeonSlab = {
            Material.END_STONE_BRICK_SLAB,
    };

    private Material getRandomSDungeonMaterial() {
        return SDungeonBlocks[random.nextInt(SDungeonBlocks.length)];
    }

    private Material getRandomSDungeonStairMaterial() {
        return SDungeonStairs[random.nextInt(SDungeonStairs.length)];
    }

    private Material getRandomSDungeonWallMaterial() {
        return SDungeonWall[random.nextInt(SDungeonWall.length)];
    }

    private Material getRandomSDungeonSlabMaterial() {
        return SDungeonSlab[random.nextInt(SDungeonSlab.length)];
    }


    // TODO         A DUNGEON
    private final Material[] ADungeonBlocks = {
            Material.NETHER_GOLD_ORE,
            Material.NETHER_QUARTZ_ORE,
            Material.RED_NETHER_BRICKS,
            Material.CHISELED_NETHER_BRICKS,
            Material.CRACKED_NETHER_BRICKS,
            Material.NETHERRACK,
            Material.NETHER_WART_BLOCK,
            Material.CRIMSON_HYPHAE,

            Material.BASALT,
            Material.SMOOTH_BASALT,
            Material.CRACKED_POLISHED_BLACKSTONE_BRICKS,
            Material.POLISHED_BLACKSTONE_BRICKS,
            Material.CHISELED_POLISHED_BLACKSTONE,
            Material.POLISHED_BLACKSTONE,
            Material.SMOOTH_BASALT,

    };
    private final Material[] ADungeonStairs = {
            Material.NETHER_BRICK_STAIRS,
            Material.RED_NETHER_BRICK_STAIRS,
            Material.POLISHED_BLACKSTONE_BRICK_STAIRS,
            Material.BLACKSTONE_STAIRS,
            Material.RED_NETHER_BRICK_STAIRS,
    };

    private final Material[] ADungeonWalls = {
            Material.POLISHED_BLACKSTONE_BRICK_WALL,
            Material.BLACKSTONE_WALL,
            Material.POLISHED_BLACKSTONE_WALL,
            Material.NETHER_BRICK_WALL,
            Material.RED_NETHER_BRICK_WALL,
    };

    private final Material[] ADungeonSlab = {
            Material.POLISHED_BLACKSTONE_BRICK_SLAB,
            Material.BLACKSTONE_SLAB,
            Material.POLISHED_BLACKSTONE_SLAB,
            Material.NETHER_BRICK_SLAB,
            Material.RED_NETHER_BRICK_SLAB,
    };

    private Material getRandomADungeonMaterial() {
        return ADungeonBlocks[random.nextInt(ADungeonBlocks.length)];
    }

    private Material getRandomAStairDungeonMaterial() {
        return ADungeonStairs[random.nextInt(ADungeonStairs.length)];
    }

    private Material getRandomAWallDungeonMaterial() {
        return ADungeonWalls[random.nextInt(ADungeonWalls.length)];
    }

    private Material getRandomASlabDungeonMaterial() {
        return ADungeonSlab[random.nextInt(ADungeonSlab.length)];
    }


    // TODO         B DUNGEON

    private final Material[] BDungeonBlocks = {
            Material.DEEPSLATE_TILES,
            Material.POLISHED_DEEPSLATE,
            Material.CHISELED_DEEPSLATE,
            Material.DEEPSLATE_BRICKS,
            Material.SCULK,
            Material.CRACKED_DEEPSLATE_BRICKS,
            Material.CRACKED_DEEPSLATE_TILES,
    };
    private final Material[] BDungeonStairs = {
            Material.DEEPSLATE_BRICK_STAIRS,
            Material.DEEPSLATE_TILE_STAIRS,
            Material.POLISHED_DEEPSLATE_STAIRS,

    };

    private final Material[] BDungeonWalls = {
            Material.DEEPSLATE_BRICK_WALL,
            Material.DEEPSLATE_TILE_WALL,
            Material.POLISHED_DEEPSLATE_WALL,

    };
    private final Material[] BDungeonSlab = {
            Material.DEEPSLATE_BRICK_SLAB,
            Material.DEEPSLATE_TILE_SLAB,
            Material.POLISHED_DEEPSLATE_SLAB,

    };

    private Material getRandomBDungeonMaterial() {
        return BDungeonBlocks[random.nextInt(BDungeonBlocks.length)];
    }

    private Material getRandomBStairDungeonMaterial() {
        return BDungeonStairs[random.nextInt(BDungeonStairs.length)];
    }

    private Material getRandomBWallDungeonMaterial() {
        return BDungeonWalls[random.nextInt(BDungeonWalls.length)];
    }

    private Material getRandomBSlabDungeonMaterial() {
        return BDungeonSlab[random.nextInt(BDungeonSlab.length)];
    }


    // TODO         C DUNGEON

    private final Material[] CDungeonBlocks = {
            Material.STONE_BRICKS,
            Material.MOSSY_STONE_BRICKS,
            Material.CRACKED_STONE_BRICKS,
            Material.CHISELED_STONE_BRICKS
    };

    private final Material[] CDungeonStairs = {
            Material.STONE_BRICK_STAIRS,
            Material.MOSSY_STONE_BRICK_STAIRS,

    };

    private final Material[] CDungeonWalls = {
            Material.STONE_BRICK_WALL,
            Material.MOSSY_STONE_BRICK_WALL,
    };
    private final Material[] CDungeonSlab = {
            Material.STONE_BRICK_SLAB,
            Material.MOSSY_STONE_BRICK_SLAB,

    };

    private Material getRandomCDungeonMaterial() {
        return CDungeonBlocks[random.nextInt(CDungeonBlocks.length)];
    }

    private Material getRandomCStairDungeonMaterial() {
        return CDungeonStairs[random.nextInt(CDungeonStairs.length)];
    }

    private Material getRandomCWallDungeonMaterial() {
        return CDungeonWalls[random.nextInt(CDungeonWalls.length)];
    }

    private Material getRandomCSlabDungeonMaterial() {
        return CDungeonSlab[random.nextInt(CDungeonSlab.length)];
    }

    // TODO         D DUNGEON

    private final Material[] DDungeonBlocks = {
            Material.PRISMARINE_BRICKS,
            Material.DARK_PRISMARINE,
            Material.PRISMARINE,
            Material.SEA_LANTERN,

//            Material.POLISHED_ANDESITE,
//            Material.POLISHED_DIORITE,
//            Material.POLISHED_GRANITE,
    };

    private final Material[] DDungeonStairs = {
            Material.PRISMARINE_STAIRS,
            Material.DARK_PRISMARINE_STAIRS,
            Material.PRISMARINE_BRICK_STAIRS,

//            Material.POLISHED_ANDESITE_STAIRS,
//            Material.POLISHED_DIORITE_STAIRS,
//            Material.POLISHED_GRANITE_STAIRS,

    };

    private final Material[] DDungeonWalls = {
            Material.PRISMARINE_WALL,
    };
    private final Material[] DDungeonSlab = {
            Material.PRISMARINE_SLAB,
            Material.DARK_PRISMARINE_SLAB,
            Material.PRISMARINE_BRICK_SLAB,

//            Material.POLISHED_ANDESITE_SLAB,
//            Material.POLISHED_DIORITE_SLAB,
//            Material.POLISHED_GRANITE_SLAB,

    };

    private Material getRandomDDungeonMaterial() {
        return DDungeonBlocks[random.nextInt(DDungeonBlocks.length)];
    }

    private Material getRandomDStairDungeonMaterial() {
        return DDungeonStairs[random.nextInt(DDungeonStairs.length)];
    }

    private Material getRandomDWallDungeonMaterial() {
        return DDungeonWalls[random.nextInt(DDungeonWalls.length)];
    }

    private Material getRandomDSlabDungeonMaterial() {
        return DDungeonSlab[random.nextInt(DDungeonSlab.length)];
    }


    private final Random random = new Random();
    private final List<Block> cubeBlocks = new ArrayList<>();

    public void generateRooms(Location startLocation, String tier) {
        int maxroomx = 9 + tierToInteger(tier);
        int maxroomz = 9 + tierToInteger(tier);
        int maxroomy = 9 + tierToInteger(tier);
        int minroomx = 6 + tierToInteger(tier);
        int minroomy = 6 + tierToInteger(tier);
        int minroomz = 6 + tierToInteger(tier);
        int columns = random.nextInt((3 + tierToInteger(tier)) - (2 + tierToInteger(tier)) + 1) + 2 + tierToInteger(tier);
        int rows = random.nextInt((3 + tierToInteger(tier)) - (2 + tierToInteger(tier)) + 1) + 2 + tierToInteger(tier);
        int roomCount = 0;
        int spawnRoomRandom = random.nextInt(rows * columns - 1) + 1;
        for (int col = -2; col < columns * maxroomx + columns * 2; col++) {
            for (int row = -2; row < rows * maxroomz + rows * 2; row++) {
                for (int y = -5; y < maxroomy + 5; y++) {
                    Location blockLocation = startLocation.clone().add(col, y, row);

                    placeBlockForTier(blockLocation.getBlock(), tier);

                    cubeBlocks.add(blockLocation.getBlock());

                }
            }
        }
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                roomCount += 1;
                int randomRoomSizex = random.nextInt(maxroomx - minroomx + 1) + minroomx;
                int randomRoomSizey = random.nextInt(maxroomz - minroomz + 1) + minroomy;
                int randomRoomSizez = random.nextInt(maxroomy - minroomy + 1) + minroomz;
                int xOffset = col * maxroomx + col * 2;
                int zOffset = row * maxroomz + 2 + row * 2;

                Location roomLocation = startLocation.clone().add(xOffset, 0, zOffset);
                generateRoom(roomLocation, randomRoomSizex, randomRoomSizez, randomRoomSizey, tier, true);
                if (roomCount == spawnRoomRandom) {
                    Location spawnRoomLocation = roomLocation.add(0, randomRoomSizey - 1, 0);
                    generateSpawnRoom(randomRoomSizex, randomRoomSizez, spawnRoomLocation, tier);
                }
            }
        }
    }

    private final List<Integer> roomMid = new ArrayList<>();

    public void generateRoom(Location roomLocation, int sizex, int sizez, int sizey, String tier, boolean removable) {
        roomMid.clear();
        roomMid.add(sizex / 2);
        roomMid.add(sizez / 2);
        for (int x = 0; x < sizex; x++) {
            for (int y = 0; y < sizey; y++) {
                for (int z = 0; z < sizez; z++) {
                    if (x > 0 && x < sizex - 1 && z > 0 && z < sizez - 1 && y > 0 && y < sizey - 1) {
                        Location voidLocation = roomLocation.clone().add(x, y, z);
                        voidLocation.getBlock().setType(Material.VOID_AIR);

                        if (removable) {
                            cubeBlocks.add(voidLocation.getBlock());
                        }
                    }

                    if (x == 0 || y == 0 || z == 0 || x == sizex - 1 || y == sizey - 1 || z == sizez - 1 || x == 1 && z == 1 || x == sizex - 2 && z == sizez - 2 || x == 1 && z == sizez - 2 || x == sizex - 2 && z == 1) {
                        Location blockLocation = roomLocation.clone().add(x, y, z);


                        placeBlockForTier(blockLocation.getBlock(), tier);
                        int randomNumber = random.nextInt(750);
                        switch (randomNumber) {
                            case 0:
                                spawnBuffChest(blockLocation);
                                break;
                        }
                        if (removable) {
                            cubeBlocks.add(blockLocation.getBlock());
                        }
                    }

                    if (x > 1 && x < sizex - 2 && z == 1 && y == 1) {
                        Location blockLocation = roomLocation.clone().add(x, y, z);
                        placeStairForTier(blockLocation.getBlock(), tier);

                        Block block = blockLocation.getBlock();
                        BlockData blockData = block.getBlockData();

                        if (blockData instanceof Stairs) {
                            Stairs stairs = (Stairs) blockData;
                            stairs.setFacing(BlockFace.NORTH);

                            block.setBlockData(stairs);
                        }
                    } else if (z > 1 && z < sizez - 2 && x == 1 && y == 1) {
                        Location blockLocation = roomLocation.clone().add(x, y, z);
                        placeStairForTier(blockLocation.getBlock(), tier);

                        Block block = blockLocation.getBlock();
                        BlockData blockData = block.getBlockData();

                        if (blockData instanceof Stairs) {
                            Stairs stairs = (Stairs) blockData;
                            stairs.setFacing(BlockFace.WEST);

                            block.setBlockData(stairs);
                        }
                    } else if (z > 1 && z < sizez - 2 && x == sizex - 2 && y == 1) {
                        Location blockLocation = roomLocation.clone().add(x, y, z);
                        placeStairForTier(blockLocation.getBlock(), tier);

                        Block block = blockLocation.getBlock();
                        BlockData blockData = block.getBlockData();

                        if (blockData instanceof Stairs) {
                            Stairs stairs = (Stairs) blockData;
                            stairs.setFacing(BlockFace.EAST);

                            block.setBlockData(stairs);
                        }
                    } else if (x > 1 && x < sizex - 2 && z == sizez - 2 && y == 1) {
                        Location blockLocation = roomLocation.clone().add(x, y, z);
                        placeStairForTier(blockLocation.getBlock(), tier);

                        Block block = blockLocation.getBlock();
                        BlockData blockData = block.getBlockData();

                        if (blockData instanceof Stairs) {
                            Stairs stairs = (Stairs) blockData;
                            stairs.setFacing(BlockFace.SOUTH);
                            block.setBlockData(stairs);
                        }
                    }

                    if (x > 1 && x < sizex - 2 && z == 1 && y == sizey - 2) {
                        Location blockLocation = roomLocation.clone().add(x, y, z);
                        placeStairForTier(blockLocation.getBlock(), tier);

                        Block block = blockLocation.getBlock();
                        BlockData blockData = block.getBlockData();

                        if (blockData instanceof Stairs) {
                            Stairs stairs = (Stairs) blockData;
                            stairs.setFacing(BlockFace.NORTH);
                            stairs.setHalf(Bisected.Half.TOP);
                            block.setBlockData(stairs);
                        }
                    } else if (z > 1 && z < sizez - 2 && x == 1 && y == sizey - 2) {
                        Location blockLocation = roomLocation.clone().add(x, y, z);
                        placeStairForTier(blockLocation.getBlock(), tier);

                        Block block = blockLocation.getBlock();
                        BlockData blockData = block.getBlockData();

                        if (blockData instanceof Stairs) {
                            Stairs stairs = (Stairs) blockData;
                            stairs.setFacing(BlockFace.WEST);
                            stairs.setHalf(Bisected.Half.TOP);
                            block.setBlockData(stairs);
                        }
                    } else if (z > 1 && z < sizez - 2 && x == sizex - 2 && y == sizey - 2) {
                        Location blockLocation = roomLocation.clone().add(x, y, z);
                        placeStairForTier(blockLocation.getBlock(), tier);

                        Block block = blockLocation.getBlock();
                        BlockData blockData = block.getBlockData();

                        if (blockData instanceof Stairs) {
                            Stairs stairs = (Stairs) blockData;
                            stairs.setFacing(BlockFace.EAST);
                            stairs.setHalf(Bisected.Half.TOP);
                            block.setBlockData(stairs);
                        }
                    } else if (x > 1 && x < sizex - 2 && z == sizez - 2 && y == sizey - 2) {
                        Location blockLocation = roomLocation.clone().add(x, y, z);
                        placeStairForTier(blockLocation.getBlock(), tier);

                        Block block = blockLocation.getBlock();
                        BlockData blockData = block.getBlockData();

                        if (blockData instanceof Stairs) {
                            Stairs stairs = (Stairs) blockData;
                            stairs.setFacing(BlockFace.SOUTH);
                            stairs.setHalf(Bisected.Half.TOP);
                            block.setBlockData(stairs);
                        }
                    }
                }
            }
        }

        for (int x = 0; x < sizex; x++) {
            for (int y = 0; y < sizey; y++) {
                for (int z = 0; z < sizez; z++) {
                    if (x == roomMid.get(0) && y < 4 && y > 0 && z == sizez - 1 || x == roomMid.get(0) - 1 && y < 4 && y > 0 && z == sizez - 1) {
                        Location blockLocation = roomLocation.clone().add(x, y, z);
                        blockLocation.add(0, 0, 1);
                        if (blockLocation.getBlock().getType() != Material.VOID_AIR) {
                            blockLocation.getBlock().setType(Material.AIR);
                            blockLocation.add(0, 0, -1);
                            blockLocation.getBlock().setType(Material.AIR);
                            blockLocation.add(0, 0, -1);
                            blockLocation.getBlock().setType(Material.AIR);
                            blockLocation.add(0, 0, 2);
                            for (int zair = 0; zair < 7; zair++) {
                                blockLocation.add(0, 0, 1);
                                blockLocation.getBlock().setType(Material.CAVE_AIR);
                                cubeBlocks.add(blockLocation.getBlock());
                            }
                        }
                    }
                    if (z == roomMid.get(1) && y < 4 && y > 0 && x == sizex - 1 || z == roomMid.get(1) - 1 && y < 4 && y > 0 && x == sizex - 1) {
                        Location blockLocation = roomLocation.clone().add(x, y, z);
                        blockLocation.add(1, 0, 0);
                        if (blockLocation.getBlock().getType() != Material.VOID_AIR) {
                            blockLocation.getBlock().setType(Material.AIR);
                            blockLocation.add(-1, 0, 0);
                            blockLocation.getBlock().setType(Material.AIR);
                            blockLocation.add(-1, 0, 0);
                            blockLocation.getBlock().setType(Material.AIR);
                            blockLocation.add(2, 0, 0);
                            for (int xair = 0; xair < 7; xair++) {
                                blockLocation.add(1, 0, 0);
                                blockLocation.getBlock().setType(Material.CAVE_AIR);
                                cubeBlocks.add(blockLocation.getBlock());
                            }
                        }
                    }
                }
            }
        }
        for (int x = 0; x < sizex; x++) {
            for (int y = 0; y < sizey; y++) {
                for (int z = 0; z < sizez; z++) {
                    if ((x == 0 || y == 0 || z == 0 || x == sizex - 1 || y == sizey - 1 || z == sizez - 1 || x == 1 && z == 1 || x == sizex - 2 && z == sizez - 2 || x == 1 && z == sizez - 2 || x == sizex - 2 && z == 1)) {
                        Location blockLocation = roomLocation.clone().add(x, y, z);
                        blockLocation.add(-1, 0, 0);
                        if (blockLocation.getBlock().getType() == Material.CAVE_AIR) {
                            blockLocation.add(1, 0, 0);
                            blockLocation.getBlock().setType(Material.AIR);
                            tierStairListLength(roomLocation, tier, x, z, y, true);
                        } else {
                            blockLocation.add(1, 0, -1);
                            if (blockLocation.getBlock().getType() == Material.CAVE_AIR) {
                                blockLocation.add(0, 0, 1);
                                blockLocation.getBlock().setType(Material.AIR);
                                tierStairListLength(roomLocation, tier, x, z, y, false);
                            }
                        }
                    }
                }
            }
        }
        /*
        if (Objects.equals(tier, "s")){

        } else if (Objects.equals(tier, "a")) {

        }else if (Objects.equals(tier, "b")) {

        }else if (Objects.equals(tier, "c")) {

        }else if (Objects.equals(tier, "d")) {

        }

         */
        int randomInterior = random.nextInt(6 - 0 + 1) + 0;
        int randomInterior2 = random.nextInt(6 - 0 + 1) + 0;
        if (randomInterior == 0 || randomInterior2 == 0) {
            for (int x = 0; x < sizex; x++) {
                for (int z = 0; z < sizez; z++) {
                    for (int y = 0; y < sizey; y++) {
                        Location blocklocation = roomLocation.clone().add(x - 1, y, z);
                        if (blocklocation.getBlock().getType() != Material.AIR) {
                            blocklocation.add(1, 0, 0);
                            if (x == 1 && z < sizez - 2 && z > 1 && y == 1) {
                                BlockData blockData = blocklocation.getBlock().getBlockData();
                                int randomBlock = random.nextInt(10 - 1 + 1) + 1;
                                if (randomBlock == 1) {
                                    blocklocation.getBlock().setType(Material.FURNACE);
                                    setBlockFacing(blocklocation.getBlock(), BlockFace.EAST);
                                }
                                if (randomBlock == 2) {
                                    blocklocation.getBlock().setType(Material.BLAST_FURNACE);
                                    setBlockFacing(blocklocation.getBlock(), BlockFace.EAST);
                                }
                                if (randomBlock == 3) {
                                    blocklocation.getBlock().setType(Material.SMITHING_TABLE);
                                    setBlockFacing(blocklocation.getBlock(), BlockFace.EAST);
                                }
                                if (randomBlock == 4) {
                                    blocklocation.getBlock().setType(Material.FLETCHING_TABLE);
                                    setBlockFacing(blocklocation.getBlock(), BlockFace.EAST);
                                }
                                if (randomBlock == 5) {
                                    blocklocation.getBlock().setType(Material.CRAFTING_TABLE);
                                    setBlockFacing(blocklocation.getBlock(), BlockFace.EAST);
                                }
                                if (randomBlock == 6) {
                                    blocklocation.getBlock().setType(Material.CARTOGRAPHY_TABLE);
                                    setBlockFacing(blocklocation.getBlock(), BlockFace.EAST);
                                }
                                if (randomBlock == 7) {
                                    blocklocation.getBlock().setType(Material.LOOM);
                                    setBlockFacing(blocklocation.getBlock(), BlockFace.EAST);
                                }
                                if (randomBlock == 8) {
                                    blocklocation.getBlock().setType(Material.SMOKER);
                                    setBlockFacing(blocklocation.getBlock(), BlockFace.EAST);
                                }
                                if (randomBlock == 9) {
                                    blocklocation.getBlock().setType(Material.CHISELED_BOOKSHELF);
                                    setBlockFacing(blocklocation.getBlock(), BlockFace.EAST);
                                }
                                if (randomBlock == 10) {
                                    blocklocation.getBlock().setType(Material.BOOKSHELF);
                                }
                            }
                            if (x == 1 && z < sizez - 2 && z > 1 && y == 2) {
                                Block block = blocklocation.getBlock();
                                int randomBlock2 = random.nextInt(12 - 1 + 1) + 1;
                                if (randomBlock2 == 1) {
                                    blocklocation.getBlock().setType(Material.CHISELED_BOOKSHELF);
                                    setBlockFacing(blocklocation.getBlock(), BlockFace.EAST);
                                }
                                if (randomBlock2 == 2) {
                                    blocklocation.getBlock().setType(Material.BOOKSHELF);
                                }
                                if (randomBlock2 == 3) {
                                    blocklocation.getBlock().setType(Material.CAULDRON);
                                }
                                if (randomBlock2 == 4) {
                                    setBlockFacing(blocklocation.getBlock(), BlockFace.EAST);
                                }
                                if (randomBlock2 == 5) {
                                    blocklocation.getBlock().setType(Material.STONECUTTER);
                                    setBlockFacing(blocklocation.getBlock(), BlockFace.EAST);
                                }
                                if (randomBlock2 == 6) {
                                    blocklocation.getBlock().setType(Material.ANVIL);
                                }
                                if (randomBlock2 == 7) {
                                    blocklocation.getBlock().setType(Material.COMPOSTER);
                                }
                                if (randomBlock2 == 8) {
                                    blocklocation.getBlock().setType(Material.BELL);
                                    setBlockFacing(blocklocation.getBlock(), BlockFace.EAST);
                                    block.getState().update();
                                }
                            }
                        }
                    }
                }
            }
        }

        if (randomInterior == 1 && Objects.equals(tier, "s") || randomInterior == 1 && Objects.equals(tier, "a") || randomInterior == 1 && Objects.equals(tier, "b") || randomInterior2 == 1 && Objects.equals(tier, "s") || randomInterior2 == 1 && Objects.equals(tier, "a") || randomInterior2 == 1 && Objects.equals(tier, "b")) {
            for (int x = 0; x < sizex; x++) {
                for (int z = 0; z < sizez; z++) {
                    for (int y = 0; y < sizey; y++) {
                        Location blocklocation = roomLocation.clone().add(x, y, z);
                        if (x == sizex - 4 && z == 1 && y < 7 || x == sizex - 2 && z == 3 && y < 7 || x == sizex - 3 && z == 2 && y < 7) {
                            placeBlockForTier(blocklocation.getBlock(), tier);
                        }
                        if (x == sizex - 4 && z == 2 && y == 1 || x == sizex - 3 && z == 3 && y == 2 || x == sizex - 2 && z == 4 && y == 3 || x == sizex - 4 && z == 2 && y == 6 || x == sizex - 3 && z == 3 && y == 5 || x == sizex - 2 && z == 5 && y == 4 ) {
                            placeSlabForTier(blocklocation.getBlock(), tier);
                            BlockData blockData = blocklocation.getBlock().getBlockData();
                            if (blockData instanceof Slab) {
                                Slab directional = (Slab) blockData;
                                blocklocation.getBlock().setBlockData(directional);
                                directional.setType(Slab.Type.TOP);
                            }
                        }
                        if (x == sizex-3 && z == 1 && y == 1 || x == sizex-2 && z == 2 && y == 1){
                            spawnBuffChest(blocklocation);
                        }
                    }
                }
            }
        }
        if (randomInterior == 2) {
            for (int x = 0; x < sizex; x++) {
                for (int z = 0; z < sizez; z++) {
                    for (int y = 0; y < sizey; y++) {
                        Location blocklocation = roomLocation.clone().add(x, y, z);
                        if (blocklocation.getBlock().getType() == Material.VOID_AIR) {
                            int randomGlassSpawn = random.nextInt(25 + 1);
                            if (randomGlassSpawn == 3){
                                if (tier.equalsIgnoreCase("s")){
                                    blocklocation.getBlock().setType(Material.PURPLE_STAINED_GLASS_PANE);
                                }
                                if (tier.equalsIgnoreCase("a")){
                                    blocklocation.getBlock().setType(Material.RED_STAINED_GLASS_PANE);
                                }
                                if (tier.equalsIgnoreCase("b")){
                                    blocklocation.getBlock().setType(Material.BLACK_STAINED_GLASS_PANE);
                                }
                                if (tier.equalsIgnoreCase("c")){
                                    blocklocation.getBlock().setType(Material.GREEN_STAINED_GLASS_PANE);
                                }
                                if (tier.equalsIgnoreCase("d")){
                                    blocklocation.getBlock().setType(Material.CYAN_STAINED_GLASS_PANE);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (randomInterior == 3 || randomInterior2 == 3) {
            for (int x = 0; x < sizex; x++) {
                for (int z = 0; z < sizez; z++) {
                    for (int y = 0; y < sizey; y++) {
                        Location blocklocation = roomLocation.clone().add(x, y, z);
                        if (blocklocation.getBlock().getType() == Material.VOID_AIR && y > 4 && y < sizey - 4) {
                            int randomLanternSpawn = random.nextInt(100 + 1);
                            if (randomLanternSpawn == 3) {
                                blocklocation.getBlock().setType(Material.LANTERN);
                                for (int i = y; i < sizey - 2; i++) {
                                    blocklocation.add(0,1,0);
                                    blocklocation.getBlock().setType(Material.CHAIN);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (randomInterior == 4 && Objects.equals(tier, "s") ||randomInterior == 4 && Objects.equals(tier, "a") || randomInterior == 4 && Objects.equals(tier, "b")){
            for (int x = 0; x < sizex; x++) {
                for (int z = 0; z < sizez; z++) {
                    for (int y = 0; y < sizey; y++) {
                        Location blocklocation = roomLocation.clone().add(x, y, z);
                        if (y == sizey - 4 ){
                            blocklocation.getBlock().setType(Material.SPRUCE_PLANKS);
                        }
                        if (y == sizey - 4 && x == roomMid.get(0) && z != roomMid.get(1) || y == sizey - 4 && x != roomMid.get(0) && z == roomMid.get(1)){
                            blocklocation.getBlock().setType(Material.DARK_OAK_WOOD);
                        }
                        if (y > sizey - 4 && x == 2 && z == 2 || y > sizey - 4 && x == sizex -2 && z == 2 || y > sizey - 4 && x == 2 && z == sizez - 2 || y > sizey - 4 && x == sizex -2 && z == sizez - 2){
                            blocklocation.getBlock().setType(Material.CHAIN);
                        }
                        if (y <= sizey-4 && x == roomMid.get(0) -1 && z == roomMid.get(1)){
                            blocklocation.getBlock().setType(Material.DARK_OAK_WOOD);
                        }
                        if (y <= sizey-4 && x == roomMid.get(0) && z == roomMid.get(1)){
                            blocklocation.getBlock().setType(Material.LADDER);
                            setBlockFacing(blocklocation.getBlock(), BlockFace.EAST);
                        }
                        if (y == sizey-4 && x == roomMid.get(0) && z == roomMid.get(1)){
                            blocklocation.getBlock().setType(Material.SPRUCE_TRAPDOOR);
                            setBlockFacing(blocklocation.getBlock(), BlockFace.EAST);
                        }
                        if (y == sizey - 3 && x == roomMid.get(0) && z == 1 || y == sizey - 3 && x == 1 && z == roomMid.get(1) || y == sizey - 3 && x == roomMid.get(0)  && z == sizez - 1 || y == sizey - 3 && x == sizex -1 && z == roomMid.get(1) ){
                            int randomNumber = random.nextInt(3);
                            switch (randomNumber) {
                                case 0:
                                    spawnBuffChest(blocklocation);
                                    break;
                            }
                        }
                    }
                }
            }
        }
    }

    private void spawnBuffChest(Location location) {
        location.getBlock().setType(Material.CHEST);
        BlockMetaDatas.addMetaData(location.getBlock().getState(), "DungeonBuffChest");
    }
    public static void setBlockFacing(Block block, BlockFace facing) {
        if (block == null || block.getType() == Material.AIR) {
            return;
        }

        BlockData blockData = block.getBlockData();

        if (blockData instanceof Directional) {
            Directional directional = (Directional) blockData;
            directional.setFacing(facing);
            block.setBlockData(directional);
        }
    }

    private void tierStairListLength(Location roomlocation, String tier, int x, int z, int y, boolean zOrx) {
        for (int i = 0; i < tierToStair(tier) - 1; i++) {
            replaceStair(roomlocation, tier, i, x, z, y, zOrx);
        }
    }

    private void replaceStair(Location roomlocation, String tier, int index, int x, int z, int y, boolean zOrx) {
        if (zOrx) {
            Location location = roomlocation.clone().add(x + 1, y, z);
            if (Objects.requireNonNull(getAllStairsForTier(location.getBlock(), tier)).get(index) == Objects.requireNonNull(getAllStairsForTier(location.getBlock(), tier)).get(index)) {
                location.getBlock().setType(Material.AIR);
            }
        } else {
            Location location = roomlocation.clone().add(x, y, z + 1);
            if (Objects.requireNonNull(getAllStairsForTier(location.getBlock(), tier)).get(index) == Objects.requireNonNull(getAllStairsForTier(location.getBlock(), tier)).get(index)) {
                location.getBlock().setType(Material.AIR);
            }
        }
    }


    private static String generateRandomRoomID() {
        Random random = new Random();
        int randomNumber = random.nextInt(10000);
        String formattedNumber = String.format("%04d", randomNumber);
        return formattedNumber;
    }

    private final List<Integer> midLocation = new ArrayList<>();
    public Location playerDungeonSpawn;

    private void generateSpawnRoom(int sizex, int sizez, Location roomLocation, String tier) {
        midLocation.add(sizex / 2);
        midLocation.add(sizez / 2);
        for (int x = 0; x < sizex; x++) {
            for (int y = 0; y < 6; y++) {
                for (int z = 0; z < sizez; z++) {
                    Location voidLocation = roomLocation.clone().add(x, y, z);
                    voidLocation.getBlock().setType(Material.VOID_AIR);
                    cubeBlocks.add(voidLocation.getBlock());
                    if (y == 1 && x == midLocation.get(0) && z == midLocation.get(1)) {
                        Location playerDungeonSpawnLocation = roomLocation.clone().add(x, y, z);
                        playerDungeonSpawn = playerDungeonSpawnLocation;
                    } else if (y == 0 && x != 0 && x != sizex - 1 && z != 0 && z != sizez - 1) {
                        Location blockLocation = roomLocation.clone().add(x, y, z);

                        blockLocation.getBlock().setType(Material.GLASS);
                        BlockMetaDatas.addMetaData(blockLocation.getBlock().getState(), "StartDungeonGlass");
                        BlockMetaDatas.removeMetaData(blockLocation.getBlock().getState(), "noBreakButDrop");

                        cubeBlocks.add(blockLocation.getBlock());
                    } else if (x == 0 || z == 0 || x == sizex - 1 || y == 6 - 1 || z == sizez - 1) {
                        Location blockLocation = roomLocation.clone().add(x, y, z);

                        placeBlockForTier(blockLocation.getBlock(), tier);
                        //blockLocation.getBlock().setType(Material.CYAN_WOOL);

                        cubeBlocks.add(blockLocation.getBlock());
                    }
                }
            }
        }
        Bukkit.broadcastMessage("§aGenerated SpawnRoom");
    }

    public int tierToInteger(String tier) {
        int value = 1;
        if (tier.equalsIgnoreCase("s")) {
            return value + 4;
        } else if (tier.equalsIgnoreCase("a")) {
            return value + 3;
        } else if (tier.equalsIgnoreCase("b")) {
            return value + 2;
        } else if (tier.equalsIgnoreCase("c")) {
            return value + 1;
        } else if (tier.equalsIgnoreCase("d")) {
            return value;
        }
        return value;
    }


    private int tierToStair(String tier) {
        if (tier.equalsIgnoreCase("s")) {
            return SDungeonStairs.length;
        } else if (tier.equalsIgnoreCase("a")) {
            return ADungeonStairs.length - 2;
        } else if (tier.equalsIgnoreCase("b")) {
            return BDungeonStairs.length;
        } else if (tier.equalsIgnoreCase("c")) {
            return CDungeonStairs.length;
        } else if (tier.equalsIgnoreCase("d")) {
            return DDungeonStairs.length;
        }
        return 1;
    }

    private void placeBlockForTier(Block block, String tier) {
        BlockMetaDatas.addMetaData(block.getState(), "noBreakButDrop");
        if (tier.equalsIgnoreCase("s")) {
            Material material = getRandomSDungeonMaterial();
            block.setType(material);
        } else if (tier.equalsIgnoreCase("a")) {
            Material material = getRandomADungeonMaterial();
            block.setType(material);
        } else if (tier.equalsIgnoreCase("b")) {
            Material material = getRandomBDungeonMaterial();
            block.setType(material);
        } else if (tier.equalsIgnoreCase("c")) {
            Material material = getRandomCDungeonMaterial();
            block.setType(material);
        } else if (tier.equalsIgnoreCase("d")) {
            Material material = getRandomDDungeonMaterial();
            block.setType(material);
        }
    }

    private Material getBlockForTier(Block block, String tier) {
        if (tier.equalsIgnoreCase("s")) {
            return getRandomSDungeonMaterial();
        } else if (tier.equalsIgnoreCase("a")) {
            return getRandomADungeonMaterial();
        } else if (tier.equalsIgnoreCase("b")) {
            return getRandomBDungeonMaterial();
        } else if (tier.equalsIgnoreCase("c")) {
            return getRandomCDungeonMaterial();
        } else if (tier.equalsIgnoreCase("d")) {
            return getRandomDDungeonMaterial();
        }
        return null;
    }

    private void placeStairForTier(Block block, String tier) {
        BlockMetaDatas.addMetaData(block.getState(), "noBreakButDrop");
        if (tier.equalsIgnoreCase("s")) {
            Material material = getRandomSDungeonStairMaterial();
            block.setType(material);
        } else if (tier.equalsIgnoreCase("a")) {
            Material material = getRandomAStairDungeonMaterial();
            block.setType(material);
        } else if (tier.equalsIgnoreCase("b")) {
            Material material = getRandomBStairDungeonMaterial();
            block.setType(material);
        } else if (tier.equalsIgnoreCase("c")) {
            Material material = getRandomCStairDungeonMaterial();
            block.setType(material);
        } else if (tier.equalsIgnoreCase("d")) {
            Material material = getRandomDStairDungeonMaterial();
            block.setType(material);
        }
    }

    private Material getStairForTier(Block block, String tier) {
        if (tier.equalsIgnoreCase("s")) {
            return getRandomSDungeonStairMaterial();
        } else if (tier.equalsIgnoreCase("a")) {
            return getRandomAStairDungeonMaterial();
        } else if (tier.equalsIgnoreCase("b")) {
            return getRandomBStairDungeonMaterial();
        } else if (tier.equalsIgnoreCase("c")) {
            return getRandomCStairDungeonMaterial();
        } else if (tier.equalsIgnoreCase("d")) {
            return getRandomDStairDungeonMaterial();
        }
        return null;
    }

    private List<Material> getAllStairsForTier(Block block, String tier) {
        List<Material> materials = new ArrayList<>();
        if (tier.equalsIgnoreCase("s")) {
            materials.addAll(Arrays.asList(SDungeonStairs));
            return materials;
        } else if (tier.equalsIgnoreCase("a")) {
            materials.addAll(Arrays.asList(SDungeonStairs));
            return materials;
        } else if (tier.equalsIgnoreCase("b")) {
            materials.addAll(Arrays.asList(SDungeonStairs));
            return materials;
        } else if (tier.equalsIgnoreCase("c")) {
            materials.addAll(Arrays.asList(SDungeonStairs));
            return materials;
        } else if (tier.equalsIgnoreCase("d")) {
            materials.addAll(Arrays.asList(SDungeonStairs));
            return materials;
        }
        return null;
    }


    private void placeWallForTier(Block block, String tier) {
        BlockMetaDatas.addMetaData(block.getState(), "noBreakButDrop");
        if (tier.equalsIgnoreCase("s")) {
            Material material = getRandomSDungeonWallMaterial();
            block.setType(material);
        } else if (tier.equalsIgnoreCase("a")) {
            Material material = getRandomAWallDungeonMaterial();
            block.setType(material);
        } else if (tier.equalsIgnoreCase("b")) {
            Material material = getRandomBWallDungeonMaterial();
            block.setType(material);
        } else if (tier.equalsIgnoreCase("c")) {
            Material material = getRandomCWallDungeonMaterial();
            block.setType(material);
        } else if (tier.equalsIgnoreCase("d")) {
            Material material = getRandomDWallDungeonMaterial();
            block.setType(material);
        }
    }

    private Material getWallForTier(Block block, String tier) {
        if (tier.equalsIgnoreCase("s")) {
            return getRandomSDungeonMaterial();
        } else if (tier.equalsIgnoreCase("a")) {
            return getRandomAWallDungeonMaterial();
        } else if (tier.equalsIgnoreCase("b")) {
            return getRandomBWallDungeonMaterial();
        } else if (tier.equalsIgnoreCase("c")) {
            return getRandomCWallDungeonMaterial();
        } else if (tier.equalsIgnoreCase("d")) {
            return getRandomDWallDungeonMaterial();
        }
        return null;
    }

    private void placeSlabForTier(Block block, String tier) {
        BlockMetaDatas.addMetaData(block.getState(), "noBreakButDrop");
        if (tier.equalsIgnoreCase("s")) {
            Material material = getRandomSDungeonSlabMaterial();
            block.setType(material);
        } else if (tier.equalsIgnoreCase("a")) {
            Material material = getRandomASlabDungeonMaterial();
            block.setType(material);
        } else if (tier.equalsIgnoreCase("b")) {
            Material material = getRandomBSlabDungeonMaterial();
            block.setType(material);
        } else if (tier.equalsIgnoreCase("c")) {
            Material material = getRandomCSlabDungeonMaterial();
            block.setType(material);
        } else if (tier.equalsIgnoreCase("d")) {
            Material material = getRandomDSlabDungeonMaterial();
            block.setType(material);
        }
    }

    private List<Material> getAllSlabsForTier(String tier) {
        List<Material> materials = new ArrayList<>();
        if (tier.equalsIgnoreCase("s")) {
            materials.addAll(Arrays.asList(SDungeonSlab));
            return materials;
        } else if (tier.equalsIgnoreCase("a")) {
            materials.addAll(Arrays.asList(ADungeonSlab));
            return materials;
        } else if (tier.equalsIgnoreCase("b")) {
            materials.addAll(Arrays.asList(BDungeonSlab));
            return materials;
        } else if (tier.equalsIgnoreCase("c")) {
            materials.addAll(Arrays.asList(CDungeonSlab));
            return materials;
        } else if (tier.equalsIgnoreCase("d")) {
            materials.addAll(Arrays.asList(DDungeonSlab));
            return materials;
        }
        return null;
    }


    public void clearRooms() {
        for (Block block : cubeBlocks) {
            block.setType(Material.AIR);
            BlockMetaDatas.removeMetaData(block.getState(), "noBreakButDrop");
            BlockMetaDatas.removeMetaData(block.getState(), "StartDungeonGlass");
            BlockMetaDatas.removeMetaData(block.getState(), "DungeonBuffChest");
        }
        cubeBlocks.clear();
    }
}

