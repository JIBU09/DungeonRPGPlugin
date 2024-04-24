package de.jibu.dungeonrpgplugin.CustomItems;

import de.jibu.dungeonrpgplugin.Config.InventoryConfig;
import de.jibu.dungeonrpgplugin.Config.ItemConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddItemCommand implements CommandExecutor {

    ItemManager itemManager = new ItemManager();
    ItemConfig itemConfig = new ItemConfig();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                itemManager.createItem(player);
            }
            if (args.length == 1) {
                Player t = Bukkit.getPlayer(args[0]);
                if (t != null) {
                    InventoryConfig inventoryConfig = new InventoryConfig(t);
                    inventoryConfig.addWeaponToPlayersInventory(player.getItemInHand());
                    player.sendMessage("§aAdded item to players inventory!");
                } else {
                    player.sendMessage("§cThis player doesn't exist!");
                }

            } else {
                player.sendMessage("§cUse /additem");
            }
        }
        return false;
    }
}
