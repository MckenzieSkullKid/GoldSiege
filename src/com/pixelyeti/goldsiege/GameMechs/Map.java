package com.pixelyeti.goldsiege.GameMechs;

import com.pixelyeti.goldsiege.Main;
import com.pixelyeti.goldsiege.Util.StringUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Callum on 11/06/2015.
 */
public class Map {
    public static String name;
    public static int numTeams;
    public static String worldFileName;
    public static boolean saving;
    public static Location[] spawns;

    public Map(String name, int numTeams, String worldFileName, boolean saving, Location[] spawns) {
        this.name = name;
        this.numTeams = numTeams;
        this.worldFileName = worldFileName;
        this.saving = saving;
        this.spawns = spawns;
    }

    public static Location[] loadSpawns(String name) {
        FileConfiguration config = Main.plugin.getConfigFile();

        int spawnsSize = 0;

        for (String s : config.getConfigurationSection("Maps." + name + ".Spawns").getKeys(false)) {
            spawnsSize += 1;
        }

        Location[] spawns = new Location[spawnsSize];

        World w = null;

        int count = 0;
        for (String s : config.getConfigurationSection("Maps." + name + ".Spawns").getKeys(false)) {
            Location loc = null;
            double x = config.getConfigurationSection("Maps." + name + ".Spawns." + s).getDouble("x");
            double y = config.getConfigurationSection("Maps." + name + ".Spawns." + s).getDouble("y");
            double z = config.getConfigurationSection("Maps." + name + ".Spawns." + s).getDouble("z");
            float yaw = config.getConfigurationSection("Maps." + name + ".Spawns." + s).getInt("yaw");
            float pitch = config.getConfigurationSection("Maps." + name + ".Spawns." + s).getInt("pitch");

            w = Bukkit.getServer().getWorld(config.getConfigurationSection("Maps." + name).getString("WorldFileName"));
            loc = new Location(w,x,y,z,pitch,yaw);

            spawns[count] = loc;

            count++;
        }
        return spawns;
    }

    public static void addMapSpawn(String name, String teamName, UUID id) {
        Player p = Bukkit.getPlayer(id);

        FileConfiguration config = Main.plugin.getConfigFile();

        config.set("Maps." + name + ".Spawns." + teamName + ".x", p.getLocation().getX());
        config.set("Maps." + name + ".Spawns." + teamName + ".y", p.getLocation().getY());
        config.set("Maps." + name + ".Spawns." + teamName + ".z", p.getLocation().getZ());
        config.set("Maps." + name + ".Spawns." + teamName + ".pitch", p.getLocation().getPitch());
        config.set("Maps." + name + ".Spawns." + teamName + ".yaw", p.getLocation().getYaw());
        Main.plugin.saveConfig();

        p.sendMessage(StringUtilities.prefix + ChatColor.GOLD + "Map spawn added correctly!");

    }

    public static Location getSpawn(String map, int teamID) {
        for (Map m : MapManager.getMaps()) {
            if (m.name.equalsIgnoreCase(map)) {
                return m.spawns[teamID];
            }
        }
        return null;
    }

    public static void teleportToSpawns(String map, Game g) {
        for (UUID id : g.players) {
            Player p = Bukkit.getPlayer(id);
            if (Teams.getTeam(id) != null) {
                p.teleport(getSpawn(map,Teams.teamsAr.indexOf(Teams.getTeam(id))));
            }
        }
    }

    public static String getName() {
        return name;
    }

    public static int getTeamAmount() {
        return numTeams;
    }

}
