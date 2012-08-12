package com.themaskedcrusader.zombiesmp.listener;

import com.themaskedcrusader.zombiesmp.ZombieSmpPlugin;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    public PlayerListener(ZombieSmpPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage("Welcome to the Game!!!!");
        if (player.isOp()) {
            player.sendMessage("You are an OP");
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void preventHealthRegeneration(EntityRegainHealthEvent event) {
        event.setCancelled(event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED);
    }

    @EventHandler
    public void spawnZombieOnPlayerDeath(PlayerDeathEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            Location location = entity.getLocation();
            entity.getWorld().spawnEntity(location, EntityType.ZOMBIE);
        }
    }

    @EventHandler
    public void opIsInvincible(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            event.setCancelled(((Player) entity).isOp());
        }
    }
}
