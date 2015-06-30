package com.pixelyeti.goldsiege.Util;

import com.pixelyeti.goldsiege.GameMechs.Teams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

/**
 * Created by Callum on 09/06/2015.
 */
public class TeamGUI implements Listener {

    public static Inventory teamGUI;

    public static void initiate() {
        teamGUI = Bukkit.createInventory(null, 9, "Teams!");
    }

    public static void setItems() {
        int numSlots = Teams.teamsAr.size() / 9;
        teamGUI = Bukkit.createInventory(null, (numSlots + 1) * 9, "Teams!");

        for(int i = 0; i <= (Teams.teamsAr.size() - 1); i++) {
            if (!(Teams.teamsAr.get(i).getName().equalsIgnoreCase("Spectate"))) {
                teamGUI.setItem(i, ItemStackBuilder.createCustomItemStack(Material.BANNER, "Teams " + Teams.teamsAr.get(i).getName()
                        , ChatColor.GOLD, Teams.teamsAr.get(i).getSize(), 15 - i));
            }
        }
    }

    public static void openInventory(UUID uuid) {
        Player p = Bukkit.getPlayer(uuid);
        setItems();
        p.openInventory(teamGUI);
    }
}
