package com.themaskedcrusader.zombiesmp.schedule;

import com.themaskedcrusader.zombiesmp.ZombieSmpPlugin;
import com.themaskedcrusader.zombiesmp.beans.BlockPlaceBean;
import com.themaskedcrusader.zombiesmp.singleton.BlockPlaceSingleton;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class BlockPlaceSchedule {

    public BlockPlaceSchedule(ZombieSmpPlugin plugin) {
        plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {

            public void run() {
                BlockPlaceSchedule.cleanUpBlocks();
            }

        }, 60L, 200L);
    }

    public static void cleanUpBlocks() {
        cleanUpBlocks(Material.STONE_BUTTON, 60000); // 60 seconds
        cleanUpBlocks(Material.TORCH, 300000);       //  5 minutes
        cleanUpBlocks(Material.WEB, 600000);         // 10 minutes
        cleanUpBlocks(Material.MELON_BLOCK, 60000);  // 60 seconds
    }


    private static void cleanUpBlocks(Material material, int timeSpan) {
        Date date = new Date();
        for (Map.Entry<String, BlockPlaceBean> block : BlockPlaceSingleton.getList(material).entrySet()) {
            BlockPlaceBean toRemove = block.getValue();
            if (date.getTime() - toRemove.getPlacedDate().getTime() > timeSpan) {
                BlockPlaceSingleton.remove(toRemove.getBlock());
                removeBlock(toRemove);
            }
        }
    }

    private static void removeBlock(BlockPlaceBean toRemove) {
        Block block = toRemove.getBlock().getWorld().getBlockAt(toRemove.getBlock().getLocation());
        block.setType(Material.AIR);
    }

    // force cleanup on command on unload;
    public static void cleanUpAllBlocks() {
        cleanUpBlocks(Material.STONE_BUTTON, 0);
        cleanUpBlocks(Material.TORCH, 0);
        cleanUpBlocks(Material.WEB, 0);
        cleanUpBlocks(Material.MELON_BLOCK, 0);
    }
}
