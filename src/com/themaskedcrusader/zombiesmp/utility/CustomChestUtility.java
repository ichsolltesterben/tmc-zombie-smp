package com.themaskedcrusader.zombiesmp.utility;

import com.themaskedcrusader.zombiesmp.ZombieSmpPlugin;
import com.themaskedcrusader.zombiesmp.beans.CustomChestBean;
import com.themaskedcrusader.zombiesmp.singleton.BlockPlaceSingleton;
import com.themaskedcrusader.zombiesmp.singleton.ChestSingleton;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Date;

public class CustomChestUtility  {
    ZombieSmpPlugin plugin;

    public CustomChestUtility(ZombieSmpPlugin plugin) {
        this.plugin = plugin;
    }

    public void loadChest(Block block) {
        Inventory inv = ((Chest) block.getState()).getBlockInventory();

        // If not a player chest, change contents
        if (!BlockPlaceSingleton.isPlayerPlacedBlock(block)) {
            String location = StringUtils.getLocation(block.getLocation());

            // If chest hasn't been opened this game, add to opened list
            if (!ChestSingleton.getMapChests().containsKey(location)) {
                CustomChestBean ccb = new CustomChestBean();
                ccb.setBlock(block);
                ccb.setLocation(block.getLocation());
                ccb.setFacing(block.getData());
                ccb.setContents(getNewInventory(-1, inv.getContents()));
                ccb.setOpenedDate(new Date());
                ChestSingleton.put(location, ccb);
            }

            // If the inventory holds the (sic) key, reload with other contents
            if (inv.contains(Material.getMaterial(plugin.getConfig().getInt("items.chest.key")))) {
                ItemStack[] items = inv.getContents();
                int count = getRandomNumberForLoadingChest(inv);
                ItemStack[] newItems = getNewInventory(count, items);
                inv.clear();
                inv.addItem(newItems);
            }
        }
    }

    public ItemStack[] getNewInventory(int count, ItemStack[] items) {
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

    public ItemStack copyItemStack(ItemStack in) {
        if (in != null) {
            ItemStack newItem = new ItemStack(in.getType(), in.getAmount(), in.getDurability());
            newItem.setData(in.getData());
            newItem.addEnchantments(in.getEnchantments());
            return newItem;
        }
        return null;
    }

    private int getRandomNumberForLoadingChest(Inventory inv) {
        int materialId = plugin.getConfig().getInt("items.chest.key");
        int index = inv.first(Material.getMaterial(materialId));    // get index of key
        int keys = inv.getItem(index).getAmount();                  // get total number of keys
        int abs = Math.abs(index - keys);                           // get difference between index and amount
        int calc = (int) (Math.random() * abs);                     // choose a random number between 0 and difference
        return calc + Math.min(index, keys);                        // add minimum number back on and return
    }
}
