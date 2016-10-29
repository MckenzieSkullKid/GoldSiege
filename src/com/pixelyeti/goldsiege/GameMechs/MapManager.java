package com.pixelyeti.goldsiege.GameMechs;

import com.pixelyeti.goldsiege.Main;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import javax.security.auth.login.Configuration;
import java.util.Random;

/**
 * Created by Callum on 11/06/2015.
 */
public class MapManager {

    public static Map[] maps = null;
    public static int count = 0;

    public static final void initiateMaps() {
        FileConfiguration config = Main.plugin.getConfigFile(); // <----------- ERRORING FOR NO REASON D:
        ConfigurationSection configSection = config.getConfigurationSection("Maps");

        int mapsSize = 0;

        for (String s : configSection.getKeys(false)) {
            mapsSize++;
        }

        maps = new Map[mapsSize];

        for (String s : configSection.getKeys(false)) {

            String name = config.getString("Maps." + s + ".Name");
            int numTeams = config.getInt("Maps." + s + ".NumTeams");
            String worldFileName = config.getString("Maps." + s + ".WorldFileName");
            boolean saving = config.getBoolean("Maps." + s + ".Saving");

            maps[count] = new Map(name, numTeams, worldFileName, saving, Map.loadSpawns(name));

            count++;
        }
    }

    public static Map selectMap() {
        Map map = null;
        Random rand = new Random();

        int randNum = rand.nextInt(((maps.length - 1) - 0) +1);
        map = maps[randNum];

        return map;
    }

    public static Map[] getMaps() {
        return maps;
    }


}
