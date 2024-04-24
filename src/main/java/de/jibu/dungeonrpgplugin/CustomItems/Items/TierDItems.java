package de.jibu.dungeonrpgplugin.CustomItems.Items;

import de.jibu.dungeonrpgplugin.JUtils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class TierDItems {

    public static ItemStack defaultDagger() {
        ItemStack itemStack = new ItemBuilder(Material.WOODEN_SWORD).setName("§bDagger")
                .addLoreLine("")
                .addLoreLine("§8This simple dagger was created")
                .addLoreLine("§8from an old dungeon mob whose")
                .addLoreLine("§8core was broken.")
                .addLoreLine("")
                .addLoreLine("§b§lBROKEN D TIER CORE")
                .setUnbreakable()
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .addItemFlag(ItemFlag.HIDE_UNBREAKABLE).getItemStack();
        return itemStack;
    }

    public static ItemStack lightningFork() {
        ItemStack itemStack = new ItemBuilder(Material.TRIDENT).setName("§bThunderstriker")
                .addLoreLine("")
                .addLoreLine("§6Ability: Lightning Strike §e§lATTACK")
                .addLoreLine("§7Everytime you hit an enemy")
                .addLoreLine("§7with a fully charged attack a")
                .addLoreLine("§7a lightning strike on them!")
                .addLoreLine("§7Use with caution!")
                .addLoreLine("")
                .addLoreLine("§8This trident one stabbed")
                .addLoreLine("§8an old and weak drowned which")
                .addLoreLine("§8dropped his broken core.")
                .addLoreLine("")
                .addLoreLine("§b§lD TIER CORE")
                .setUnbreakable()
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .addItemFlag(ItemFlag.HIDE_UNBREAKABLE).getItemStack();
        return itemStack;
    }



}
