package com.pixelyeti.goldsiege.Util;

import com.pixelyeti.goldsiege.GameMechs.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

/**
 * Created by Callum on 30/10/2016.
 */
public class GameTimer extends BukkitRunnable {

    private Game g;
    private int gameTimer;

    public GameTimer(Game g, int gameTimer) {
        this.g = g;
        this.gameTimer = gameTimer;
    }

    @Override
    public void run() {
        if (gameTimer <= 5 && gameTimer != 0) {
            for (UUID id : g.players) {
                Player p = Bukkit.getPlayer(id);
                p.sendMessage(StringUtilities.prefix + ChatColor.RED + "Game ending in " + gameTimer + " seconds!");
            }
        } else if (gameTimer == 0) {

        }

        if (gameTimer % 60 == 0) {
            int minute = gameTimer/60;
            for (UUID id : g.players) {
                Player p = Bukkit.getPlayer(id);
                p.sendMessage(StringUtilities.prefix + ChatColor.RED + "Game ending in " + minute + " minutes!");
            }
        }
        gameTimer--;
    }
}
