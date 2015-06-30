package com.pixelyeti.goldsiege.Listeners;

import com.pixelyeti.goldsiege.GameMechs.Teams;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kitteh.tag.AsyncPlayerReceiveNameTagEvent;

/**
 * Created by Callum on 12/06/2015.
 */
public class NameTag implements Listener {

    @EventHandler
    public void onNameTag(AsyncPlayerReceiveNameTagEvent e) {
        e.setTag(Teams.getTeam(e.getPlayer().getUniqueId()).getPrefix() + e.getPlayer().getDisplayName());
    }

}
