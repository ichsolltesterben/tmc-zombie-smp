package com.themaskedcrusader.zombiesmp.schedule;

import com.themaskedcrusader.zombiesmp.ZombieSmpPlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlayerThirstSchedule {
    static ZombieSmpPlugin plugin;

    public PlayerThirstSchedule(ZombieSmpPlugin plugin) {
        this.plugin = plugin;

        if (plugin.getConfig().getBoolean("player.thirst.enabled")) {
            plugin.getLogger().info("Thirst System Activated!");
            plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
                public void run() {
                    PlayerThirstSchedule.calculateThirst();
                }
            }, 20L, (plugin.getConfig().getLong("player.thirst.ticks")));
        }
    }

    public static void calculateThirst() {
        Player[] players = plugin.getServer().getOnlinePlayers();
        for (Player player : players) {
            if (player.getLevel() == 0) {
                player.damage(1); // ouch, that smarts
                if (player.getHealth() == 1) {
                    player.sendMessage(ChatColor.RED + "Good night, cruel world");
                }
            } else {
                player.setLevel(player.getLevel() - 1);
                sendPlayerThirstMessage(player);
            }
        }
    }

    private static void sendPlayerThirstMessage(Player player) {
        String message = "" + ChatColor.YELLOW;
        switch (player.getLevel()) {
            case 10 : player.sendMessage(message += "Whew... I'm a bit thirsty..."      )   ; break;
            case 7  : player.sendMessage(message += "I'd better find some water soon...")   ; break;
            case 3  : player.sendMessage(message += "I'm... I'm a bit parched..."       )   ; break;
            case 1  : player.sendMessage(message += "Dude, I need water NOW!"           )   ; break;
        }
    }
}
