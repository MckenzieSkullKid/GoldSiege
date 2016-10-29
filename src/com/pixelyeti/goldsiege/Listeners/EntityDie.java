package com.pixelyeti.goldsiege.Listeners;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 * Created by Callum on 09/01/2016.
 */
public class EntityDie implements Listener {

    Random rand = new Random();
    int min = 1;
    int max = 3;

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntityType() == EntityType.PIG_ZOMBIE) {

            int randAmnt = rand.nextInt((max - min) + 1) + min;

            e.getDrops().clear();
            e.getDrops().add(new ItemStack(Material.BEACON, randAmnt));
        }
    }

}
