package com.themaskedcrusader.zombiesmp.utility;

import com.themaskedcrusader.zombiesmp.ZombieSmpPlugin;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.ArrayList;
import java.util.List;

public class ZombieSpawnUtility {

    public boolean canSpawnZombies(Entity entity) {
        List nearbyZombies = removeNonZombies(entity.getNearbyEntities(40, 40, 40));
        return (nearbyZombies.size() < 10);
    }

    public void spawnZombies(ZombieSmpPlugin plugin, Entity entity) {
        int toSpawn = howManyZombiesToSpawn();
        plugin.setCurrentlySpawning(true);
        for(int i = 0 ; i < toSpawn ; i++) {
            World world = entity.getWorld();
            Location location = entity.getLocation();
            // choose a location within 3 blocks of the chosen location, so that multiple spawns are not in the same location
            Location vector = new Location(world, Math.random() * 3, Math.random() * 3, Math.random() * 3 );
            entity.getWorld().spawnEntity(location.add(vector), EntityType.ZOMBIE);
        }
        plugin.setCurrentlySpawning(false);
    }

    private int howManyZombiesToSpawn() {
        // only a 5% chance of spawning a horde, and the horde max size is 8;
        double chance = Math.random();
        return (chance < 0.05d) ? (int) (Math.random() * 7) : 0;
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
