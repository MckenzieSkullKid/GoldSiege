package com.pixelyeti.goldsiege.Listeners;

import com.pixelyeti.goldsiege.GameMechs.Teams;
import com.pixelyeti.goldsiege.Util.TeamGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.scoreboard.Team;

/**
 * Created by Callum on 11/06/2015.
 */
public class InvClick implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getInventory().getName().equalsIgnoreCase(TeamGUI.teamGUI.getName())) {
            if (!(e.getCurrentItem().equals(null))) {
                String team = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).substring(5);
                for (Team t : Teams.teamsAr) {
                    if (t.getName().equalsIgnoreCase(team)) {
                        Teams.addPlayer(e.getWhoClicked().getUniqueId(), team);
                    }
                }
                e.getCurrentItem().setAmount(e.getCurrentItem().getAmount() + 1);
            }
        }
        e.setCancelled(true);
    }
}
