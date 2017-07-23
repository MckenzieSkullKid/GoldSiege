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
        Location l = new Location(null, 0d, 0d,0d, 0f, 0f);
        l.setX((double) serverSection.get("Spawn.X"));
        l.setY((double) serverSection.get("Spawn.Y"));
        l.setZ((double) serverSection.get("Spawn.Z"));
        l.setYaw((float) serverSection.getDouble("Spawn.Yaw"));
        l.setPitch((float) serverSection.getDouble("Spawn.Pitch"));
        l.setWorld(Bukkit.getWorld(serverSection.getString("Spawn.World")));

        return l;
    }

}
