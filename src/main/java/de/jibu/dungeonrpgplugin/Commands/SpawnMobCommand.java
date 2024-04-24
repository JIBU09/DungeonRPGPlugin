package de.jibu.dungeonrpgplugin.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class SpawnMobCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 3) {
                Player t = Bukkit.getPlayer(args[0]);
                if (t != null) {
                    String entityString = args[1];
                    String countString = args[2];

                    EntityType type = EntityType.valueOf(entityString.toUpperCase());
                    if (type == null) return false;
                    if (!type.isSpawnable()) return false;

                    int count;

                    try {
                        count = Integer.parseInt(countString);
                    } catch (NumberFormatException e) {
                        return false;
                    }
                    for (int i = 0; i < count; i++) {
                        Objects.requireNonNull(t.getLocation().getWorld()).spawnEntity(t.getLocation(), type);
                    }
                    sender.sendMessage("§aDu hast erfolgreich die Mobs gespawnt!");
                } else sender.sendMessage("§cDer Spieler ist nicht online!");
            } else sender.sendMessage("§cNutze: /spawnmob <Zielspieler> <Mob> <Anzahl>");
        } else sender.sendMessage("§cDu musst ein Spieler sein!");
        return false;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if (args.length == 0) return list;
        if (args.length == 2) {
            for (EntityType value : EntityType.values()) {
                if (value.isSpawnable()) {
                    list.add(value.toString().toLowerCase());
                }
            }
        } else if (args.length == 3) {
            for (int i = 0; i < 50; i++) {
                list.add(String.valueOf(i));
            }
        } else if (args.length == 1) {
            Bukkit.getOnlinePlayers().forEach(Player::getDisplayName);
        }

        ArrayList<String> completerList = new ArrayList<>();
        String currentarg = args[args.length - 1];
        for (String s : list) {
            String s1 = s.toLowerCase();
            if (s1.startsWith(currentarg)) {
                completerList.add(s);
            }
        }
        return completerList;
    }
}

