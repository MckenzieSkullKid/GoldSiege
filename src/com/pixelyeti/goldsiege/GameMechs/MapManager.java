package com.pixelyeti.goldsiege.GameMechs;

import com.pixelyeti.goldsiege.Main;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import javax.security.auth.login.Configuration;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Callum on 11/06/2015.
 */
public class MapManager {

    public static ArrayList<Map> maps = new ArrayList<>();
    private static int count = 0;
    private Map m = new Map();

    public static final void initiateMaps() {
        FileConfiguration config = Main.plugin.getConfigFile();
        ConfigurationSection configSection = config.getConfigurationSection("Maps");

        for (String s : configSection.getKeys(false)) {
            Map m = new Map();

            m.setName(config.getString("Maps." + s + ".Name"));
            m.setTeamAmount(config.getInt("Maps." + s + ".NumTeams"));
            m.setWorldFileName(config.getString("Maps." + s + ".WorldFileName"));
            m.setSaving(config.getBoolean("Maps." + s + ".Saving"));

            maps.add(m);
            count++;
        }
    }

    public static Map selectMap() {
        Map map = null;
        Random rand = new Random();

        int randNum = rand.nextInt(((maps.size() - 1)) +1);
        System.out.println(randNum);
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
