package com.pixelyeti.goldsiege.GameMechs;

import com.pixelyeti.goldsiege.Main;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Callum on 11/06/2015.
 */
public class MapManager {

    public static ArrayList<Map> maps = new ArrayList<>();

    public static final void initiateMaps() {
        FileConfiguration config = Main.getInstance().getConfig();
        ConfigurationSection configSection = config.getConfigurationSection("Maps");

        for (String s : configSection.getKeys(false)) {
            Map m = new Map();

            m.setName(config.getString("Maps." + s + ".Name"));
            m.setTeamAmount(config.getInt("Maps." + s + ".NumTeams"));
            m.setWorldFileName(config.getString("Maps." + s + ".WorldFileName"));
            m.setSaving(config.getBoolean("Maps." + s + ".Saving"));
            m.spawns = m.loadSpawns(m.getWorldFileName());

            maps.add(m);

            Bukkit.createWorld(new WorldCreator(m.getWorldFileName()));
        }
    }

    public static Map selectMap() {
        Map map = null;
        Random rand = new Random();

        int randNum = rand.nextInt(((maps.size() - 1)) + 1);
        map = maps.get(randNum);

        return map;
    }

    public static ArrayList<Map> getMaps() {
        return maps;
    }

    public static Map getMap(String mapName) {
        for (Map m : getMaps()) {
            if (m.getName().equalsIgnoreCase(mapName)) {
                return m;
            }
        }
        return null;
    }
}
