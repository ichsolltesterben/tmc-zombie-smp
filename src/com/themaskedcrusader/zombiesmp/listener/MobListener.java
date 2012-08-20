package com.themaskedcrusader.zombiesmp.listener;

import com.themaskedcrusader.zombiesmp.ZombieSmpPlugin;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;

@SuppressWarnings("unused")
public class MobListener implements Listener {
    ZombieSmpPlugin plugin;

    public MobListener(ZombieSmpPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void preventAllMobSpawning(CreatureSpawnEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void allowAnimalSpawningFromSpawner(CreatureSpawnEvent event) {
        EntityType et = event.getEntityType();
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER) {
            if (et == EntityType.PIG || et == EntityType.COW || et == EntityType.CHICKEN) {
                event.setCancelled(false);
            }
        }
    }
}
