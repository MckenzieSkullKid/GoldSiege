package com.pixelyeti.goldsiege.Listeners;

import com.pixelyeti.goldsiege.GameMechs.GameManager;
import com.pixelyeti.goldsiege.GameMechs.Teams;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Callum on 29/10/2016.
 */
public class PlayerLeave implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        GameManager.removeFromGame(e.getPlayer().getUniqueId());
    }

}
