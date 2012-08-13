package com.themaskedcrusader.zombiesmp.listener;

import com.themaskedcrusader.zombiesmp.ZombieSmpPlugin;
import com.themaskedcrusader.zombiesmp.singleton.PlayerZombiesSingleton;
import com.themaskedcrusader.zombiesmp.utility.ZombieSpawnUtility;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class MobListener implements Listener {
    ZombieSmpPlugin plugin;

    public MobListener(ZombieSmpPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void preventMobSpawningExceptZombie(CreatureSpawnEvent event) {
        ZombieSpawnUtility utility = new ZombieSpawnUtility();
        Entity entity = event.getEntity();
        if (!plugin.isCurrentlySpawning()) {
            if (entity.getType() == EntityType.ZOMBIE && utility.canSpawnZombies(entity)) {
                utility.spawnZombies(plugin, entity);
                event.setCancelled(true);
            } else {
                event.setCancelled(true);
            }
        } else if (entity.getType() != EntityType.ZOMBIE ) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void allowAnimalSpawningFromSpawner(CreatureSpawnEvent event) {
        EntityType et = event.getEntityType();
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER) {
            if (et == EntityType.PIG || et == EntityType.COW || et == EntityType.CHICKEN) {
                event.setCancelled(false);
            }
        }
    }

    @EventHandler
    public void onZombieDeath(EntityDeathEvent event) {
        if (event.getEntity().getType() == EntityType.ZOMBIE) {
            Entity entity = event.getEntity();
            ArrayList<ItemStack> inv = PlayerZombiesSingleton.getInstance().getZombieInventory(entity.getEntityId());
            if (inv != null) {
                event.getDrops().addAll(inv);
                PlayerZombiesSingleton.removeZombie(entity.getEntityId());
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void cancelZombieFire(EntityCombustEvent event) {
        EntityType et = event.getEntityType();
        event.setCancelled(et == EntityType.ZOMBIE);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void allowZombieFireByPlayer(EntityCombustByEntityEvent event) {
        event.setCancelled(false);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void allowZombieFireByBlock(EntityCombustByBlockEvent event) {
        event.setCancelled(false);
    }

}
