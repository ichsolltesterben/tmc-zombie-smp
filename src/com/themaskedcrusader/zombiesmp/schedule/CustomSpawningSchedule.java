package com.themaskedcrusader.zombiesmp.schedule;

import com.themaskedcrusader.zombiesmp.ZombieSmpPlugin;

public class CustomSpawningSchedule {


    public CustomSpawningSchedule(ZombieSmpPlugin plugin) {
        if (plugin.getConfig().getBoolean("custom_spawning.enabled")) {
            plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
                public void run() {
                    CustomSpawningSchedule.spawnMobs();
                }
            }, 30L, plugin.getConfig().getLong("custom_spawning.zombie_seconds") * 20L);
        }
    }

    public static void spawnMobs() {
        // custom mob spawning algorithm.
    }
}
