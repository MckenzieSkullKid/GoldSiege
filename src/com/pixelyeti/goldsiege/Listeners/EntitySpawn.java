package com.pixelyeti.goldsiege.Listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

/**
 * Created by Callum on 20/11/2016.
 */
public class EntitySpawn implements Listener {

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        if (e.getLocation().getWorld().getName().equalsIgnoreCase("world")) {
            if (e.getEntityType() == EntityType.SKELETON || e.getEntityType() == EntityType.ZOMBIE
                    || e.getEntityType() == EntityType.CREEPER || e.getEntityType() == EntityType.WITCH
                    || e.getEntityType() == EntityType.SPIDER) {
                e.setCancelled(true);
            }
        }
    }
}
