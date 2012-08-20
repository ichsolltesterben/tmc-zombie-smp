package com.themaskedcrusader.zombiesmp.utility;

import com.themaskedcrusader.zombiesmp.ZombieSmpPlugin;
import com.themaskedcrusader.zombiesmp.singleton.ZombieSingleton;
import net.minecraft.server.EntityTypes;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.ArrayList;
import java.util.List;

public class ZombieSpawnUtility {

    public boolean canSpawnZombies(Entity entity) {
        List nearbyZombies = removeNonZombies(entity.getNearbyEntities(20, 20, 20));
        return (nearbyZombies.size() < 10);
    }

    public boolean spawnZombies(LivingEntity entity) {
        if (entity.getType() != EntityType.ZOMBIE) {
            return true;
        } else if (!canSpawnZombies(entity)) {
            return true;
        }

        int zombiesToSpawn = howManyZombiesToSpawn();
        World world = entity.getWorld();
        Location location = entity.getLocation();
        for(int i = 0 ; i < zombiesToSpawn ; i++) {
            Location vector = new Location(world, Math.random() * 3, Math.random() * 3, Math.random() * 3 );
            Location authorized = new Location(world, location.getX() + vector.getX(), location.getY() + vector.getY(), location.getZ() + vector.getZ());
            ZombieSingleton.authorize(authorized);
            world.spawnEntity(authorized, EntityType.ZOMBIE);
        }
        return true;
    }

    private int howManyZombiesToSpawn() {
        // only a 5% chance of spawning a horde, and the horde max size is 8;
        double chance = Math.random();
        return (chance < 0.05d) ? (int) (Math.random() * 8) : 1;
    }

    private List<Entity> removeNonZombies(List<Entity> nearbyEntities) {
        List<Entity> onlyZombies = new ArrayList<Entity>();
        for (Entity entity : nearbyEntities) {
            if (entity.getType() == EntityType.ZOMBIE) {
                onlyZombies.add(entity);
            }
        }
        return onlyZombies;
    }
}
