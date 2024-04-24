package de.jibu.dungeonrpgplugin.Commands;

import de.jibu.dungeonrpgplugin.Generator.RoomTeleporter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RoomTpCommand implements CommandExecutor {

    private final RoomTeleporter roomTeleporter;

    public RoomTpCommand(RoomTeleporter roomTeleporter) {
        this.roomTeleporter = roomTeleporter;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            // Überprüfe, ob genügend Argumente übergeben wurden
            if (args.length < 1) {
                player.sendMessage("Benutzung: /roomtp <Raum-ID>");
                return true;
            }

            String roomId = args[0];
            roomTeleporter.teleportToRoom(player, roomId);

            return true;
        }
        return false;
    }
}

