package de.jibu.dungeonrpgplugin.Commands;

import de.jibu.dungeonrpgplugin.Generator.RandomCubesGenerator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlaceCubesCommand implements CommandExecutor {

    private final RandomCubesGenerator cubesGenerator;

    public PlaceCubesCommand(RandomCubesGenerator cubesGenerator) {
        this.cubesGenerator = cubesGenerator;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length < 5) {
                player.sendMessage("§aBenutzung: /placecubes <gridSize> <cubes pro row> <Mindestgröße> <Maximalgröße> <Tier>");
                return true;
            }
            int gridSize = Integer.parseInt(args[0]);
            int cubesPerRow = Integer.parseInt(args[1]);
            int numCubes = Integer.parseInt(args[1]);
            int minSize = Integer.parseInt(args[2]);
            int maxSize = Integer.parseInt(args[3]);
            String tier = String.valueOf(args[4]);

            //cubesGenerator.placeRandomCubes(player.getLocation(), numCubes, minSize, maxSize, tier);
            cubesGenerator.placeCubesInGrid(player.getLocation(), 80, 5, 10, 20, "c");

            return true;
        }
        return false;
    }



}


