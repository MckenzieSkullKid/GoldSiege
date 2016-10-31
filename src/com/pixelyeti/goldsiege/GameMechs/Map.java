package com.pixelyeti.goldsiege.GameMechs;

import com.pixelyeti.goldsiege.Main;
import com.pixelyeti.goldsiege.Util.StringUtilities;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Callum on 11/06/2015.
 */
public class Map {
    public String name;
    public int numTeams;
    public String worldFileName;
    public boolean saving;
    public Location[] spawns;

    public Map(String name, int numTeams, String worldFileName, boolean saving, Location[] spawns) {
        this.name = name;
        this.numTeams = numTeams;
        this.worldFileName = worldFileName;
        this.saving = saving;
        this.spawns = spawns;
    }

    public Map() {
    }

    public Location[] loadSpawns(String name) {
        FileConfiguration config = Main.plugin.getConfigFile();

        int spawnsSize = 0;

        if (config.contains("Maps." + name + ".Spawns")) {
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
                loc = new Location(w, x, y, z, pitch, yaw);
                spawns[count] = loc;

                count++;
            }
            return spawns;
        }
        return null;
    }

    public void addMapSpawn(String name, String teamName, UUID id) {
        Player p = Bukkit.getPlayer(id);

        FileConfiguration config = Main.plugin.getConfigFile();

        config.set("Maps." + name + ".Spawns." + teamName + ".x", p.getLocation().getX());
        config.set("Maps." + name + ".Spawns." + teamName + ".y", p.getLocation().getY());
        config.set("Maps." + name + ".Spawns." + teamName + ".z", p.getLocation().getZ());
        config.set("Maps." + name + ".Spawns." + teamName + ".pitch", p.getLocation().getPitch());
        config.set("Maps." + name + ".Spawns." + teamName + ".yaw", p.getLocation().getYaw());
        Main.plugin.saveConfigFile();

        p.sendMessage(StringUtilities.prefix + ChatColor.GOLD + "Map spawn added correctly!");

    }

    public Location getSpawn(String map, int teamID) {
        for (Map m : MapManager.getMaps()) {
            if (m.name.equalsIgnoreCase(map)) {
                return m.spawns[teamID];
            }
        }
        return null;
    }

    public void teleportToSpawns(String map, Game g) {
        if (!Bukkit.getWorlds().contains(worldFileName)) {
            Bukkit.createWorld(new WorldCreator(worldFileName));
        }
        for (UUID id : g.players) {
            Player p = Bukkit.getPlayer(id);
            Bukkit.createWorld(new WorldCreator(g.map.getWorldFileName() + g.gameName));
            if (Teams.getTeam(id) != null) {
                Location l = getSpawn(map,Teams.teamsAr.indexOf(Teams.getTeam(id)));
                System.out.println(Bukkit.getWorlds());
                l.setWorld(Bukkit.getWorld(g.map.getWorldFileName() + g.gameName));
                System.out.println(l.getWorld().getName());
                p.teleport(l);
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String mapName) {
        name = mapName;
        loadSpawns(name);
    }

    public int getTeamAmount() {
        return numTeams;
    }

    public void setTeamAmount(int amntTeams) { numTeams = amntTeams; }

    public String getWorldFileName() { return worldFileName; }

    public void setWorldFileName(String world) { worldFileName = world; }

    public boolean getSaving() { return saving; }

    public void setSaving(boolean value) { saving = value; }

    public void setSpawns(Location[] spawnLocs) { spawns = spawnLocs; }

}
