package com.themaskedcrusader.zombiesmp.singleton;

import com.themaskedcrusader.zombiesmp.beans.BlockPlaceBean;
import com.themaskedcrusader.zombiesmp.utility.StringUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class BlockPlaceSingleton {
    static HashMap<String, BlockPlaceBean> buttons = new HashMap<String, BlockPlaceBean>();
    static HashMap<String, BlockPlaceBean> torches = new HashMap<String, BlockPlaceBean>();
    static HashMap<String, BlockPlaceBean> webs = new HashMap<String, BlockPlaceBean>();
    static HashMap<String, BlockPlaceBean> melons = new HashMap<String, BlockPlaceBean>();

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
            default: return true;
        }
    }

    public static HashMap<String, BlockPlaceBean> getList(Material material) {
        switch(material) {
            case STONE_BUTTON: return buttons;
            case        TORCH: return torches;
            case          WEB: return webs;
            case  MELON_BLOCK: return melons;
            default: return null;
        }
    }

    private static boolean isBlockInListOrRemove(Block block, boolean remove) {
        HashMap<String, BlockPlaceBean> list = getList(block.getType());
        String location = StringUtils.getLocation(block.getLocation());

        if (list.containsKey(location)) {
            if (remove) {
                list.remove(location);
            }
            return true;
        }
        return false;
    }

    public static boolean isBlockInList(Block block) {
        return isBlockInListOrRemove(block, false);
    }

    public static void removeIfApplicable(Block block) {
        isBlockInListOrRemove(block, true);
    }

    public static void remove(Block block) {
        HashMap<String, BlockPlaceBean> list = getList(block.getType());
        String toRemoveKey = StringUtils.getLocation(block.getLocation());
        list.remove(toRemoveKey);
    }
}
