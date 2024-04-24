package de.jibu.dungeonrpgplugin.Commands;

import de.jibu.dungeonrpgplugin.Config.DungeonLocationConfig;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PortalTPCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                try {
                    String portalId = String.valueOf(args[0]);
                    DungeonLocationConfig dungeonLocationConfig = new DungeonLocationConfig();
                    Location location = dungeonLocationConfig.getPortalLocation(args[0]);
                    if (location != null) {
                        player.teleport(location.add(0.5,0.5,0.5));
                        return true;
                    } else {
                        player.sendMessage(ChatColor.RED + "Invalid portal location for Portal ID: " + portalId);
                    }
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Invalid Portal ID. Please use a valid integer.");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /teleportdungeongateway <Portal ID>");
            }
        }
        return false;
    }
}
