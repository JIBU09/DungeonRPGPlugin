package de.jibu.dungeonrpgplugin.CustomItems;

import de.jibu.dungeonrpgplugin.Config.InventoryConfig;
import de.jibu.dungeonrpgplugin.Config.ItemConfig;
import de.jibu.dungeonrpgplugin.CustomItems.Items.TierDItems;
import de.jibu.dungeonrpgplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemManager {

    ItemConfig itemConfig = new ItemConfig();

    public void createItem(Player player) {
        itemConfig.addCustomItem(player);
        Main.getInstance().getCommand("items").setExecutor(new ItemsCommand());
        Main.getInstance().getCommand("items").setTabCompleter(new ItemsCommand());
        Main.getInstance().getCommand("removeitem").setExecutor(new RemoveItemCommand());

    }

    public void removeItem(String name) {
        itemConfig.removeCustomItem(name);
        Main.getInstance().getCommand("items").setExecutor(new ItemsCommand());
        Main.getInstance().getCommand("items").setTabCompleter(new ItemsCommand());
        Main.getInstance().getCommand("removeitem").setExecutor(new RemoveItemCommand());
    }

    public void openItemsInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 54, "§6§lItems");
        if (itemConfig.getItems() != null) {
            List<String> completions = new ArrayList<>();
            completions.addAll(itemConfig.getItems().getKeys(false));
            for (int i = 0; i < completions.size(); i++) {
                inventory.addItem(itemConfig.getCustomItem(completions.get(i)));
            }
        }
        player.openInventory(inventory);
        player.sendMessage("§aOpening...");
    }


    public void openCustomItemsInventoryFromPlayer(Player player, int slot) {
        slot += 1;
        InventoryConfig inventoryConfig = new InventoryConfig(player);
        Inventory inventory = Bukkit.createInventory(player, 54, "§6§lItem Selection §7| §8" + slot);
        if (inventoryConfig.getWeapons() != null) {
            List<String> completions = new ArrayList<>();
            completions.addAll(inventoryConfig.getWeapons().getKeys(false));
            for (int i = 0; i < completions.size(); i++) {
                inventory.addItem(inventoryConfig.getCustomWeapons(completions.get(i)));
            }
        } else {
            inventory.addItem(TierDItems.defaultDagger());
        }
        player.openInventory(inventory);
        player.sendMessage("§aOpening...");
    }

}
