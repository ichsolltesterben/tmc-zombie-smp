package com.themaskedcrusader.zombiesmp.utility;

import com.themaskedcrusader.zombiesmp.ZombieSmpPlugin;
import com.themaskedcrusader.zombiesmp.schedule.CustomChestSchedule;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandUtility {
    ZombieSmpPlugin plugin;
    private static String opMessage = ChatColor.RED + "Only an op can use that command";

    public CommandUtility(ZombieSmpPlugin plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("unused")
    public static boolean fireCommand(CommandSender sender, Command command, String s, String[] strings) {
        switch (command.getName().toLowerCase()) {
            case "respawnchests" : return respawnChests(sender);
            case "despawnchests" : return despawnChests(sender);

            default:
                sender.sendMessage(ChatColor.RED + "Command not found... please try again");
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

}
