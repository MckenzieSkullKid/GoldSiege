package com.pixelyeti.goldsiege.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

/**
 * Created by Callum on 20/11/2016.
 */
public class ItemDrop implements Listener {

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        if (e.getPlayer().getLocation().getWorld().getName().equalsIgnoreCase("world")) {
            e.setCancelled(true);
        }
    }

}
