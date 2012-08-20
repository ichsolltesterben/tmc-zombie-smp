package com.themaskedcrusader.zombiesmp.listener;

import com.themaskedcrusader.zombiesmp.ZombieSmpPlugin;
import com.themaskedcrusader.zombiesmp.entity.FasterZombie;
import com.themaskedcrusader.zombiesmp.singleton.ZombieSingleton;
import com.themaskedcrusader.zombiesmp.utility.ZombieSpawnUtility;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class ZombieListener implements Listener {

    ZombieSmpPlugin plugin;

    public ZombieListener(ZombieSmpPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void spawnZombie(CreatureSpawnEvent event) {
        ZombieSpawnUtility utility = new ZombieSpawnUtility();
        Entity entity = event.getEntity();
        net.minecraft.server.Entity mcEntity = (((CraftEntity) entity).getHandle());
        if (mcEntity instanceof FasterZombie) {
            event.setCancelled(false);
        } else if (entity.getType() == EntityType.ZOMBIE && ZombieSingleton.isAuthorized(entity.getLocation())) {
            ZombieSingleton.addEntityToWorld(event);
            event.setCancelled(true);
        } else {
            utility.spawnZombies(event.getEntity());
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onZombieDeath(EntityDeathEvent event) {
        if (event.getEntity().getType() == EntityType.ZOMBIE) {
            Entity entity = event.getEntity();
            ArrayList<ItemStack> inv = ZombieSingleton.getZombieInventory(entity.getEntityId());
            if (inv != null) {
                event.getDrops().addAll(inv);
                ZombieSingleton.removePlayerZombie(entity.getEntityId());
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
