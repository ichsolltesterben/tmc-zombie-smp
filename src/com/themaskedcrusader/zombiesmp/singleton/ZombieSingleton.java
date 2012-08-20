package com.themaskedcrusader.zombiesmp.singleton;

import com.themaskedcrusader.zombiesmp.entity.FasterZombie;
import com.themaskedcrusader.zombiesmp.utility.StringUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ZombieSingleton {
    private static HashMap<Integer, ArrayList<ItemStack>> playerZombies = new HashMap<Integer, ArrayList<ItemStack>>();
    private static HashMap<String, Location> authorizedZombies = new HashMap<>();

    private static ZombieSingleton instance = null;

    private ZombieSingleton() {
        // Exists only to defeat instantiation.
    }

    public static ZombieSingleton getInstance() {
        if (instance == null) {
            instance = new ZombieSingleton();
        }
        return instance;
    }

    public static void savePlayerZombie(Entity zombie, PlayerInventory playerInventory) {
        ArrayList<ItemStack> inv = new ArrayList<ItemStack>();
        inv.add(playerInventory.getHelmet());
        inv.add(playerInventory.getChestplate());
        inv.add(playerInventory.getLeggings());
        inv.add(playerInventory.getBoots());
        inv.addAll(Arrays.asList(playerInventory.getContents()));
        playerZombies.put(zombie.getEntityId(), inv);
    }

    public static ArrayList<ItemStack> getZombieInventory(Integer zombieId) {
        return (playerZombies.containsKey(zombieId)) ? playerZombies.get(zombieId) : null ;
    }

    public static void removePlayerZombie(Integer zombieId) {
        playerZombies.remove(zombieId);
    }

    public static void authorize(Location location) {
        String key = StringUtils.getLocation(location);
        authorizedZombies.put(key, location);
    }

    public static boolean isAuthorized(Location location) {
        String key = StringUtils.getLocation(location);
        return authorizedZombies.containsKey(key);
    }

    public static void addEntityToWorld(CreatureSpawnEvent event) {
        Location location = event.getLocation();
        Entity entity = event.getEntity();
        EntityType et = event.getEntityType();
        World world = location.getWorld();

        net.minecraft.server.World mcWorld = ((CraftWorld) world).getHandle();
        net.minecraft.server.Entity mcEntity = (((CraftEntity) entity).getHandle());

        if (et == EntityType.ZOMBIE && !(mcEntity instanceof FasterZombie)){
            FasterZombie FasterZombie = new FasterZombie(mcWorld);

            FasterZombie.setPosition(location.getX(), location.getY(), location.getZ());

            mcWorld.removeEntity(mcEntity);
            mcWorld.addEntity(FasterZombie, CreatureSpawnEvent.SpawnReason.CUSTOM);
        }
    }
}
