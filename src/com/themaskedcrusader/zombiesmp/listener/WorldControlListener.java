package com.themaskedcrusader.zombiesmp.listener;

import com.themaskedcrusader.zombiesmp.ZombieSmpPlugin;
import com.themaskedcrusader.zombiesmp.singleton.BlockPlaceSingleton;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class WorldControlListener implements Listener {

    public WorldControlListener(ZombieSmpPlugin plugin ) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void preventBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        event.setCancelled(true);
    }

    @EventHandler
    public void preventBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlockPlaced();
        event.setCancelled(BlockPlaceSingleton.placeBlock(event));
    }
}
