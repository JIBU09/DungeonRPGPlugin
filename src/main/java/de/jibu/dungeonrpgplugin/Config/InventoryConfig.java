package de.jibu.dungeonrpgplugin.Config;

import de.jibu.dungeonrpgplugin.JUtils.FileWriter;
import de.jibu.dungeonrpgplugin.Main;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class InventoryConfig {

    private final FileWriter fileWriter;
    private final String saveName = "defaultSave";
    UUID uuid;


    public InventoryConfig(Player player) {
        uuid = player.getUniqueId();
        this.fileWriter = new FileWriter("plugins//DungeonRPGPlugin//Inventory//DungeonInventory", uuid + ".yml");
        this.fileWriter.save();
    }

    public void saveInventory(Player player) {
        List<ItemStack> list = new ArrayList<>();
        for (ItemStack itemStack : player.getInventory()) {
            list.add(itemStack);
        }
        fileWriter.setValue("defaultInventory", list);
        fileWriter.save();

    }

    public void restoreInventory(Player player) {
        if (fileWriter.isSet("defaultInventory")) {
            int i = 0;
            for (Object object : Objects.requireNonNull(fileWriter.getList("defaultInventory"))) {
                ItemStack item = (ItemStack) object;
                if (item == null) item = new ItemStack(Material.AIR);

                player.getInventory().setItem(i, item);

                i++;
            }
        }

    }

    public void addItemToPlayersInventory(String itemCategory, ItemStack item) {
        fileWriter.setItemStack("PCIInv." + itemCategory, item);
        fileWriter.save();
    }

    public void addArmorToPlayersInventory(ItemStack item) {
        fileWriter.setItemStack("PCIInv.Armor", item);
        fileWriter.save();
    }

    public void addGemToPlayersInventory(ItemStack item) {
        fileWriter.setItemStack("PCIInv.Gems", item);
        fileWriter.save();
    }

    public void addWeaponToPlayersInventory(ItemStack item) {
        fileWriter.setItemStack("PCIInv.Weapons." + item.getItemMeta().getDisplayName(), item);
        fileWriter.save();
    }

    public void removeCustomItem(String itemCategory, ItemStack item) {
        fileWriter.setValue("PCIInv." + itemCategory + item, null);
        fileWriter.save();
    }


    public ItemStack getCustomItem(String itemCategory, String itemNamen) {
        return fileWriter.getItemStack("PCIInv." + itemCategory + itemNamen);
    }

    public ItemStack getCustomArmor(String itemStack) {
        return fileWriter.getItemStack("PCIInv.Armor" + itemStack);
    }

    public ItemStack getCustomWeapons(String itemName) {
        return fileWriter.getItemStack("PCIInv.Weapons." + itemName);
    }

    public ItemStack getCustomGems(String itemStack) {
        return fileWriter.getItemStack("PCIInv.Gems" + itemStack);
    }

    public ConfigurationSection getArmors() {
        return fileWriter.getConfigurationSection("PCIInv.Armor.");
    }

    public ConfigurationSection getGems() {
        return fileWriter.getConfigurationSection("PCIInv.Gems.");
    }

    public ConfigurationSection getWeapons() {
        return fileWriter.getConfigurationSection("PCIInv.Weapons.");
    }

    public ConfigurationSection getItemByCategory(String itemCategory) {
        return fileWriter.getConfigurationSection("PCIInv." + itemCategory + ".");
    }



    public void saveCustomInventory(Player player, Inventory inventory) {
        UUID uuid = player.getUniqueId();

        for (int i = 0; i < 9; i++) {
            ItemStack item = inventory.getItem(i);
            fileWriter.setItemStack(".hotbar." + i, item);
            fileWriter.save();
        }

        for (int i = 9; i < 18; i++) {
            ItemStack item = inventory.getItem(i);
            fileWriter.setItemStack(".inventory." + i, item);
            fileWriter.save();
        }

        for (int i = 18; i < 27; i++) {
            ItemStack item = inventory.getItem(i);
            fileWriter.setItemStack(".inventory." + i, item);
            fileWriter.save();
        }

        for (int i = 27; i < 36; i++) {
            ItemStack item = inventory.getItem(i);
            fileWriter.setItemStack(".inventory." + i, item);
            fileWriter.save();
        }

        for (int i = 45; i < 49; i++) {
            ItemStack item = inventory.getItem(i);
            fileWriter.setItemStack(".armor." + i, item);
            fileWriter.save();
        }

        ItemStack item = inventory.getItem(49);
        fileWriter.setItemStack(".offhand." + 49, item);
        fileWriter.save();


    }

    public void loadCustomInventory(Player player) {
        UUID uuid = player.getUniqueId();
        player.getInventory().clear();
        for (int i = 0; i < 9; i++) {
            ItemStack item = fileWriter.getItemStack(".hotbar." + i);
            player.getInventory().setItem(i, item);
        }

        for (int i = 9; i < 18; i++) {
            ItemStack item = fileWriter.getItemStack(".inventory." + i);
            player.getInventory().setItem(i + 18, item);
        }


        for (int i = 18; i < 27; i++) {
            ItemStack item = fileWriter.getItemStack(".inventory." + i);
            player.getInventory().setItem(i, item);
        }

        for (int i = 27; i < 36; i++) {
            ItemStack item = fileWriter.getItemStack(".inventory." + i);
            player.getInventory().setItem(i - 18, item);
        }


        for (int i = 45; i < 49; i++) {
            ItemStack item = fileWriter.getItemStack(".armor." + i);
            player.getInventory().setItem(i - 9, item);
        }


        ItemStack item = fileWriter.getItemStack(".offhand." + 49);
        player.getInventory().setItem(40, item);

        player.getInventory().remove(Material.GRAY_STAINED_GLASS_PANE);
        player.getInventory().remove(Material.RED_STAINED_GLASS_PANE);

        player.getInventory().remove(Material.CYAN_STAINED_GLASS_PANE);
        player.getInventory().remove(Material.LIME_STAINED_GLASS_PANE);

        ItemStack[] armorContents = player.getInventory().getArmorContents();
        for (int i = 0; i < armorContents.length; i++) {
            if (armorContents[i] != null && armorContents[i].getType().equals(Material.CYAN_STAINED_GLASS_PANE)) {
                armorContents[i] = new ItemStack(Material.AIR);
            }
        }
        player.getInventory().setArmorContents(armorContents);

        if (player.getInventory().getItemInOffHand() != null && player.getInventory().getItemInOffHand().getType() == Material.LIME_STAINED_GLASS_PANE) {
            ItemStack air = new ItemStack(Material.AIR);
            player.getInventory().setItemInOffHand(air);
        }


    }

}
