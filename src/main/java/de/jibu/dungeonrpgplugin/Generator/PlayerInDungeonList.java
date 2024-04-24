package de.jibu.dungeonrpgplugin.Generator;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerInDungeonList {

    private static final List<List<Player>> playerLists = new ArrayList<>();

    public static void addPlayersToList(int listIndex, List<Player> players) {
        playerLists.add(listIndex, new ArrayList<>(players));
    }

    public static List<Player> getPlayersList(int listIndex) {
        if (listIndex >= 0 && listIndex < playerLists.size()) {
            return playerLists.get(listIndex);
        }
        return null;
    }

}
