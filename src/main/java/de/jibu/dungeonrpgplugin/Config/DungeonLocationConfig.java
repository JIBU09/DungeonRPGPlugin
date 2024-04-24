package de.jibu.dungeonrpgplugin.Config;

import de.jibu.dungeonrpgplugin.Config.AmountConfig;
import de.jibu.dungeonrpgplugin.JUtils.FileWriter;
import org.bukkit.*;

import java.util.Set;

import static org.bukkit.Bukkit.getLogger;

public class DungeonLocationConfig {

    private final FileWriter fileWriter;

    AmountConfig amountConfig = new AmountConfig();

    public DungeonLocationConfig() {
        this.fileWriter = new FileWriter("plugins//DungeonRPGPlugin//DungeonPortals//", "PortalLocations.yml");
        this.fileWriter.save();
    }

    public void setPortalLocation(String key, Location location) {
        String locationString = location.getWorld().getName() + ";" +
                location.getX() + ";" +
                location.getY() + ";" +
                location.getZ() + ";" +
                location.getYaw() + ";" +
                location.getPitch();

        fileWriter.setValue("Location." + key, locationString);
        fileWriter.save();
    }


    public void setPortalTier(String key, String tier) {
        fileWriter.setValue("Tier." + key, tier);
        fileWriter.save();
    }



    public void loadPortalLocationsFromConfig() {
        Set<String> keys = getKeys();

        for (String key : keys) {
            Location location = getPortalLocation(key);

            if (location != null) {
                getLogger().info("Loaded portal location: " + location.toString());
            } else {
                getLogger().warning("Invalid portal location for key: " + key);
            }
        }
    }

    public String getPortalTier(String key) {
        return fileWriter.getString("Tier." + key);
    }

    public Location getPortalLocation(String key) {
        String locationString = fileWriter.getString("Location." + key);
        fileWriter.save();
        if (locationString != null) {
            String[] parts = locationString.split(";");
            World world = Bukkit.getWorld(parts[0]);
            double x = Double.parseDouble(parts[1]);
            double y = Double.parseDouble(parts[2]);
            double z = Double.parseDouble(parts[3]);
            float yaw = Float.parseFloat(parts[4]);
            float pitch = Float.parseFloat(parts[5]);

            return new Location(world, x, y, z, yaw, pitch);
        }

        return null;
    }


    public String getPortalNameByLocation(Location portalLocation) {
        for (int i = 0; i < amountConfig.getPortalAmount() + 1; i++) {
            Location storedLocation = getPortalLocation("p" + i);
            if (portalLocation.equals(storedLocation)) {
                return "p" + i;
            }
        }
        return null;
    }

    public void showPortalLocations() {
        Set<String> keys = fileWriter.getKeys(false);

        for (String key : keys) {
            Location location = getPortalLocation(key);

            if (location != null) {
                Bukkit.broadcastMessage(ChatColor.GREEN + "Portal Location for " + key + ": " +
                        "X=" + location.getX() + ", Y=" + location.getY() + ", Z=" + location.getZ());
            } else {
                Bukkit.broadcastMessage(ChatColor.RED + "Invalid portal location for " + key);
            }
        }
    }

    public Set<String> getKeys() {
        return fileWriter.getKeys(false);
    }


    public void setPublicPortal(String key, boolean statement) {
        fileWriter.setValue("Public." + key, statement);
        fileWriter.save();
    }

    public boolean isPublic(String key) {
        return fileWriter.getBoolean("Public." + key);
    }
}
