package com.pixelyeti.goldsiege.GameMechs;

import com.pixelyeti.goldsiege.GameMechs.Teams;
import com.pixelyeti.goldsiege.Util.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Callum on 09/06/2015.
 */
public class TeamGUI {

    public static Inventory teamGUI;
    private static ArrayList<Team> teams = new ArrayList<>();

    public static void initiate() {
        teamGUI = Bukkit.createInventory(null, 9, "Teams!");
    }

    public static void setItems(String gameName) {
        for (Game g : GameManager.getGames()) {
            if(g.gameName.equalsIgnoreCase(gameName)) {
                teams = g.teams;
            }
        }
        int numSlots = teams.size() / 9;
        teamGUI = Bukkit.createInventory(null, (numSlots + 1) * 9, "Teams!");

        for(int i = 0; i <= (teams.size() - 1); i++) {
            if (!(teams.get(i).getName().equalsIgnoreCase("Spectate"))) {
                teamGUI.setItem(i, ItemStackBuilder.createCustomItemStack(Material.BANNER, "Team " + teams.get(i).getName()
                        , ChatColor.GOLD, teams.get(i).getSize(), 15 - i));
            }
        }
    }

    public static void openInventory(UUID uuid) {
        Player p = Bukkit.getPlayer(uuid);
        //setItems();
        p.openInventory(teamGUI);
    }
}
