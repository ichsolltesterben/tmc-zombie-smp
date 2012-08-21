package com.themaskedcrusader.zombiesmp.schedule;

import com.themaskedcrusader.zombiesmp.ZombieSmpPlugin;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityPlayer;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class PlayerVisibilitySchedule {
    static ZombieSmpPlugin plugin;
    static HashMap<String, Double> playerX = new HashMap<>();
    static HashMap<String, Double> playerY = new HashMap<>();
    static HashMap<String, Double> playerZ = new HashMap<>();

    public PlayerVisibilitySchedule(ZombieSmpPlugin plugin) {
        this.plugin = plugin;
        if (plugin.getConfig().getBoolean("player.visibility.enabled")) {
            plugin.getLogger().info("Player Visibility System Activated!");
            plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                public void run() {
                    PlayerVisibilitySchedule.storeMidVelocity();
                }
            }, 5L, plugin.getConfig().getLong("player.visibility.ticks"));

            plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                public void run() {
                    PlayerVisibilitySchedule.calculateVisibility();
                }
            }, 10L, plugin.getConfig().getLong("player.visibility.ticks"));
        }
    }

    private static void storeMidVelocity() {
        // Zero out values
        playerX = new HashMap<>();
        playerY = new HashMap<>();
        playerZ = new HashMap<>();
        Player[] players = plugin.getServer().getOnlinePlayers();
        for (Player player : players) {
            playerX.put(player.getUniqueId().toString(), player.getLocation().getX());
            playerY.put(player.getUniqueId().toString(), player.getLocation().getY());
            playerZ.put(player.getUniqueId().toString(), player.getLocation().getZ());
        }
    }

    public static void calculateVisibility() {
        Player[] players = plugin.getServer().getOnlinePlayers();
        for (Player player : players) {
            float visibility = calculateVisibility(player);
            player.setExp(visibility / 8);
        }
    }

    private static float calculateVisibility(Player player) {
        float visibility = 3;
        boolean playerMovingX = playerX.get(player.getUniqueId().toString()) != player.getLocation().getX();
        boolean playerMovingY = playerY.get(player.getUniqueId().toString()) != player.getLocation().getY();
        boolean playerMovingZ = playerZ.get(player.getUniqueId().toString()) != player.getLocation().getZ();

        if (player.isSneaking()) visibility -= 2;
        if (playerMovingX || playerMovingZ) visibility += 1;    // moving
        if (playerMovingY) visibility += 1;                     // in the air (jumping / falling)
        if (player.isSprinting()) visibility += 2;
        if (player.getWorld().isThundering()) visibility -= 1;  // 1 reduction for thunder
        return visibility;
    }
}
