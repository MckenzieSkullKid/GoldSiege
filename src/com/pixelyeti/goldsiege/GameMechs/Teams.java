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

import java.util.*;

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

        obj = board.registerNewObjective("Gold Blocks", "dummy");

        for(Team t : teamsAr) {
            for(OfflinePlayer p : t.getPlayers()) {
                t.removePlayer(p);
            }
        }
        teamsAr.clear();

        for(int i=0; i <= teamAmount - 1; i++) {
            teamsAr.add(i, board.registerNewTeam(teamNames[i]));
        }

        if (spectate) {
            teamsAr.add(teamsAr.size(), board.registerNewTeam("Spectate"));
        }
    }

    public static Team getTeam(UUID uuid) {
        for(Team t : teamsAr) {
            if (t.getPlayers().contains(Bukkit.getPlayer(uuid))) {
                return t;
            }
        }
        return null;
    }

    public static ArrayList<Team> getTeams() {
        return teamsAr;
    }

    public static void addPlayer(UUID uuid, String team) {
        Player p = Bukkit.getPlayer(uuid);
        boolean wasAdded = false;
        for(Team t : teamsAr) {
            if(team.equalsIgnoreCase(t.getName())) {
                t.addPlayer(Bukkit.getOfflinePlayer(uuid));
                wasAdded = true;
            }
        }
        if (!wasAdded) {
            p.sendMessage(StringUtilities.prefix + ChatColor.RED + "That team does not exist!");
        }
    }

    public static void removePlayer(UUID uuid) {
        Player p = Bukkit.getPlayer(uuid);
        for(Team t : teamsAr) {
            if(t.getPlayers().contains(p)) {
                t.removePlayer(p);
            }
        }
    }

    public static void applyRandPrefix() {
        Random rand = new Random();
        for(Team t : teamsAr) {
            t.setPrefix(ChatColor.translateAlternateColorCodes('$', "$" + RandomValue.randomChar(0,16)));
        }
    }

    public static void sendPlayers(Team t, Location l) {
        for (OfflinePlayer pl : teamsAr.get(teamsAr.indexOf(t)).getPlayers()) {
            Player p = (Player) pl;
            p.teleport(l);
        }
    }

    public static int getTeamSize(String t) {
        for(Team ts : teamsAr) {
            if (t.equalsIgnoreCase(ts.getName())) {
                return ts.getSize();
            }
        }
        return 0;
    }

    public static int getMaxPlayers() {
        int max = 0;
        for(Team t : teamsAr) {
            max += t.getSize();
        }
        return max;
    }

    public static void incrementScore(Team t) {
        Score score = obj.getScore(t.getPrefix() + t.getName() + ":");
        score.setScore(score.getScore() + 1);
        if (Game.checkGameWon()) {
            Game.endGame();
        }
    }

    public static void applyNameTag() {
        for (UUID id : Game.players) {
            Player p = Bukkit.getPlayer(id);


        }
    }

    public static void loadTeams() {
        int count = 0;
        List<String> te = Main.plugin.getConfig().getConfigurationSection("Game").getStringList("Teams");
        for(String s : te) {
            Main.plugin.teams[count] = s;
            count += 1;
        }
    }



}
