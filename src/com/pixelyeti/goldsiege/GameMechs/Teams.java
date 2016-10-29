package com.pixelyeti.goldsiege.GameMechs;

import com.pixelyeti.goldsiege.Main;
import com.pixelyeti.goldsiege.Util.RandomValue;
import com.pixelyeti.goldsiege.Util.StringUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Callum on 09/06/2015.
 */
public class Teams {

    public static ArrayList<Team> teamsAr = new ArrayList<Team>();
    public static Objective obj;
    public static Scoreboard board;
    public static ScoreboardManager manager = Bukkit.getScoreboardManager();

    public static void creatingTeams(Integer teamAmount, String[] teamNames, boolean spectate, Game g) {
        board = g.board;

        g.obj = board.registerNewObjective("Gold Blocks", "dummy");

        for (Team t : g.teams) {
            t.getPlayers().forEach(t::removePlayer);
        }
        g.teams.clear();

        for (int i = 0; i <= teamAmount - 1; i++) {
            g.teams.add(i, board.registerNewTeam(teamNames[i]));
        }

        if (spectate) {
            g.teams.add(g.teams.size(), board.registerNewTeam("Spectate"));
        }
    }

    public static Team getTeam(UUID uuid) {
        for (Game g : GameManager.getGames()) {
            for (Team t : g.teams) {
                if (t.getPlayers().contains(Bukkit.getPlayer(uuid))) {
                    return t;
                }
            }
        }
        return null;
    }

    public static ArrayList<Team> getTeams() {
        return teamsAr;
    }

    public static void addPlayer(UUID uuid, String team, String gameName) {
        Player p = Bukkit.getPlayer(uuid);
        boolean wasAdded = false;
        for (Game ga : GameManager.getGames()) {
            if (ga.gameName.equalsIgnoreCase(gameName)) {
                for (Team t : ga.teams) {
                    if (team.equalsIgnoreCase(t.getName())) {
                        t.addPlayer(Bukkit.getOfflinePlayer(uuid));
                        wasAdded = true;
                    }
                }
            }
        }
        if (!wasAdded) {
            p.sendMessage(StringUtilities.prefix + ChatColor.RED + "That team does not exist!");
        }
    }

    public static void removePlayer(UUID uuid, String gameName) {
        Player p = Bukkit.getPlayer(uuid);
        for (Team t : teamsAr) {
            if (t.getPlayers().contains(p)) {
                t.removePlayer(p);
            }
        }
    }

    public static void applyRandPrefix() {
        Random rand = new Random();
        for (Team t : teamsAr) {
            t.setPrefix(ChatColor.translateAlternateColorCodes('$', "$" + RandomValue.randomChar(0, 16)));
        }
    }

    public static void sendPlayers(Team t, Location l) {
        for (OfflinePlayer pl : teamsAr.get(teamsAr.indexOf(t)).getPlayers()) {
            Player p = (Player) pl;
            p.teleport(l);
        }
    }

    public static int getTeamSize(String t) {
        for (Team ts : teamsAr) {
            if (t.equalsIgnoreCase(ts.getName())) {
                return ts.getSize();
            }
        }
        return 0;
    }

    public static int getMaxPlayers() {
        int max = 0;
        for (Team t : teamsAr) {
            max += t.getSize();
        }
        return max;
    }

    public static void incrementScore(Team t, String gameName) {
        Game ga = null;
        for (Game g : GameManager.getGames()) {
            if (g.gameName.equalsIgnoreCase(gameName)) {
                ga = g;
            }
        }
        Score score = ga.obj.getScore(t.getPrefix() + t.getName() + ":");
        score.setScore(score.getScore() + 1);
        if (GameManager.checkGameWon(gameName)) {
            GameManager.endGame(gameName);
        }
    }

    public static void loadTeams() {
        int count = 0;
        List<String> te = Main.plugin.getConfigFile().getConfigurationSection("Game").getStringList("Teams");
        for (String s : te) {
            Main.plugin.teams[count] = s;
            count += 1;
        }
    }


}
