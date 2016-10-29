package com.pixelyeti.goldsiege.Listeners;

import com.pixelyeti.goldsiege.Main;
import com.pixelyeti.goldsiege.Util.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Callum on 10/06/2015.
 */
public class Join implements Listener {

    private static Main plugin;

    public Join(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        p.getInventory().setItem(0, ItemStackBuilder.createCustomItemStack(Material.NETHER_STAR, "Game Selector",
                ChatColor.AQUA, 1));
        p.getInventory().setItem(8, ItemStackBuilder.createCustomItemStack(Material.WATCH, "Hide players",
                ChatColor.GREEN, 1));
        p.getInventory().setItem(4, ItemStackBuilder.createCustomItemStack(Material.BARRIER, "Return to Hub",
                ChatColor.RED, 1));

        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (pl.getPlayerTime() == pl.getWorld().getFullTime() + 1) {
                pl.hidePlayer(p);
            }
        }
    }
}
