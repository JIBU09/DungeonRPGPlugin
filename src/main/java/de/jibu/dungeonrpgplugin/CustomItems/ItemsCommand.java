package de.jibu.dungeonrpgplugin.CustomItems;

import de.jibu.dungeonrpgplugin.Config.ItemConfig;
import de.jibu.dungeonrpgplugin.Main;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ItemsCommand implements CommandExecutor, TabCompleter {

    ItemManager itemManager = new ItemManager();
    ItemConfig itemConfig = new ItemConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length >= 1) {
                String itemName = String.join(" ", args).toLowerCase();
                if (itemConfig.getCustomItem(itemName) != null) {
                    player.getInventory().addItem(itemConfig.getCustomItem(itemName));
                    player.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1, 1);
                } else {
                    player.sendMessage("§cItem could not be found!");
                }
            } else {
                itemManager.openItemsInventory(player);
            }
        }
        return false;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            if (itemConfig.getItems() != null) {
                completions.addAll(itemConfig.getItems().getKeys(false));
            }
        }
        return completions;
    }
}
