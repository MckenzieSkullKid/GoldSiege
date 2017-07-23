package com.pixelyeti.goldsiege.GameMechs;

import com.pixelyeti.goldsiege.Main;
import com.pixelyeti.goldsiege.Util.RandomValue;
import com.pixelyeti.goldsiege.Util.StringUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;
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
            t.getEntries().forEach(t::removeEntry);
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
        for (Game g : GameManager.getGames())
            for (Team t : g.teams)
                if (t.getEntries().contains(Bukkit.getPlayer(uuid).getName()))
                    return t;
        return null;
    }

    public static ArrayList<Team> getTeams() {
        return teamsAr;
    }

    public static void addPlayer(UUID uuid, String team, String gameName) {
        Player p = Bukkit.getPlayer(uuid);
        boolean wasAdded = false;
        for (Game ga : GameManager.getGames())
            if (ga.gameName.equalsIgnoreCase(gameName))
                for (Team t : ga.teams)
                    if (team.equalsIgnoreCase(t.getName())) {
                        t.addEntry(Bukkit.getPlayer(uuid).getName());
                        Bukkit.getPlayer(uuid).setDisplayName(t.getPrefix() + Bukkit.getPlayer(uuid).getName() + ChatColor.RESET);
                        wasAdded = true;
                    }
        if (!wasAdded) {
            p.sendMessage(StringUtilities.prefix + ChatColor.RED + "That team does not exist!");
        }
    }

    public static void removePlayer(UUID uuid, String gameName) {
        Player p = Bukkit.getPlayer(uuid);
        for (Team t : teamsAr) {
            if (t.getEntries().contains(p.getName())) {
                t.removeEntry(p.getName());
                break;
            }
        }
    }

    public static void applyRandPrefix() {
        for (Team t : teamsAr)
            t.setPrefix(ChatColor.translateAlternateColorCodes('$',
                    "$" + RandomValue.randomChar(0, 15)));
    }

    public static void sendPlayers(Team t, Location l) {
        for (String s : teamsAr.get(teamsAr.indexOf(t)).getEntries()) {
            Player p = Bukkit.getPlayer(s);
            p.teleport(l);
        }
    }

    public static int getTeamSize(String t) {
        for (Team ts : teamsAr)
            if (t.equalsIgnoreCase(ts.getName()))
                return ts.getSize();
        return 0;
    }

    public static int getMaxPlayers() {
        int max = 0;
        for (Team t : teamsAr)
            max += t.getSize();
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
        List<String> te = Main.getInstance().getConfig().getConfigurationSection("Game").getStringList("Teams");
        for (String s : te) {
            Main.getInstance().teams[count] = s;
            count += 1;
        }
    }


}
