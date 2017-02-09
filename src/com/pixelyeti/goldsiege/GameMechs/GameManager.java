package com.pixelyeti.goldsiege.GameMechs;

import com.connorlinfoot.titleapi.TitleAPI;
import com.pixelyeti.goldsiege.Main;
import com.pixelyeti.goldsiege.Util.Countdown;
import com.pixelyeti.goldsiege.Util.ItemStackBuilder;
import com.pixelyeti.goldsiege.Util.ServerUtils;
import com.pixelyeti.goldsiege.Util.StringUtilities;
import org.bukkit.*;
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

    public static Game getGame(String gameName) {
        for (Game g : getGames()) {
            if (g.gameName.equalsIgnoreCase(gameName)) {
                return g;
            }
        }
        return null;
    }

    public static void createGames() {
        AmountGames  = Main.getInstance().getConfig().getConfigurationSection("Game").getInt("AmountGames");
        games = new Game[AmountGames];

        ConfigurationSection gameSection = Main.getInstance().getConfig().getConfigurationSection("Game");

        int minPlayers = gameSection.getInt("MinPlayers");
        String gamePrefix = gameSection.getString("Prefix");

        Teams.loadTeams();
        int gamesSize = 0;
        for (Game g : games) {
            if(g != null) {
                gamesSize += 1;
            }
        }
        if (gamesSize < AmountGames) {
            int remainderGames = AmountGames - gamesSize;
            int minValue = 0;
            if(gamesSize - 1 > 0 ) {
                minValue = gamesSize -1;
            }
            for (int i = minValue; i <= remainderGames +
                    (minValue - 1); i++) {
                games[i] = new Game(minPlayers, gamePrefix + i, GameState.WAITING, Teams.teamsAr);
                Teams.creatingTeams(Main.instance.teams.length, Main.instance.teams, true, games[i]);

                if (Main.getInstance().getConfig().getBoolean("Game.ChooseMapBefore")) {
                    games[i].map = MapManager.selectMap();
                }

            }
        }

    }

    public static void addToGame(String gameName, UUID id) {
        int gameSize = 0;
        Player p = Bukkit.getPlayer(id);
        for (Game g : getGames()) {
            if (g.gameName.equalsIgnoreCase(gameName)) {
                if (g.players != null) {
                    for (UUID uuid : g.players) {
                        if (uuid != null) {
                            gameSize += 1;
                        }
                    }
                }
                if (gameSize < 16) {
                    g.players.add(id);
                    p.getInventory().remove(ItemStackBuilder.createCustomItemStack(Material.NETHER_STAR, "Game Selector",
                            ChatColor.AQUA, 1));
                    p.getInventory().addItem(ItemStackBuilder.createCustomItemStack(Material.END_CRYSTAL, "Team Selector",
                            ChatColor.LIGHT_PURPLE, 1));
                } else {
                    p.sendMessage(StringUtilities.prefix + ChatColor.RED + " This game is full!");
                }

                if (gameSize >= g.minPlayers && gameSize != 16) {
                    new Countdown(g, 120, 30, 20, 10, 5, 4, 3, 2, 1).runTaskTimer(Main.instance, 0, 1000);
                } else if (gameSize == 16) {
                    new Countdown(g, 30, 30, 20, 10, 5, 4, 3, 2, 1).runTaskTimer(Main.instance, 0, 1000);
                }
            }
        }
    }

    public static void removeFromGame(UUID id) {
        Teams.removePlayer(id, GameManager.getPlayersGame(id));
        Game g = getGame(getPlayersGame(id));
        if(g != null) {
            for (int i = 0; i <= g.players.size() - 1; i++) {
                if (g.players.get(i) == id) {
                    g.players.remove(i);
                    break;
                }
            }
        }
    }

    public static String getPlayersGame(UUID p) {
        for (Game g : getGames()) {
            if (g.players == null) {
                return null;
            }
            for (UUID id : g.players) {
                if (id == p) {
                    return g.gameName;
                }
            }
        }
        return null;
    }

    public static void startGameCountdownTimer(String gameName) {

    }

    public static void startGame(String gameName) {
        new Countdown(GameManager.getGame(gameName), Main.getInstance().getConfig().getInt("Game.WaitTimeMin"),
                Main.getInstance().getConfig().getInt("Game.TeleportTime"),30, 20, 10, 5, 4, 3, 2, 1)
                .runTaskTimer(Main.getInstance(), 0, 20);
        Game g = getGame(gameName);
        g.gameState = GameState.INGAME;
    }


    // TODO: Check game won conditions
    public static boolean checkGameWon(String gameName) {
        Game ga = null;
        for (Game g : getGames()) {
            if (g.gameName.equalsIgnoreCase(gameName)) {
                ga = g;
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
        if (ga.winningTeam == null)
            return;
        for (UUID id : ga.players) {
            Player pl = Bukkit.getPlayer(id);
            String status = (Teams.getTeam(id) == ga.winningTeam) ? "Winner" : "Loser";
            TitleAPI.sendTitle(pl, 5, 30, 5, ChatColor.GREEN + status + "!", ChatColor.AQUA + "Team " +
                    ChatColor.GREEN + ga.winningTeam.getName() + ChatColor.AQUA + " Won!");
            pl.setGameMode(GameMode.SPECTATOR);
        }
        for(OfflinePlayer op : ga.winningTeam.getPlayers()) {
            Player p = Bukkit.getPlayer(op.getName());

            p.getLocation().getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
        }
        Game finalGa = ga;
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
            for (UUID id : finalGa.players) {
                GameManager.removeFromGame(id);
                Bukkit.getPlayer(id).teleport(ServerUtils.getServerSpawn());
            }
        }, 400L);
    }
}
