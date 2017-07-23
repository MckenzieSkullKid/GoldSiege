package com.pixelyeti.goldsiege.Listeners;

import com.pixelyeti.goldsiege.Main;
import org.bukkit.plugin.PluginManager;

/**
 * Created by Callum on 22/06/2015.
 */
public class EventsManager {

    public static void registerEvents(PluginManager pm) {
        pm.registerEvents(new EntityDie(), Main.getInstance());
        pm.registerEvents(new EntitySpawn(), Main.getInstance());
        pm.registerEvents(new InvClick(), Main.getInstance());
        pm.registerEvents(new ItemDrop(), Main.getInstance());
        pm.registerEvents(new Join(Main.getInstance()), Main.getInstance());
        pm.registerEvents(new PlayerInteract(), Main.getInstance());
        pm.registerEvents(new PlayerLeave(), Main.getInstance());
        pm.registerEvents(new WeatherChange(), Main.getInstance());
        pm.registerEvents(new PlayerRespawn(), Main.getInstance());
        pm.registerEvents(new PlayerChat(), Main.getInstance());
    }
}
