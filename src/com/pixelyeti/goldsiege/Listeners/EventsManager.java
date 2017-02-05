package com.pixelyeti.goldsiege.Listeners;

import com.pixelyeti.goldsiege.Main;
import org.bukkit.plugin.PluginManager;

/**
 * Created by Callum on 22/06/2015.
 */
public class EventsManager {

    private static Main plugin;

    public EventsManager(Main plugin) {
        this.plugin = plugin;
    }

    public static void registerEvents(PluginManager pm) {
        pm.registerEvents(new EntityDie(), plugin);
        pm.registerEvents(new EntitySpawn(), plugin);
        pm.registerEvents(new InvClick(), plugin);
        pm.registerEvents(new ItemDrop(), plugin);
        pm.registerEvents(new Join(plugin), plugin);
        pm.registerEvents(new PlayerInteract(), plugin);
        pm.registerEvents(new PlayerLeave(), plugin);
        pm.registerEvents(new WeatherChange(), plugin);

    }
}
