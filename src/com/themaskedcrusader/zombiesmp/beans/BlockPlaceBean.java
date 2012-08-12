package com.themaskedcrusader.zombiesmp.beans;

import org.bukkit.block.Block;

import java.util.Date;

public class BlockPlaceBean {
    private Block block;
    private Date placedDate;

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public Date getPlacedDate() {
        return placedDate;
    }

    public void setPlacedDate(Date placedDate) {
        this.placedDate = placedDate;
    }
}
