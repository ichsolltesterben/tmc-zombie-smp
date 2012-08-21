package com.themaskedcrusader.zombiesmp.singleton;

import com.themaskedcrusader.zombiesmp.beans.BlockPlaceBean;
import com.themaskedcrusader.zombiesmp.utility.StringUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class BlockPlaceSingleton {
    static HashMap<String, BlockPlaceBean> buttons = new HashMap<String, BlockPlaceBean>();
    static HashMap<String, BlockPlaceBean> torches = new HashMap<String, BlockPlaceBean>();
    static HashMap<String, BlockPlaceBean> webs = new HashMap<String, BlockPlaceBean>();
    static HashMap<String, BlockPlaceBean> melons = new HashMap<String, BlockPlaceBean>();
    static HashMap<String, BlockPlaceBean> chests = new HashMap<String, BlockPlaceBean>();

    private static BlockPlaceSingleton instance = null;

    private BlockPlaceSingleton() {
        // Exists only to defeat instantiation.
    }

    public static BlockPlaceSingleton getInstance() {
        if (instance == null) {
            instance = new BlockPlaceSingleton();
        }
        return instance;
    }

    public static boolean placeBlock(BlockPlaceEvent event) {
        Block block = event.getBlock();
        String location = StringUtils.getLocation(block.getLocation());
        BlockPlaceBean bean = new BlockPlaceBean();
        bean.setBlock(block);
        bean.setPlacedDate(new Date());
        switch (event.getBlock().getType()) {
            case STONE_BUTTON: buttons.put(location, bean);   return false;
            case        TORCH: torches.put(location, bean);   return false;
            case          WEB: webs.put(location, bean);      return false;
            case  MELON_BLOCK: melons.put(location, bean);    return false;
            case        CHEST: chests.put(location, bean);    return false;
            default: return true;
        }
    }

    public static HashMap<String, BlockPlaceBean> getList(Material material) {
        switch(material) {
            case STONE_BUTTON: return buttons;
            case        TORCH: return torches;
            case          WEB: return webs;
            case  MELON_BLOCK: return melons;
            case        CHEST: return chests;
            default: return null;
        }
    }

    public static boolean blockRemovedFromList(Block block) {
        HashMap<String, BlockPlaceBean> list = getList(block.getType());
        String location = StringUtils.getLocation(block.getLocation());

        if (list.containsKey(location)) {
            list.remove(location);
            return true;
        }
        return false;
    }

    public static boolean isPlayerPlacedBlock(Block block) {
        String location = StringUtils.getLocation(block.getLocation());
        HashMap<String, BlockPlaceBean> list = getList(block.getType());
        if (list != null) {
            return list.containsKey(location);
        }

        return false;
    }
}
