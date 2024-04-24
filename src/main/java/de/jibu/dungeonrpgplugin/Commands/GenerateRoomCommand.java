package de.jibu.dungeonrpgplugin.Commands;

import de.jibu.dungeonrpgplugin.Generator.RoomSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class GenerateRoomCommand implements CommandExecutor {

    private final RoomSpawner roomSpawner;

    public GenerateRoomCommand(RoomSpawner roomSpawner) {
        this.roomSpawner = roomSpawner;
    }

    Random random = new Random();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            int maxroomx = 9 + roomSpawner.tierToInteger(args[0]);
            int maxroomz = 9 + roomSpawner.tierToInteger(args[0]);
            int maxroomy = 9 + roomSpawner.tierToInteger(args[0]);
            int minroomx = 6 + roomSpawner.tierToInteger(args[0]);
            int minroomy = 6 + roomSpawner.tierToInteger(args[0]);
            int minroomz = 6 + roomSpawner.tierToInteger(args[0]);
            int randomRoomSizex = random.nextInt(maxroomx - minroomx + 1) + minroomx;
            int randomRoomSizey = random.nextInt(maxroomz - minroomz + 1) + minroomy;
            int randomRoomSizez = random.nextInt(maxroomy - minroomy + 1) + minroomz;
            roomSpawner.generateRoom(player.getLocation(), randomRoomSizex,randomRoomSizez,randomRoomSizey, args[0], Boolean.parseBoolean(args[1]));
        }

        return false;
    }
}
