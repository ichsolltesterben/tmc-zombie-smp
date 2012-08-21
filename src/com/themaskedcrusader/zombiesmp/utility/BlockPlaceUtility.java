package com.themaskedcrusader.zombiesmp.utility;

import com.themaskedcrusader.zombiesmp.singleton.BlockPlaceSingleton;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class BlockPlaceUtility {

    public static boolean canBreakBlock(Block block, Player player) {
        switch(block.getType()) {
            case STONE_BUTTON :
            {
                if (BlockPlaceSingleton.blockRemovedFromList(block)) {
                    block.setType(Material.AIR);
                }
                return false;
            }

            case MELON_BLOCK :
            {
                if (player.getItemInHand().getType() == Material.WOOD_HOE) {
                    BlockPlaceSingleton.blockRemovedFromList(block);
                    return true;
                } else {
                    return false;
                }
            }

            case TORCH :
            {
                if (BlockPlaceSingleton.blockRemovedFromList(block)) {
                    block.setType(Material.AIR);
                }
                return false;
            }

            case CHEST: {
                {
                    if (BlockPlaceSingleton.blockRemovedFromList(block)) {
                        block.setType(Material.AIR);
                    }
                    return false;
                }
            }
        }

        return false;
    }

}
