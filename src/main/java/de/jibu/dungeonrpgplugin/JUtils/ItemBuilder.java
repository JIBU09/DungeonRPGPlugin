package de.jibu.dungeonrpgplugin.JUtils;


import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;

public class ItemBuilder {
    private ItemStack is;

    public ItemBuilder(Material m) {
        this(m, 1);
    }

    public ItemBuilder(ItemStack is) {
        this.is = is;
    }

    public ItemBuilder(Material m, int amount) {
        this.is = new ItemStack(m, amount);
    }

    public ItemBuilder clone() {
        return new ItemBuilder(this.is);
    }

    public ItemBuilder setDurability(short dur) {
        this.is.setDurability(dur);
        return this;
    }

    public ItemBuilder setName(String name) {
        ItemMeta im = this.is.getItemMeta();
        im.setDisplayName(name);
        this.is.setItemMeta(im);
        return this;
    }


    public ItemBuilder removeEnchantment(Enchantment enchant) {
        this.is.removeEnchantment(enchant);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchant, int level) {
        ItemMeta im = this.is.getItemMeta();
        im.addEnchant(enchant, level, true);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments) {
        this.is.addEnchantments(enchantments);
        return this;
    }

    public ItemBuilder setSkullOwner(Player owner) {
        SkullMeta im = (SkullMeta) this.is.getItemMeta();
        im.setOwningPlayer(owner);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setSkullOwner(String UUID, Boolean juckt) {
        SkullMeta im = (SkullMeta) this.is.getItemMeta();
        im.setOwningPlayer(Bukkit.getOfflinePlayer(java.util.UUID.fromString(UUID)));
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setSkullOwner(String url) {
        url = "http://textures.minecraft.net/texture/" + url;
        ItemMeta skullMeta = this.is.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), (String) null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;

        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
        } catch (SecurityException | NoSuchFieldException var8) {
            var8.printStackTrace();
        }

        profileField.setAccessible(true);

        try {
            profileField.set(skullMeta, profile);
        } catch (IllegalAccessException | IllegalArgumentException var7) {
            var7.printStackTrace();
        }

        this.is.setItemMeta(skullMeta);
        return this;
    }

    public ItemBuilder setSkullBase64(String code) {
        SkullMeta meta = (SkullMeta) this.is.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), "");
        profile.getProperties().put("textures", new Property("textures", code));
        Field profileField = null;

        try {
            profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (IllegalAccessException | NoSuchFieldException | SecurityException | IllegalArgumentException var6) {
            var6.printStackTrace();
        }

        this.is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setUnbreakable() {
        ItemMeta im = this.is.getItemMeta();
        im.setUnbreakable(true);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        ItemMeta im = this.is.getItemMeta();
        im.setLore(Arrays.asList(lore));
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta im = this.is.getItemMeta();
        im.setLore(lore);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder removeLoreLine(String line) {
        ItemMeta im = this.is.getItemMeta();
        List<String> lore = new ArrayList(im.getLore());
        if (!lore.contains(line)) {
            return this;
        } else {
            lore.remove(line);
            im.setLore(lore);
            this.is.setItemMeta(im);
            return this;
        }
    }

    public ItemBuilder removeLoreLine(int index) {
        ItemMeta im = this.is.getItemMeta();
        List<String> lore = new ArrayList(im.getLore());
        if (index >= 0 && index <= lore.size()) {
            lore.remove(index);
            im.setLore(lore);
            this.is.setItemMeta(im);
            return this;
        } else {
            return this;
        }
    }

    public ItemBuilder addLoreLine(String line) {
        ItemMeta im = this.is.getItemMeta();
        List<String> lore = new ArrayList();
        if (im.hasLore()) {
            lore = new ArrayList(im.getLore());
        }

        lore.add(line);
        im.setLore(lore);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addHealthValue(String value) {
        ItemMeta im = this.is.getItemMeta();
        List<String> lore = new ArrayList();
        if (im.hasLore()) {
            lore = new ArrayList(im.getLore());
        }

        lore.add("§7Health: §a" + value);
        im.setLore(lore);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addCritChanceValue(String value) {
        ItemMeta im = this.is.getItemMeta();
        List<String> lore = new ArrayList();
        if (im.hasLore()) {
            lore = new ArrayList(im.getLore());
        }

        lore.add("§7Crit Chance: §c" + value);
        im.setLore(lore);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addStrengthValue(String value) {
        ItemMeta im = this.is.getItemMeta();
        List<String> lore = new ArrayList();
        if (im.hasLore()) {
            lore = new ArrayList(im.getLore());
        }

        lore.add("§7Strength: §c" + value);
        im.setLore(lore);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addCritDamageValue(String value) {
        ItemMeta im = this.is.getItemMeta();
        List<String> lore = new ArrayList();
        if (im.hasLore()) {
            lore = new ArrayList(im.getLore());
        }

        lore.add("§7Crit Damage: §c" + value);
        im.setLore(lore);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addDefenseValue(String value) {
        ItemMeta im = this.is.getItemMeta();
        List<String> lore = new ArrayList();
        if (im.hasLore()) {
            lore = new ArrayList(im.getLore());
        }

        lore.add("§7Defense: §a" + value);
        im.setLore(lore);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addSpeedValue(String value) {
        ItemMeta im = this.is.getItemMeta();
        List<String> lore = new ArrayList();
        if (im.hasLore()) {
            lore = new ArrayList(im.getLore());
        }

        lore.add("§7Speed: §a" + value);
        im.setLore(lore);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addIntelligenceValue(String value) {
        ItemMeta im = this.is.getItemMeta();
        List<String> lore = new ArrayList();
        if (im.hasLore()) {
            lore = new ArrayList(im.getLore());
        }

        lore.add("§7Intelligence: §a" + value);
        im.setLore(lore);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addLoreLine(String line, int pos) {
        ItemMeta im = this.is.getItemMeta();
        List<String> lore = new ArrayList(im.getLore());
        lore.set(pos, line);
        im.setLore(lore);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setLeatherColor(Color color) {
        try {
            LeatherArmorMeta im = (LeatherArmorMeta) this.is.getItemMeta();
            im.setColor(color);
            this.is.setItemMeta(im);
        } catch (ClassCastException var3) {
        }

        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag flag) {
        ItemMeta im = this.is.getItemMeta();
        im.addItemFlags(new ItemFlag[]{flag});
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setCustomModelData(Integer modelID) {
        ItemMeta im = this.is.getItemMeta();
        im.setCustomModelData(modelID);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setCompassLocation(Location pos) {
        CompassMeta im = (CompassMeta) this.is.getItemMeta();
        im.setLodestoneTracked(true);
        im.setLodestone(pos);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setCompassLocation(World world, Integer x, Integer y, Integer z) {
        CompassMeta im = (CompassMeta) this.is.getItemMeta();
        Location loc = new Location(world, (double) x, (double) y, (double) z);
        im.setLodestoneTracked(true);
        im.setLodestone(loc);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addPotionEffect(PotionEffect effect) {
        PotionMeta im = (PotionMeta) this.is.getItemMeta();
        im.addCustomEffect(effect, true);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setPotionColor(Color color) {
        PotionMeta im = (PotionMeta) this.is.getItemMeta();
        im.setColor(color);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemStack getItemStack() {
        return this.is;
    }
}

