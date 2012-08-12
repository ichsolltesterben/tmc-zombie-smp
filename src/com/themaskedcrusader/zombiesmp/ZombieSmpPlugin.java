package com.themaskedcrusader.zombiesmp;

import com.themaskedcrusader.zombiesmp.listener.MobListener;
import com.themaskedcrusader.zombiesmp.listener.PlayerListener;
import com.themaskedcrusader.zombiesmp.listener.WorldControlListener;
import com.themaskedcrusader.zombiesmp.schedule.BlockPlaceSchedule;
import org.bukkit.plugin.java.JavaPlugin;

public class ZombieSmpPlugin extends JavaPlugin {
    private boolean currentlySpawning = false;

    public void onEnable() {
        registerListeners();
        registerSchedules();
        getLogger().info("Plugin Activated");
    }

    private void registerListeners() {
        new PlayerListener(this);
        new MobListener(this);
        new WorldControlListener(this);
    }

    private void registerSchedules() {
        new BlockPlaceSchedule(this);
    }

    public void onDisable() {
        BlockPlaceSchedule.cleanUpAllBlocks();
        getLogger().info("Plugin Deactivated!");
    }

    public boolean isCurrentlySpawning() {
        return currentlySpawning;
    }

    public void setCurrentlySpawning(boolean currentlySpawning) {
        this.currentlySpawning = currentlySpawning;
    }
}
