package com.pixelyeti.goldsiege.Listeners;

import com.pixelyeti.goldsiege.GameMechs.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawn implements Listener {

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        if (GameManager.getPlayersGame(p.getUniqueId()) != null) {
            Game g = GameManager.getGame(GameManager.getPlayersGame(p.getUniqueId()));
            if (g.gameState == GameState.INGAME) {
                Map m = g.map;
                Location l = m.getSpawn(m.getName(), Teams.teamsAr.indexOf(Teams.getTeam(p.getUniqueId())));
                l.setWorld(Bukkit.getWorld(m.getWorldFileName() + g.gameName));
                e.setRespawnLocation(l);
            }
        }
    }
}
