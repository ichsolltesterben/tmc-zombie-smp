package com.themaskedcrusader.zombiesmp.singleton;

import com.themaskedcrusader.zombiesmp.beans.BlockPlaceBean;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class BlockPlaceSingleton {
    static ArrayList<BlockPlaceBean> buttons = new ArrayList<BlockPlaceBean>();
    static ArrayList<BlockPlaceBean> torches = new ArrayList<BlockPlaceBean>();
    static ArrayList<BlockPlaceBean> webs = new ArrayList<BlockPlaceBean>();
    static ArrayList<BlockPlaceBean> melons = new ArrayList<BlockPlaceBean>();

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
        BlockPlaceBean bean = new BlockPlaceBean();
        bean.setBlock(event.getBlock());
        bean.setPlacedDate(new Date());
        switch (event.getBlock().getType()) {
            case STONE_BUTTON: buttons.add(bean);   return false;
            case        TORCH: torches.add(bean);   return false;
            case          WEB: webs.add(bean);      return false;
            case  MELON_BLOCK: melons.add(bean);    return false;
            default: return true;
        }
    }

    public static ArrayList<BlockPlaceBean> getList(Material material) {
        switch(material) {
            case STONE_BUTTON: return buttons;
            case        TORCH: return torches;
            case          WEB: return webs;
            case  MELON_BLOCK: return melons;
            default: return null;
        }
    }

    private static boolean isBlockInListOrRemove(Block block, boolean remove) {
        ArrayList<BlockPlaceBean> list = getList(block.getType());

        for (Iterator it = list.iterator() ; it.hasNext() ; ) {
            BlockPlaceBean bean = (BlockPlaceBean) it.next();
            if (bean.getBlock().getLocation().getBlockX() == block.getLocation().getBlockX() &&
                    bean.getBlock().getLocation().getBlockY() == block.getLocation().getBlockY() &&
                    bean.getBlock().getLocation().getBlockZ() == block.getLocation().getBlockZ()) {
                if (remove) {
                    it.remove();
                }
                return true;
            }
        }
        return false;
    }

    public static boolean isBlockInList(Block block) {
        return isBlockInListOrRemove(block, false);
    }

    public static void removeIfApplicable(Block block) {
        isBlockInListOrRemove(block, true);
    }
}
