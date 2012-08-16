package com.themaskedcrusader.zombiesmp;

import com.themaskedcrusader.zombiesmp.listener.MobListener;
import com.themaskedcrusader.zombiesmp.listener.PlayerListener;
import com.themaskedcrusader.zombiesmp.listener.WorldControlListener;
import com.themaskedcrusader.zombiesmp.schedule.BlockPlaceSchedule;
import com.themaskedcrusader.zombiesmp.schedule.CustomChestSchedule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
//        new CustomChestSchedule(this);
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

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(cmd.getName().equalsIgnoreCase("respawnChests")){
            CustomChestSchedule.reAddChestWithContents();
            return true;
        }

        if(cmd.getName().equalsIgnoreCase("despawnChests")){
            CustomChestSchedule.removeOpenedChests();
            return true;
        }

        return false;
    }
}
