package com.themaskedcrusader.zombiesmp.singleton;

import com.themaskedcrusader.zombiesmp.ZombieSmpPlugin;
import com.themaskedcrusader.zombiesmp.beans.CustomChestBean;
import com.themaskedcrusader.zombiesmp.utility.StringUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ChestSingleton {
    private static HashMap<String, CustomChestBean> mapChests = new HashMap<String, CustomChestBean>();
    private static ArrayList<Location> toRemove = new ArrayList<Location>();

    private static ChestSingleton instance = null;

    private ChestSingleton() {
        // Exists only to defeat instantiation.
    }

    public static ChestSingleton getInstance() {
        if (instance == null) {
            instance = new ChestSingleton();
        }
        return instance;
    }

    public static HashMap<String, CustomChestBean> getMapChests() {
        return mapChests;
    }

    public static void scheduleRemoval(Location location) {
        toRemove.add(location);
    }

    public static ArrayList<Location> getToRemove() {
        return toRemove;
    }

    public static void put(String location, CustomChestBean ccb) {
        mapChests.put(location, ccb);
    }
}
