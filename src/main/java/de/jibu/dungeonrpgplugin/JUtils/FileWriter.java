package de.jibu.dungeonrpgplugin.JUtils;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class FileWriter {

    private File f;
    private YamlConfiguration c;

    public FileWriter(String FilePath, String FileName) {
        this.f = new File(FilePath, FileName);
        this.c = YamlConfiguration.loadConfiguration(this.f);
    }

    public FileWriter setValue(String ValuePath, Object Value) {
        c.set(ValuePath, Value);
        return this;
    }

    public void loadCustomConfig() {
        this.c = YamlConfiguration.loadConfiguration(this.f);
    }

    public boolean exist() {
        return f.exists();
    }

    public Object getObject(String ValuePath) {
        return c.get(ValuePath);
    }

    public int getInt(String ValuePath) {
        return c.getInt(ValuePath);
    }

    public String getString(String ValuePath) {
        return c.getString(ValuePath);
    }

    public String getStringValue(String ValuePath, String Value) {
        return c.getString(ValuePath, Value);
    }

    public List<?> getList(String ValuePath) {
        return c.getList(ValuePath);
    }

    public long getLong(String ValuePath) {
        return c.getLong(ValuePath);
    }

    public boolean getBoolean(String ValuePath) {
        return c.getBoolean(ValuePath);
    }

    public Location getLocation(String ValuePath) {
        return c.getLocation(ValuePath);
    }

    public boolean isSet(String ValuePath) {
        return c.isSet(ValuePath);
    }


    public FileWriter setItemStack(String path, ItemStack itemStack) {
        String name = ColorCodeConverter.convertCodingToChatting(itemStack.getItemMeta().getDisplayName());
        c.set(path, itemStack);
        return this;
    }


    public ItemStack getItemStack(String path) {
        return c.getItemStack(path);
    }

    public List<String> getStringList(String ValuePath) {
        return c.getStringList(ValuePath);
    }

    public Set<String> getKeys(boolean deep) {
        return c.getKeys(deep);
    }

    public ConfigurationSection getConfigurationSection(String Section) {
        return c.getConfigurationSection(Section);
    }

    public FileWriter save() {
        try {
            this.c.save(this.f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

}


