package de.jibu.dungeonrpgplugin.Config;

import de.jibu.dungeonrpgplugin.JUtils.ColorCodeConverter;
import de.jibu.dungeonrpgplugin.JUtils.FileWriter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemConfig {

    private final FileWriter fileWriter;

    public ItemConfig() {
        this.fileWriter = new FileWriter("plugins//DungeonRPGPlugin//Inventory//", "CustomItems.yml");
        this.fileWriter.save();
    }

    public void addCustomItem(Player player) {
        ItemStack item = player.getItemInHand();
        if (item.hasItemMeta()) {
            String displayName = item.getItemMeta().getDisplayName().toLowerCase();
            String convertedDisplayName = ColorCodeConverter.convertCodingToChatting(displayName);
            fileWriter.setItemStack("CustomItems." + convertedDisplayName, item);
            addItemsAmount();
            fileWriter.save();
            player.sendMessage(displayName + " §asuccessfully saved!");
        } else {
            player.sendMessage("§cItem needs to have ItemMeta!");
        }
    }


    public void addCustomItemStack(ItemStack item) {
        if (item.hasItemMeta()) {
            String displayName = item.getItemMeta().getDisplayName().toLowerCase();
            String convertedDisplayName = ColorCodeConverter.convertCodingToChatting(displayName);
            fileWriter.setItemStack("CustomItems." + convertedDisplayName, item);
            addItemsAmount();
            fileWriter.save();
        }
    }

    public void removeCustomItem(String itemName) {
        fileWriter.setValue("CustomItems." + itemName, null);
        removeItemsAmount();
        fileWriter.save();
    }

    public ItemStack getCustomItem(String itemName) {
        return fileWriter.getItemStack("CustomItems." + itemName);
    }

    public ConfigurationSection getItems() {
        return fileWriter.getConfigurationSection("CustomItems.");
    }

    public int getItemsAmount() {
        return fileWriter.getInt("Amount");
    }

    public void addItemsAmount() {
        fileWriter.setValue("Amount", getItemsAmount() + 1);
        fileWriter.save();
    }

    public void removeItemsAmount() {
        fileWriter.setValue("Amount", getItemsAmount() - 1);
        fileWriter.save();
    }

}
