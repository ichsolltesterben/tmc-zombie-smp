package com.themaskedcrusader.zombiesmp;

import com.themaskedcrusader.zombiesmp.entity.FasterZombie;
import com.themaskedcrusader.zombiesmp.listener.MobListener;
import com.themaskedcrusader.zombiesmp.listener.PlayerListener;
import com.themaskedcrusader.zombiesmp.listener.WorldControlListener;
import com.themaskedcrusader.zombiesmp.listener.ZombieListener;
import com.themaskedcrusader.zombiesmp.schedule.BlockPlaceSchedule;
import com.themaskedcrusader.zombiesmp.schedule.CustomChestSchedule;
import com.themaskedcrusader.zombiesmp.schedule.PlayerThirstSchedule;
import com.themaskedcrusader.zombiesmp.schedule.PlayerVisibilitySchedule;
import com.themaskedcrusader.zombiesmp.utility.CommandUtility;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Method;

public class ZombieSmpPlugin extends JavaPlugin {
    private boolean currentlySpawning = false;

    public void onEnable() {
        loadConfiguration();
        registerListeners();
        registerSchedules();
        registerCommands();
        registerNewZombies();
        getLogger().info("Plugin Activated");
    }

    private void registerListeners() {
        new PlayerListener(this);
        new ZombieListener(this);
        new MobListener(this);
        new WorldControlListener(this);
    }

    private void registerSchedules() {
        new BlockPlaceSchedule(this);
        new CustomChestSchedule(this);
        new PlayerThirstSchedule(this);
        new PlayerVisibilitySchedule(this);
    }

    private void registerCommands() {
        new CommandUtility(this);
    }

    private void loadConfiguration() {
        String pluginFolder = this.getDataFolder().getAbsolutePath();
        new File(pluginFolder).mkdirs();
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void registerNewZombies() {
        try{
            Class[] args = new Class[3];
            args[0] = Class.class;
            args[1] = String.class;
            args[2] = int.class;

            Method a = net.minecraft.server.EntityTypes.class.getDeclaredMethod("a", args);
            a.setAccessible(true);

            a.invoke(a, FasterZombie.class, "Zombie", 54);

        }catch (Exception e){
            e.printStackTrace();
            this.setEnabled(false);
        }
    }

    public void onDisable() {
        BlockPlaceSchedule.cleanUpAllBlocks();
//        CustomChestSchedule.replaceOpenedMapChests();
        getLogger().info("Plugin Deactivated!");
    }

    public boolean isCurrentlySpawning() {
        return currentlySpawning;
    }

    public void setCurrentlySpawning(boolean currentlySpawning) {
        this.currentlySpawning = currentlySpawning;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return CommandUtility.fireCommand(commandSender, command, s, strings);

    }
}
