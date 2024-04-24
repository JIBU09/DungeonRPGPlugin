package de.jibu.dungeonrpgplugin.CustomItems.Gems;

import de.jibu.dungeonrpgplugin.JUtils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class GemItems {

    public static ItemStack waterGem() {
        ItemStack itemStack = new ItemBuilder(Material.HEART_OF_THE_SEA).setName("§9§lWater Gem")
                .addLoreLine("")
                .addLoreLine("§6Ability: Gemstone of the sea §e§lSNEAK RIGHT CLICK IN OFFHAND")
                .addLoreLine("§7This ability floods the entire room and")
                .addLoreLine("§7while underwater you gain various buffs.")
                .addLoreLine("")
                .addLoreLine("§8Once held by a mythical creature. Which was")
                .addLoreLine("§8destroyed in a mysterious night. His core was")
                .addLoreLine("§8shattered an picked up by his subordinates.")
                .addLoreLine("§8But not so long ago a traitor had shown himself!")
                .addLoreLine("§8He stole the cores of a few subordinates and")
                .addLoreLine("§8smithed this rare gem out of the cores!")
                .addLoreLine("§8He returned to kill his former leader but was")
                .addLoreLine("§8captured and brought to another location.")
                .addLoreLine("§8With that the leader now has a portion of")
                .addLoreLine("§8his true strength back.")
                .addLoreLine("")
                .addLoreLine("§8Cooldown: §a45s")
                .addLoreLine("")
                .addLoreLine("§9§lGEM")
                .setUnbreakable()
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .addItemFlag(ItemFlag.HIDE_UNBREAKABLE).getItemStack();
        return itemStack;
    }


    public static ItemStack dimensionSlasher() {
        ItemStack itemStack = new ItemBuilder(Material.DIAMOND_SWORD).setName("§9Dimension Slasher")
                .addLoreLine("")
                .addLoreLine("§6Ability: Dimensional Slash §e§lAUTOMATIC")
                .addLoreLine("§7Slash though all blocks in front of you")
                .addLoreLine("§7and deal the damage from the item")
                .addLoreLine("§7in your main hand.")
                .addLoreLine("§7Uses itself automatically.")
                .addLoreLine("")
                .addLoreLine("§8This weapon was once used")
                .addLoreLine("§8to kill the gods!")
                .addLoreLine("")
                .addLoreLine("§8Cooldown: §a15s")
                .addLoreLine("")
                .addLoreLine("§9§lGEM")
                .setUnbreakable()
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .addItemFlag(ItemFlag.HIDE_UNBREAKABLE).getItemStack();
        return itemStack;
    }


}
