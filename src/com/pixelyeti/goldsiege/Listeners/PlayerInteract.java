package com.pixelyeti.goldsiege.Listeners;

import com.pixelyeti.goldsiege.GameMechs.GameGUI;
import com.pixelyeti.goldsiege.GameMechs.GameManager;
import com.pixelyeti.goldsiege.GameMechs.TeamGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Set;

public class PlayerInteract implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.WATCH) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR
                    || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (player.getPlayerTime() == player.getWorld().getFullTime()) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        player.hidePlayer(p);
                    }
                    player.setPlayerTime(player.getWorld().getFullTime() + 1, true);
                    player.sendMessage(ChatColor.GOLD
                            + "Players are now invisible");
                } else {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        player.showPlayer(p);
                    }
                    player.resetPlayerTime();
                    player.sendMessage(ChatColor.GOLD
                            + "Players are now visible");
                }
            }
        } else if (player.getInventory().getItemInMainHand().getType() == Material.NETHER_STAR) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR
                    || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                GameGUI.openInventory(player.getUniqueId());
            }
        } else if (player.getInventory().getItemInMainHand().getType() == Material.END_CRYSTAL) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR
                    || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                TeamGUI.setItems(GameManager.getPlayersGame(player.getUniqueId()));
                TeamGUI.openInventory(player.getUniqueId());
            }
        } else if (player.getInventory().getItemInMainHand().getType() == Material.CARROT_STICK) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR
                    || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                player.getWorld().strikeLightning(player.getTargetBlock((Set<Material>)null, 100).getLocation());
            }
        } else if (player.getInventory().getItemInMainHand().getType() == Material.GOLD_SPADE) {
            if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                event.setCancelled(true);

                for (int x = event.getClickedBlock().getX() - 1;x <= event.getClickedBlock().getX()  + 1; x++) {
                    for (int y = event.getClickedBlock().getY() + 1; y <= event.getClickedBlock().getY() + 1; y++) {
                        for (int z = event.getClickedBlock().getZ() - 1; z <= event.getClickedBlock().getZ() + 1; z++) {
                            Location location = new Location(player.getWorld(), x, y, z);
                            location.getBlock().setType(Material.DIAMOND_BLOCK);
                        }
                    }
                }
            }
        }
    }
}
