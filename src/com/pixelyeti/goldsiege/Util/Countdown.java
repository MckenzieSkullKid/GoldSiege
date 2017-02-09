package com.pixelyeti.goldsiege.Util;

import com.connorlinfoot.titleapi.TitleAPI;
import com.pixelyeti.goldsiege.GameMechs.Game;
import com.pixelyeti.goldsiege.GameMechs.Map;
import com.pixelyeti.goldsiege.GameMechs.MapManager;
import com.pixelyeti.goldsiege.GameMechs.StartingItems;
import com.pixelyeti.goldsiege.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Callum on 30/10/2016.
 */
public class Countdown extends BukkitRunnable {

    private Game g;
    private int i;
    private int tpTime;
    private int startTime;
    private ArrayList<Integer> countingNums;

    private Map m;

    public Countdown(Game g, int start, int tpTime, int... cNums) {
        this.g = g;
        this.i = start;
        this.startTime = start;
        this.tpTime = tpTime;
        this.countingNums = new ArrayList<>();

        for (int c : cNums) {
            countingNums.add(c);
        }
    }

    @Override
    public void run() {
        for (UUID id : g.players) {
            Player p = Bukkit.getPlayer(id);
            p.setLevel(i);
        }

        if (i == startTime) {
            if (!(Main.instance.getConfig().getBoolean("Game.ChooseMapBefore"))) {
                m = MapManager.selectMap();
                g.map = m;
            } else {
                m = g.map;
            }
            FileHandler.copyWorld(Bukkit.getWorld(g.map.getWorldFileName()), g.map.getWorldFileName() + g.gameName);
            for (UUID id : g.players) {
                Player p = Bukkit.getPlayer(id);
                p.sendMessage(StringUtilities.prefix + ChatColor.AQUA + "The chosen map is: " + ChatColor.GOLD +
                        m.getName());
            }
        } else if (i == tpTime) {
            Map m = g.map;
            m.teleportToSpawns(m.getName(), g);
            for (UUID id : g.players) {
                StartingItems.get(id);
                Bukkit.getPlayer(id).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 10));
                Bukkit.getPlayer(id).sendMessage(StringUtilities.prefix + ChatColor.GREEN + "Time remaining " + i);
            }
        } else if (i == 0) {
            for (UUID id : g.players) {
                Player p = Bukkit.getPlayer(id);
                TitleAPI.sendTitle(p, 5, 10, 5, ChatColor.GOLD + "Go!",
                        ChatColor.AQUA + "Kill all of the pigmen!");
            }
            cancel();
            return;
        }

        if (countingNums.contains(i)) {
            for (UUID id : g.players) {
                Player p = Bukkit.getPlayer(id);
                p.sendMessage(StringUtilities.prefix + ChatColor.GREEN + "Time remaining " + i);
                if (i > 3) {
                    TitleAPI.sendTitle(p, 5, 10, 5, ChatColor.GREEN + "" + i, null);
                } else {
                    TitleAPI.sendTitle(p, 5, 10, 5, ChatColor.RED + "" + i, null);
                }
            }
        }
        i--;
    }
}