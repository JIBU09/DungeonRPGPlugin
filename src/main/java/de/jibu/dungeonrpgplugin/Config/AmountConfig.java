package de.jibu.dungeonrpgplugin.Config;

import de.jibu.dungeonrpgplugin.JUtils.FileWriter;

public class AmountConfig {

    private final FileWriter fileWriter;

    public AmountConfig() {
        this.fileWriter = new FileWriter("plugins//DungeonRPGPlugin//DungeonPortals", "PortalAmount.yml");
        this.fileWriter.save();
    }

    public int getPortalAmount() {
        return fileWriter.getInt("Amount");
    }

    public void addPortalAmount() {
        fileWriter.setValue("Amount", getPortalAmount() + 1);
        fileWriter.save();
    }

}
