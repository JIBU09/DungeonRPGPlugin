package de.jibu.dungeonrpgplugin.Commands;

import de.jibu.dungeonrpgplugin.Generator.RoomSpawner;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GenerateRoomsCommand implements CommandExecutor {

    private final RoomSpawner roomSpawner;

    public GenerateRoomsCommand(RoomSpawner roomSpawner) {
        this.roomSpawner = roomSpawner;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            Location startLocation = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());

            if (args.length <= 2) {
                String tier = String.valueOf(args[0]);

                player.sendMessage("Räume generiert!");
                roomSpawner.generateRooms(startLocation, tier);
            } else {
                player.sendMessage("§cUse: /generaterooms <Rows> <Columns> <RoomSize> <Tier>");
            }
            return true;
        }

        return false;
    }

}

