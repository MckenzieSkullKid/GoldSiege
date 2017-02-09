package com.pixelyeti.goldsiege.Listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

/**
 * Created by Callum on 20/11/2016.
 */
public class EntitySpawn implements Listener {

    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent e) {
        if (e.getEntityType() != EntityType.PIG_ZOMBIE) {
            e.setCancelled(true);
        }
    }
}
