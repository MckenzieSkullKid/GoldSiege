package com.pixelyeti.goldsiege.Listeners;

import com.pixelyeti.goldsiege.GameMechs.*;
import com.pixelyeti.goldsiege.Util.StringUtilities;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.scoreboard.Team;

import java.util.UUID;

/**
 * Created by Callum on 11/06/2015.
 */
public class InvClick implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
            return;
        }
        if (e.getInventory().getName().equalsIgnoreCase(TeamGUI.teamGUI.getName())) {
            if (e.getCurrentItem().getType() == Material.BANNER) {
                e.setCancelled(true);
                String team = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).substring(5);
                System.out.println(team);
                String gameName = null;
                for (Game g : GameManager.getGames()) {
                    for (UUID id : g.players) {
                        if (id == e.getWhoClicked().getUniqueId()) {
                            gameName = g.gameName;
                        }
                    }
                }
                if (gameName == null) {
                    System.out.println("ERROR!");
                }
                for (Team t : Teams.teamsAr) {
                    if (t.getName().equalsIgnoreCase(team)) {
                        Teams.addPlayer(e.getWhoClicked().getUniqueId(), team, gameName);
                    }
                }
                e.getCurrentItem().setAmount(e.getCurrentItem().getAmount() + 1);
            }
        } else if (e.getInventory().getName().equalsIgnoreCase(GameGUI.gameGUI.getName())) {
            System.out.println(e.getCurrentItem());
            if (e.getCurrentItem().getType() == Material.HARD_CLAY) {
                e.setCancelled(true);
                String game = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).substring(5);
                for (Game g : GameManager.getGames()) {
                    if (g.gameName.equalsIgnoreCase(game)) {
                        for (UUID id : g.players) {
                            if (id == e.getWhoClicked().getUniqueId()) {
                                e.getWhoClicked().sendMessage(StringUtilities.errorMessage + " Already in a game!");
                                return;
                            }
                        }
                        GameManager.addToGame(g.gameName, e.getWhoClicked().getUniqueId());
                        TeamGUI.setItems(g.gameName);
                        TeamGUI.openInventory(e.getWhoClicked().getUniqueId());
                    }
                }
            }
        } else {
            return;
        }
        e.setCancelled(true);
    }
}
