package com.pixelyeti.goldsiege.Util;

import com.connorlinfoot.titleapi.TitleAPI;
import com.pixelyeti.goldsiege.GameMechs.*;
import com.pixelyeti.goldsiege.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Callum on 11/06/2015.
 */
public class Countdown{

    private static Main plugin;

    public Countdown(Main plugin) {
        this.plugin = plugin;
    }

    static int taskID;
    static int taskID2;
    public static int SecondsToCountDown;
    public static Map m;

    private static Game g;

    public static Runnable startCountdown(final String gameName) {

        for (Game ga : GameManager.getGames()) {
            if (ga.gameName.equalsIgnoreCase(gameName)) {
                g = ga;
            }
        }

        taskID = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if(g.players.size() >= Main.plugin.getConfigFile().getInt("Game.MinPlayers")) {
                if (plugin.getConfig().contains("Game.WaitTimeMin")) {
                    SecondsToCountDown = plugin.getConfig().getInt("Game.WaitTimeMin");
                } else {
                    SecondsToCountDown = 120;
                }

                if (!(Main.plugin.getConfig().getBoolean("Game.ChooseMapBefore"))) {
                    m = MapManager.selectMap();
                    g.map = m;
                } else {
                    m = g.map;
                }

                SecondsToCountDown--;
                if (SecondsToCountDown != 0) {
                    if (SecondsToCountDown == 20) {
                        for (UUID id : g.players) {
                            Player p = Bukkit.getPlayer(id);
                            p.sendMessage(StringUtilities.prefix + ChatColor.AQUA + "The chosen map is: " + m.getName());
                        }
                    } else if (SecondsToCountDown == 10) {
                        Map m = new Map();
                        m.teleportToSpawns(m.getName(), g);
                    }

                    for (UUID id : g.players) {
                        Player p = Bukkit.getPlayer(id);
                        if (SecondsToCountDown <= 3) {
                            TitleAPI.sendTitle(p, 5, 5, 10, ChatColor.RED + "" + SecondsToCountDown + ChatColor.GOLD +
                                    " second(s) left until game starts!", null);
                        } else if (SecondsToCountDown <= 5 && SecondsToCountDown > 3) {
                            TitleAPI.sendTitle(p, 5, 5, 10, ChatColor.GREEN + "" + SecondsToCountDown + ChatColor.GOLD +
                                    " second(s) left until game starts!", null);
                        }
                        p.setLevel(SecondsToCountDown);
                    }
                }

                if (SecondsToCountDown == 0) {
                    plugin.getServer().getScheduler().cancelTask(taskID);
                    for (UUID id : g.players) {
                        Player p = Bukkit.getPlayer(id);
                        TitleAPI.sendTitle(p, 5, 5, 10, ChatColor.GOLD +
                                "Game Started!", null);
                        p.setLevel(0);
                    }
                    g.gameState = GameState.INGAME;

                }
            }
        }, 20L, 20L);
        return null;
    }
}
