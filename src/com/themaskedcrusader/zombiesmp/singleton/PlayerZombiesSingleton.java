package com.themaskedcrusader.zombiesmp.singleton;

import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PlayerZombiesSingleton {
    private static HashMap<Integer, ArrayList<ItemStack>> playerZombies = new HashMap<Integer, ArrayList<ItemStack>>();

    private static PlayerZombiesSingleton instance = null;

    private PlayerZombiesSingleton() {
        // Exists only to defeat instantiation.
    }

    public static PlayerZombiesSingleton getInstance() {
        if (instance == null) {
            instance = new PlayerZombiesSingleton();
        }
        return instance;
    }

    public void savePlayerZombie(Entity zombie, PlayerInventory playerInventory) {
        ArrayList<ItemStack> inv = new ArrayList<ItemStack>();
        inv.add(playerInventory.getHelmet());
        inv.add(playerInventory.getChestplate());
        inv.add(playerInventory.getLeggings());
        inv.add(playerInventory.getBoots());
        inv.addAll(Arrays.asList(playerInventory.getContents()));
        playerZombies.put(zombie.getEntityId(), inv);
    }

    public ArrayList<ItemStack> getZombieInventory(Integer zombieId) {
        return (playerZombies.containsKey(zombieId)) ? playerZombies.get(zombieId) : null ;
    }

    public static void removeZombie(Integer zombieId) {
        playerZombies.remove(zombieId);
    }
}
