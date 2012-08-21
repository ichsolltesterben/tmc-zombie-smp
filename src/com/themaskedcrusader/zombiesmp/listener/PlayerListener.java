package com.themaskedcrusader.zombiesmp.listener;

import com.themaskedcrusader.zombiesmp.ZombieSmpPlugin;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
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
    static ZombieSmpPlugin plugin;

    public PlayerListener(ZombieSmpPlugin plugin) {
        this.plugin = plugin;
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
        if (plugin.getConfig().getBoolean("player.death.zombie")) {
            // spawn zombie on player death
            if (plugin.getConfig().getBoolean("player.zombie.inventory")) {
                // store player's inventory inside zombie
            }
        }
    }

    @EventHandler
    public void preventTeleportAndTurnEnderPearlsIntoGrenades(PlayerTeleportEvent event) {
        if (plugin.getConfig().getBoolean("items.end_grenade.enabled"))
            if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
                event.setCancelled(true);
                Location toExplode = event.getTo();
                event.getPlayer().getWorld().createExplosion(toExplode,
                        (float) plugin.getConfig().getDouble("items.end_grenade.radius"));
            }
    }

    @EventHandler
    public void opIsInvincible(EntityDamageEvent event) {
        if (plugin.getConfig().getBoolean("player.op.is_god")) {
            Entity entity = event.getEntity();
            if (entity instanceof Player) {
                event.setCancelled(((Player) entity).isOp());
            }
        }
    }
}
