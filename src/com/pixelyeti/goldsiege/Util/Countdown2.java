package com.pixelyeti.goldsiege.Util;

import com.connorlinfoot.titleapi.TitleAPI;
import com.pixelyeti.goldsiege.GameMechs.Game;
import com.pixelyeti.goldsiege.GameMechs.Map;
import com.pixelyeti.goldsiege.GameMechs.MapManager;
import com.pixelyeti.goldsiege.Main;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Callum on 30/10/2016.
 */
public class Countdown2 extends BukkitRunnable {

    private Game g;
    private int i;
    private int tpTime;
    private int startTime;
    private ArrayList<Integer> countingNums;

    private Map m;

    public Countdown2(Game g, int start, int... cNums) {
        this.g = g;
        this.i = start;
        this.startTime = start;
        this.countingNums = new ArrayList<Integer>();

        for (int c : cNums) {
            countingNums.add(c);
        }
    }

    @Override
    public void run() {
        for (UUID id : g.players) {
            Player p = Bukkit.getPlayer(id);
            p.setLevel(i);
            p.sendMessage(StringUtilities.prefix + ChatColor.GREEN + "Time remaining " + i);
        }

        if (i == startTime) {
            if (!(Main.plugin.getConfig().getBoolean("Game.ChooseMapBefore"))) {
                m = MapManager.selectMap();
                g.map = m;
            } else {
                m = g.map;
            }
            FileHandler.copyWorld(Bukkit.getWorld(g.map.getWorldFileName()), g.map.getWorldFileName() + g.gameName);
            for(UUID id : g.players) {
                Player p = Bukkit.getPlayer(id);
                p.sendMessage(StringUtilities.prefix + ChatColor.AQUA + "The chosen map is: " + ChatColor.GOLD +
                        m.getName());
            }
        } else if (i == 0) {
            for (UUID id : g.players) {
                Player p = Bukkit.getPlayer(id);
                TitleAPI.sendTitle(p, 5, 5, 10, ChatColor.GOLD + "Game Started!", null);
                Map m = new Map();
                m.teleportToSpawns(m.getName(), g);
            }

            cancel();
            return;
        }

        if (countingNums.contains(i)) {
            for (UUID id : g.players) {
                Player p = Bukkit.getPlayer(id);
                TitleAPI.sendTitle(p, 5, 5, 10, ChatColor.GREEN + "", null);
            }
        }
        i--;
    }
}