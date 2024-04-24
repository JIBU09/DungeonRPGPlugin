package de.jibu.dungeonrpgplugin.CustomItems;

import de.jibu.dungeonrpgplugin.Config.ItemConfig;
import de.jibu.dungeonrpgplugin.JUtils.ColorCodeConverter;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RemoveItemCommand implements CommandExecutor, TabCompleter {
    ItemConfig itemConfig = new ItemConfig();
    ItemManager itemManager = new ItemManager();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length >= 1) {
                String itemName = String.join(" ", args).toLowerCase();
                if (itemConfig.getCustomItem(itemName) != null) {
                    itemManager.removeItem(itemName);
                    player.sendMessage("§aRemoved " + ColorCodeConverter.convertChattingToCoding(itemName));
                } else {
                    player.sendMessage("§cItem could not be found!");
                }
            } else {
                player.sendMessage("§cUse: /removeitem <Item>");
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