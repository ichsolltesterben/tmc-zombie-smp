package com.themaskedcrusader.zombiesmp.schedule;

import com.themaskedcrusader.zombiesmp.ZombieSmpPlugin;
import com.themaskedcrusader.zombiesmp.beans.CustomChestBean;
import com.themaskedcrusader.zombiesmp.singleton.ChestSingleton;
import com.themaskedcrusader.zombiesmp.utility.WorldUtility;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class CustomChestSchedule {

    public CustomChestSchedule(ZombieSmpPlugin plugin) {
        plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
            public void run() {
                CustomChestSchedule.removeOpenedChests();
            }
        }, 60L, 400L);

        plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
            public void run() {
                CustomChestSchedule.reAddChestWithContents();
            }
        }, 30L, 3600L);
    }

    public static void removeOpenedChests() {
        for (Iterator it = ChestSingleton.getToRemove().iterator() ; it.hasNext();) {
            Location loc = (Location) it.next();
            loc.getWorld().getBlockAt(loc).setType(Material.AIR);
            it.remove();
        }
    }

    public static void reAddChestWithContents() {
        for (Map.Entry<String, CustomChestBean> toReAdd : ChestSingleton.getMapChests().entrySet()) {
            Block block = toReAdd.getValue().getBlock();
            if (block.getType() == Material.AIR) {
                Collection<Entity> nearbyPlayers = WorldUtility.getNearbyPlayers(block.getLocation(), 10);
                if (nearbyPlayers.size() > 0) {
                    String message = ChatColor.RED + "Nearby chests are unable to respawn with you close by.";
                    WorldUtility.sendBulkMessage(nearbyPlayers, message);
                    return;
                }
                block.setType(Material.CHEST);
                block.setData(toReAdd.getValue().getFacing());
                Chest chest = (Chest) block.getState();
                ItemStack[] newInv = ChestSingleton.getNewInventory(-1, toReAdd.getValue().getContents());
                chest.getInventory().clear();
                chest.getInventory().addItem(newInv);
            }
        }
    }


}
