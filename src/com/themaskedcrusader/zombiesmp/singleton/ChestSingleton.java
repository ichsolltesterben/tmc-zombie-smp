package com.themaskedcrusader.zombiesmp.singleton;

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

    public static void loadChest(Block block) {
        Inventory inv = ((Chest) block.getState()).getBlockInventory();

        // If not a player chest, change contents
        if (!BlockPlaceSingleton.isPlayerPlacedBlock(block)) {
            String location = StringUtils.getLocation(block.getLocation());

            // If chest hasn't been opened this game, add to opened list
            if (!mapChests.containsKey(location)) {
                CustomChestBean ccb = new CustomChestBean();
                ccb.setBlock(block);
                ccb.setLocation(block.getLocation());
                ccb.setFacing(block.getData());
                ccb.setContents(getNewInventory(-1, inv.getContents()));
                ccb.setOpenedDate(new Date());
                mapChests.put(location, ccb);
            }

            // If the inventory holds the (sic) key, reload with other contents
            if (inv.contains(Material.TRIPWIRE_HOOK)) {
                ItemStack[] items = inv.getContents();
                int count = getRandomNumberForLoadingChest(inv);
                ItemStack[] newItems = getNewInventory(count, items);
                inv.clear();
                inv.addItem(newItems);
            }
        }
    }

    public static ItemStack[] getNewInventory(int count, ItemStack[] items) {
        ArrayList<ItemStack> toReturn = new ArrayList<ItemStack>();

        if (count > -1) {
            while (count >= 0) {
                int max = items.length;
                int randomIndex = (int) (Math.random() * max);
                ItemStack item = copyItemStack(items[randomIndex]);
                if (item != null && item.getType() != Material.TRIPWIRE_HOOK) {
                    toReturn.add(item);
                    count--;
                }
            }

        } else {
            for (ItemStack item : items) {
                ItemStack newItem = copyItemStack(item);
                if (newItem != null) {
                    toReturn.add(newItem);
                }
            }
        }

        return toReturn.toArray(new ItemStack[toReturn.size()]);
    }

    public static ItemStack copyItemStack(ItemStack in) {
        if (in != null) {
            ItemStack newItem = new ItemStack(in.getType(), in.getAmount(), in.getDurability());
            newItem.setData(in.getData());
            newItem.addEnchantments(in.getEnchantments());
            return newItem;
        }
        return null;
    }

    private static int getRandomNumberForLoadingChest(Inventory inv) {
        int index = inv.first(Material.TRIPWIRE_HOOK);  // get index of key
        int keys = inv.getItem(index).getAmount();      // get total number of keys
        int abs = Math.abs(index - keys);               // get difference between index and amount
        int calc = (int) (Math.random() * abs);         // choose a random number between 0 and difference
        return calc + Math.min(index, keys);            // add minimum number back on and return
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
}
