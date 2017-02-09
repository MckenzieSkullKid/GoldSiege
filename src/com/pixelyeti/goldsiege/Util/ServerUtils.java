package com.pixelyeti.goldsiege.Util;

import com.pixelyeti.goldsiege.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Created by Callum on 09/02/2017.
 */
public class ServerUtils {

    public static Location getServerSpawn() {
        ConfigurationSection serverSection = Main.getInstance().getConfig().getConfigurationSection("Server");
        Location l = null;
        l.setX((Double) serverSection.get("Spawn.X"));
        l.setY((Double) serverSection.get("Spawn.Y"));
        l.setZ((Double) serverSection.get("Spawn.Z"));
        l.setYaw((float) serverSection.get("Spawn.Yaw"));
        l.setPitch((float) serverSection.get("Spawn.Pitch"));
        l.setWorld(Bukkit.getWorld(serverSection.getString("Spawn.World")));

        return l;
    }

}
