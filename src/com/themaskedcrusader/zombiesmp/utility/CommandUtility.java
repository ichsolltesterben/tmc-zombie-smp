package com.themaskedcrusader.zombiesmp.utility;

import com.themaskedcrusader.zombiesmp.ZombieSmpPlugin;
import com.themaskedcrusader.zombiesmp.schedule.CustomChestSchedule;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandUtility {
    ZombieSmpPlugin plugin;
    private static String opMessage = ChatColor.RED + "Only an op can use that command";

    public CommandUtility(ZombieSmpPlugin plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("unused")
    public static boolean fireCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (s.equalsIgnoreCase("tmcz")) {
            switch (strings[0]) {
                case "respawnchests" : return respawnChests(sender);
                case "despawnchests" : return despawnChests(sender);
//                case "spawn"         : return startGame(sender);

                default:
                    sender.sendMessage(ChatColor.RED + "Command not found... please try again");
            }
        }
        return false;
    }

    // TMCZ Commands!

    private static boolean respawnChests(CommandSender sender) {
        if (sender.isOp()) {
            CustomChestSchedule.reAddChestWithContents();
        } else {
            sender.sendMessage(opMessage);
        }
        return true;
    }

    private static boolean despawnChests(CommandSender sender) {
        if (sender.isOp()) {
            CustomChestSchedule.removeOpenedChests();
        } else {
            sender.sendMessage(opMessage);
        }
        return true;
    }

//    public static void startGame(CommandSender sender) {
//        Player player = (Player) sender;
//        player.setLevel(20);
//        player.getInventory().clear();
//        plugin.getConfig().getStringList()
//        player.getInventory().addItem(new ItemStack[]{new ItemStack(Material.WOOD_SWORD), new ItemStack()})
//
//    }

}
