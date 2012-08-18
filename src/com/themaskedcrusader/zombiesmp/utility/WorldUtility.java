package com.themaskedcrusader.zombiesmp.utility;

import org.bukkit.Location;
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

    public static void sendBulkMessage(Collection<Entity> nearbyPlayers, String message) {
        for (Entity player: nearbyPlayers) {
            if (player instanceof Player) {
                ((Player) player).sendMessage(message);
            }
        }
    }
}
