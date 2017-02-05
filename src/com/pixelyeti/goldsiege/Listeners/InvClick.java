package com.pixelyeti.goldsiege.Listeners;

import com.pixelyeti.goldsiege.GameMechs.*;
import com.pixelyeti.goldsiege.Util.StringUtilities;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
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
                gameName = GameManager.getPlayersGame(e.getWhoClicked().getUniqueId());
                if (gameName == null) {
                    System.out.println("ERROR!");
                }
                for (Team t : Teams.teamsAr) {
                    if (t.getName().equalsIgnoreCase(team)) {
                        if (Teams.getTeam(e.getWhoClicked().getUniqueId()) != null) {
                            Teams.removePlayer(e.getWhoClicked().getUniqueId(), gameName);
                        }
                        Teams.addPlayer(e.getWhoClicked().getUniqueId(), team, gameName);
                        break;
                    }
                }
                e.getCurrentItem().setAmount(e.getCurrentItem().getAmount() + 1);
                e.getWhoClicked().closeInventory();
            }
        } else if (e.getInventory().getName().equalsIgnoreCase(GameGUI.gameGUI.getName())) {
            System.out.println(e.getCurrentItem());
            if (e.getCurrentItem().getType() == Material.STAINED_CLAY) {
                e.setCancelled(true);
                String game = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).substring(5);
                String pGame = GameManager.getPlayersGame(e.getWhoClicked().getUniqueId());
                if (pGame != null) {
                    e.getWhoClicked().sendMessage(StringUtilities.errorMessage + " Already in a game!");
                    return;
                }
                GameManager.addToGame(game, e.getWhoClicked().getUniqueId());
                pGame = GameManager.getPlayersGame(e.getWhoClicked().getUniqueId());
                TeamGUI.setItems(pGame);
                TeamGUI.openInventory(e.getWhoClicked().getUniqueId());
            }
        } else {
            return;
        }
        e.setCancelled(true);
    }
}
