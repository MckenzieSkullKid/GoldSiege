package com.pixelyeti.goldsiege.GameMechs;

import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Callum on 11/06/2015.
 */
public class Game {

    public int minPlayers;
    public String gameName;
    public GameState gameState;
    public ArrayList<Team> teams;
    public UUID[] players;
    public Map map;
    public Scoreboard board;
    public Objective obj;
    public Team winningTeam;

    public Game(int minPlayers, String gameName, GameState gameState, ArrayList<Team> teams) {
        this.minPlayers = minPlayers;
        this.gameName = gameName;
        this.gameState = gameState;
        this.teams = teams;
        this.board = Teams.manager.getNewScoreboard();
    }




}
