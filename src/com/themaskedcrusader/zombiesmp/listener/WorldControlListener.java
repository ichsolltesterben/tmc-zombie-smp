package com.themaskedcrusader.zombiesmp.listener;

import com.themaskedcrusader.zombiesmp.ZombieSmpPlugin;
import com.themaskedcrusader.zombiesmp.singleton.BlockPlaceSingleton;
import com.themaskedcrusader.zombiesmp.utility.BlockBreakUtility;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class WorldControlListener implements Listener {

    public WorldControlListener(ZombieSmpPlugin plugin ) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void preventBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (BlockBreakUtility.canBreakBlock(block, player)) {
            BlockPlaceSingleton.removeIfApplicable(block);
        } else {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void allowBreakOfWebWithIronShovel(PlayerInteractEvent event) {
        try {
            Player player = event.getPlayer();
            Block block = event.getClickedBlock();
            if (block.getType() == Material.WEB && player.getItemInHand().getType() == Material.IRON_SPADE) {
                block.setType(Material.AIR);
                BlockPlaceSingleton.removeIfApplicable(block);
                block.getWorld().dropItem(block.getLocation(), new ItemStack(Material.WEB));
            }
        } catch (Exception ignored) { /* Weird NPEs are being thrown here on occasion. */ }
    }

    @EventHandler
    public void preventLandDestructionOnExplode(EntityExplodeEvent event) {
        event.blockList().clear();
    }

    @EventHandler
    public void preventBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlockPlaced();
        event.setCancelled(BlockPlaceSingleton.placeBlock(event));
    }
}
