package com.themaskedcrusader.zombiesmp.utility;

import com.themaskedcrusader.zombiesmp.singleton.BlockPlaceSingleton;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class BlockBreakUtility {

    // todo: prevent break of map buttons
    public static boolean canBreakBlock(Block block, Player player) {
        switch(block.getType()) {
            case STONE_BUTTON : block.setType(Material.AIR); return true;
            case MELON_BLOCK: return (player.getItemInHand().getType() == Material.WOOD_HOE);
            case TORCH: if (BlockPlaceSingleton.isBlockInList(block)) {
                block.setType(Material.AIR);
                return true;
            }
            default: return false;
        }
    }
}
