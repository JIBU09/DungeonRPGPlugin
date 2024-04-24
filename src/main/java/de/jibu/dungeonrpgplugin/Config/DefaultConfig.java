package de.jibu.dungeonrpgplugin.Config;

import de.jibu.dungeonrpgplugin.JUtils.FileWriter;

public class DefaultConfig {

    private final FileWriter fileWriter;

    public DefaultConfig() {
        this.fileWriter = new FileWriter("plugins//DungeonRPGPlugin//", "Settings.yml");
        this.fileWriter.save();
    }

    public boolean arePrivatePortalsDisallowed() {
        return fileWriter.getBoolean("PrivatePortals");
    }

    public void disallowPrivatePortals(boolean value) {
        fileWriter.setValue("PrivatePortals", value);
        fileWriter.save();
    }

    public int getParticleMultiplier() {
        return fileWriter.getInt("ParticleMultiplier");
    }

    public void setParticleMultiplier(int value) {
        fileWriter.setValue("ParticleMultiplier", value);
        fileWriter.save();
    }

}
