package com.pixelyeti.goldsiege.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Callum on 01/07/2017.
 */
public class PlayerChat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        event.setFormat(event.getPlayer().getDisplayName()
                + ChatColor.WHITE + " >> " + ChatColor.RESET + event.getMessage());
    }

}
