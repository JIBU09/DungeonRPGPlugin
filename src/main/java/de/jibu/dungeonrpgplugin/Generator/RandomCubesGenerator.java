package de.jibu.dungeonrpgplugin.Generator;

import de.jibu.dungeonrpgplugin.JUtils.BlockMetaDatas;
import de.jibu.dungeonrpgplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class RandomCubesGenerator {

    private final Random random = new Random();
    private final List<Block> cubeBlocks = new ArrayList<>();

    private final Material[] CDungeonBlocks = {
            Material.STONE_BRICKS,
            Material.MOSSY_STONE_BRICKS,
            Material.CRACKED_STONE_BRICKS,
            Material.CHISELED_STONE_BRICKS
    };

    private final Material[] SDungeonBlocks = {
            Material.PURPUR_BLOCK,
            Material.PURPUR_PILLAR,
            Material.STRIPPED_BIRCH_WOOD,
            Material.BIRCH_WOOD,
            Material.END_STONE,
            Material.END_STONE_BRICKS
    };

    private EntityType getSDungeonEntities() {
        EntityType[] spawnableEntityTypes = {
                EntityType.ENDERMAN,
                EntityType.WITHER_SKELETON,
                EntityType.VINDICATOR,
                EntityType.EVOKER
        };

        return spawnableEntityTypes[random.nextInt(spawnableEntityTypes.length)];
    }

    public void placeCubesInGrid(Location baseLocation, int gridSize, int numCubesPerRow, int minSize, int maxSize, String tier) {
        World world = baseLocation.getWorld();

        int cubeSpacing = gridSize / numCubesPerRow;

        for (int x = 0; x < numCubesPerRow; x++) {
            for (int z = 0; z < numCubesPerRow; z++) {
                int xOffset = x * cubeSpacing;
                int zOffset = z * cubeSpacing;

                Location cubeLocation = baseLocation.clone().add(xOffset, 0, zOffset);

                int size = random.nextInt(maxSize - minSize + 1) + minSize;

                if (tier.equalsIgnoreCase("s")) {
                    Material material = getSMaterial();
                    buildHollowCube(cubeLocation, size, material, tier);
                    Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> spawnMobs(calculateCenter(cubeLocation, size), size, tier), 20L);
                    Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> spawnMobs(calculateCenter(cubeLocation, size), size, tier), 60L);
                } else if (tier.equalsIgnoreCase("a")) {
                    // Füge hier die Logik für Tier A ein, wenn nötig
                } else if (tier.equalsIgnoreCase("b")) {
                    // Füge hier die Logik für Tier B ein, wenn nötig
                } else if (tier.equalsIgnoreCase("c")) {
                    Material material = getCMaterial();
                    buildHollowCube(cubeLocation, size, material, tier);
                } else if (tier.equalsIgnoreCase("d")) {
                    // Füge hier die Logik für Tier D ein, wenn nötig
                }
            }
        }
    }


    public void placeRandomCubes(Location location, int numCubes, int minSize, int maxSize, String tier) {
        World world = location.getWorld();

        for (int i = 0; i < numCubes; i++) {
            int size = random.nextInt(maxSize - minSize + 1) + minSize;

            int xOffset = random.nextInt(40) - 20;
            int yOffset = random.nextInt(10) - 5;
            int zOffset = random.nextInt(40) - 20;

            Location cubeLocation = location.clone().add(xOffset, yOffset, zOffset);


            if (tier.equalsIgnoreCase("s")) {
                Material material = getSMaterial();
                buildHollowCube(cubeLocation, size, material, tier);
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> spawnMobs(calculateCenter(cubeLocation, size), size, tier), 20L);
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> spawnMobs(calculateCenter(cubeLocation, size), size, tier), 60L);
            } else if (tier.equalsIgnoreCase("a")) {


            } else if (tier.equalsIgnoreCase("b")) {


            } else if (tier.equalsIgnoreCase("c")) {
                Material material = getCMaterial();
                buildHollowCube(cubeLocation, size, material, tier);

            } else if (tier.equalsIgnoreCase("d")) {

            }

        }
    }

    private Material getCMaterial() {
        return CDungeonBlocks[random.nextInt(CDungeonBlocks.length)];
    }

    private Material getSMaterial() {
        return SDungeonBlocks[random.nextInt(SDungeonBlocks.length)];
    }

    public Location calculateCenter(Location corner, int size) {
        double x = corner.getX() + size / 2.0;
        double y = corner.getY() + size / 2.0;
        double z = corner.getZ() + size / 2.0;
        return new Location(corner.getWorld(), x, y, z);
    }

    public void removeCubes() {
        for (Block block : cubeBlocks) {
            block.setType(Material.AIR);
            BlockMetaDatas.removeMetaData(block.getState(), "noBreakButDrop");
        }
        cubeBlocks.clear();
    }

    private void buildHollowCube(Location location, int size, Material material, String tier) {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < size; z++) {
                    if (x == 0 || y == 0 || z == 0 || x == size - 1 || y == size - 1 || z == size - 1) {
                        Location blockLocation = location.clone().add(x, y, z);
                        cubeBlocks.add(blockLocation.getBlock());
                        for (Block block : cubeBlocks) {
                            BlockMetaDatas.addMetaData(block.getState(), "noBreakButDrop");
                        }
                        if (tier.equalsIgnoreCase("s")) {
                            blockLocation.getBlock().setType(getSMaterial());

                        } else if (tier.equalsIgnoreCase("a")) {
                            blockLocation.getBlock().setType(getSMaterial());

                        } else if (tier.equalsIgnoreCase("b")) {
                            blockLocation.getBlock().setType(getSMaterial());

                        } else if (tier.equalsIgnoreCase("c")) {
                            blockLocation.getBlock().setType(getCMaterial());

                        } else if (tier.equalsIgnoreCase("d")) {
                            blockLocation.getBlock().setType(getSMaterial());

                        }
                    }
                }
            }
        }
    }

    private void spawnMobs(Location location, int size, String tier) {
        World world = location.getWorld();

        int numMobs = size * 2;
        int halfSize = size / 2;

        for (int i = 0; i < numMobs; i++) {
            int xOffset = random.nextInt(size) - halfSize;
            int yOffset = random.nextInt(size / 2); // Spawn-Höhe begrenzen
            int zOffset = random.nextInt(size) - halfSize;

            Location mobLocation = location.clone().add(xOffset, yOffset, zOffset);

            if (isInsideRoom(mobLocation, location, size)) {
                if (mobLocation.getBlock().getType().equals(Material.AIR)) {
                    if (tier.equalsIgnoreCase("s")) {
                        EntityType entityType = getSDungeonEntities();
                        LivingEntity mob = (LivingEntity) world.spawnEntity(mobLocation, entityType);

                        mob.addScoreboardTag("DungeonMobS");
                        mob.setSilent(true);
                        double health = random.nextDouble() * 1000 + 500;
                        mob.setMaxHealth(health);
                        mob.setHealth(health);
                        mob.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999 * 20, 1));
                        mob.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999 * 20, 1));
                        Random random = new Random();
                        Material[] armorMaterials = {Material.NETHERITE_HELMET, Material.NETHERITE_CHESTPLATE, Material.NETHERITE_LEGGINGS, Material.NETHERITE_BOOTS};
                        if (random.nextBoolean()) {
                            Material armorMaterial = armorMaterials[random.nextInt(armorMaterials.length)];

                            ItemStack armorPiece = new ItemStack(armorMaterial);
                            armorPiece.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);

                            switch (random.nextInt(4)) {
                                case 0:
                                    Objects.requireNonNull(mob.getEquipment()).setHelmet(armorPiece);
                                    break;
                                case 1:
                                    Objects.requireNonNull(mob.getEquipment()).setChestplate(armorPiece);
                                    break;
                                case 2:
                                    Objects.requireNonNull(mob.getEquipment()).setLeggings(armorPiece);
                                    break;
                                case 3:
                                    Objects.requireNonNull(mob.getEquipment()).setBoots(armorPiece);
                                    break;
                            }
                        }

                    }
                } else if (tier.equalsIgnoreCase("a")) {


                } else if (tier.equalsIgnoreCase("b")) {

                } else if (tier.equalsIgnoreCase("c")) {

                } else if (tier.equalsIgnoreCase("d")) {

                }
            }
        }
    }


    public boolean isInsideRoom(Location location, Location roomLocation, int size) {
        int halfSize = size / 2;

        double minX = roomLocation.getX() - halfSize;
        double maxX = roomLocation.getX() + halfSize;
        double minY = roomLocation.getY();
        double maxY = roomLocation.getY() + size;
        double minZ = roomLocation.getZ() - halfSize;
        double maxZ = roomLocation.getZ() + halfSize;

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
    }




}
