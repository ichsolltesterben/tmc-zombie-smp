package com.themaskedcrusader.zombiesmp.utility;

import com.themaskedcrusader.zombiesmp.entity.FasterZombie;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;

public class WorldUtility {

    public static Collection<Entity> getNearbyPlayers(Location location, int radius) {
        ArrayList<Entity> toReturn = new ArrayList<Entity>();
        final Collection<org.bukkit.entity.Entity> worldEntities = location.getWorld().getEntities();

        for (Entity entity : worldEntities) {
            if (entity instanceof Player) {
                if (entity.getLocation().distance(location) < radius) {
                    toReturn.add(entity);
                }
            }
        }
        return toReturn;
    }

    public static Entity getNearestZombie(Location location) {
        final Collection<org.bukkit.entity.Entity> worldEntities = location.getWorld().getEntities();

        // a little intense, very sloppy and very inefficient, but necessary for now. Will clean up later
        int r = 1;
        while (r < 10) {
            for (Entity entity : worldEntities) {
                net.minecraft.server.Entity mcEntity = (((CraftEntity) entity).getHandle());
                if (entity instanceof FasterZombie) {
                    if (entity.getLocation().distance(location) < r) {
                        return entity;
                    }
                }
            }
            r++;
        }
        return null;
    }

    public static void sendBulkMessage(Collection<Entity> nearbyPlayers, String message) {
        for (Entity player: nearbyPlayers) {
            if (player instanceof Player) {
                ((Player) player).sendMessage(message);
            }
        }
    }
}
