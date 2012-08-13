package com.themaskedcrusader.zombiesmp.listener;

import com.themaskedcrusader.zombiesmp.ZombieSmpPlugin;
import com.themaskedcrusader.zombiesmp.singleton.PlayerZombiesSingleton;
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
import org.bukkit.event.player.PlayerTeleportEvent;

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
        Player player = event.getEntity();
        event.getDrops().clear();
        Location location = player.getLocation();
        Entity zombie = player.getWorld().spawnEntity(location, EntityType.ZOMBIE);
        PlayerZombiesSingleton.getInstance().savePlayerZombie(zombie, player.getInventory());
    }

    @EventHandler
    public void preventTeleportAndTurnEnderPearlsIntoGrenades(PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            event.setCancelled(true);
            Location toExplode = event.getTo();
            event.getPlayer().getWorld().createExplosion(toExplode, 3f);
        }
    }

    @EventHandler
    public void opIsInvincible(EntityDamageEvent event) {
//        Entity entity = event.getEntity();
//        if (entity instanceof Player) {
//            event.setCancelled(((Player) entity).isOp());
//        }
    }
}
