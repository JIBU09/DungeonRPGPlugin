package de.jibu.dungeonrpgplugin.CustomItems.Items;

import de.jibu.dungeonrpgplugin.JUtils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class TierSItems {

    public static ItemStack astylisDominate() {
        ItemStack itemStack = new ItemBuilder(Material.EMERALD).setName("§5Astylis Dominate")
                .addLoreLine("")
                .addLoreLine("§6Ability: Astylis Conqueror §e§lRIGHT CLICK")
                .addLoreLine("§7Remove your §c40%§7 of your enemy's")
                .addLoreLine("§7entire health. Also make them")
                .addLoreLine("§7unconscious for §e15 seconds§7!")
                .addLoreLine("")
                .addLoreLine("§8This ability takes the core")
                .addLoreLine("§8energy out the universe out")
                .addLoreLine("§8of your enemy.")
                .addLoreLine("")
                .addLoreLine("§8Cooldown: §a60s")
                .addLoreLine("")
                .addLoreLine("§5§lS TIER CORE")
                .setUnbreakable()
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .addItemFlag(ItemFlag.HIDE_UNBREAKABLE).getItemStack();
        return itemStack;
    }


}
