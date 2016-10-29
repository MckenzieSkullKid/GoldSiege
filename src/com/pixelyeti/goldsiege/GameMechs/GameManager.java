package com.pixelyeti.goldsiege.GameMechs;

import com.connorlinfoot.titleapi.TitleAPI;
import com.pixelyeti.goldsiege.Main;
import com.pixelyeti.goldsiege.Util.Countdown;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.UUID;

/**
 * Created by Callum on 22/06/2015.
 */
public class GameManager {

    private static int AmountGames;

    private static Game[] games;

    public static Game[] getGames() { return games; }

    public static void createGames() {
        AmountGames  = Main.plugin.getConfigFile().getConfigurationSection("Game").getInt("AmountGames");
        games = new Game[AmountGames];

        ConfigurationSection gameSection = Main.plugin.getConfigFile().getConfigurationSection("Game");

        int minPlayers = gameSection.getInt("MinPlayers");
        String gamePrefix = gameSection.getString("Prefix");

        Teams.loadTeams();
        System.out.println(games.length);
        int gamesSize = 0;
        for (Game g : games) {
            if(g != null) {
                gamesSize += 1;
            }
        }
        System.out.println(gamesSize);
        if (gamesSize < AmountGames) {
            int remainderGames = AmountGames - gamesSize;
            System.out.println("Remain: " + remainderGames);
            int minValue = 0;
            if(gamesSize - 1 > 0 ) {
                minValue = gamesSize -1;
            }
            for (int i = minValue; i <= remainderGames +
                    (minValue - 1); i++) {
                games[i] = new Game(minPlayers, gamePrefix + i, GameState.WAITING, Teams.teamsAr);
                System.out.println("Debugging: " + games[i].gameName);

                Teams.creatingTeams(Main.plugin.teams.length, Main.plugin.teams, true, games[i]);

                if (Main.plugin.getConfigFile().getBoolean("Game.ChooseMapBefore")) {
                    games[i].map = MapManager.selectMap();
                }

            }
        }

    }

    public static void addToGame(String gameName, UUID id) {
        int gameSize = 0;
        for (Game g : getGames()) {
            if (g.gameName.equalsIgnoreCase(gameName)) {
                for(UUID uuid : g.players) {
                    if (uuid != null) {
                        gameSize += 1;
                    }
                }
                System.out.println(gameSize);
                g.players[gameSize] = id;
            }
        }
    }

    public static void startGame(String gameName) {
        Countdown.startCountdown(gameName);
    }

    public static boolean checkGameWon(String gameName) {
        Game ga = null;
        for (Game g : getGames()) {
            if (g.gameName.equalsIgnoreCase(gameName)) {
                g = ga;
            }
        }

        for (Team t : ga.teams) {

        }

        for (Team t : Teams.teamsAr) {
            if(Teams.obj.getScore(t.getPrefix() + t.getName() + ":").getScore() == 15) {
                ga.winningTeam = t;
                return true;
            }
        }
        return false;
    }

    public static void endGame(String gameName) {
        Game ga = null;
        for (Game g : GameManager.getGames()) {
            if (g.gameName.equalsIgnoreCase(gameName)) {
                ga = g;
            }
        }
        for (UUID id : ga.players) {
            Player pl = Bukkit.getPlayer(id);
            String status = "";
            status = Teams.getTeam(id) == ga.winningTeam ? "Winner" : "Loser";
            TitleAPI.sendTitle(pl, 5, 30, 5, ChatColor.GREEN + status + "!", ChatColor.AQUA + "Team " +
                    ChatColor.GREEN + ga.winningTeam.getName() + ChatColor.AQUA + " Won!");
            pl.setGameMode(GameMode.SPECTATOR);
        }
        for(OfflinePlayer op : ga.winningTeam.getPlayers()) {
            Player p = Bukkit.getPlayer(op.getName());

            p.getLocation().getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
        }
    }
}
