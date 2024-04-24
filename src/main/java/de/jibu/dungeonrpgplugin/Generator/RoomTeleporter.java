package de.jibu.dungeonrpgplugin.Generator;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class RoomTeleporter {

    private final Map<Location, String> roomIds = new HashMap<>();

    public void teleportToRoom(Player player, String roomId) {
        for (Map.Entry<Location, String> entry : roomIds.entrySet()) {
            if (entry.getValue().equals(roomId)) {
                player.teleport(entry.getKey());
                player.sendMessage("Du wurdest zum Raum " + roomId + " teleportiert!");
                return;
            }
        }

        player.sendMessage("Kein Raum mit der ID " + roomId + " gefunden.");
    }
}

