package de.jibu.dungeonrpgplugin.Commands;

import de.jibu.dungeonrpgplugin.JUtils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class GiveDebugItemsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.getInventory().clear();
            player.getInventory().addItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("§8").getItemStack());
            player.getInventory().addItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName("§7").getItemStack());
            player.getInventory().addItem(new ItemBuilder(Material.SPECTRAL_ARROW).setName("§7Last Page").getItemStack());
            player.getInventory().addItem(new ItemBuilder(Material.BARRIER).setName("§cClose").addLoreLine("§c").addLoreLine("§7Click to close").getItemStack());
            player.getInventory().addItem(new ItemBuilder(Material.ARROW).setName("§7Next page").getItemStack());
            player.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1, 1);
            player.sendMessage("§aGave items...");
        }
        return false;
    }
}
