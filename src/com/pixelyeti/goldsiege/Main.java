package com.pixelyeti.goldsiege;

import com.pixelyeti.goldsiege.Executors.*;
import com.pixelyeti.goldsiege.GameMechs.*;
import com.pixelyeti.goldsiege.Listeners.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

    private static int teamAmount;
    public static String[] teams;
    public static boolean postEnabled = false;
    public static ConfigurationSection mapSection;

    public static final Logger log = Logger.getLogger("Minecraft");

    public static YamlConfiguration config;
    public File configFile = new File(getDataFolder() + "/config.yml");
    public FileConfiguration configData = YamlConfiguration.loadConfiguration(configFile);

    public static Main instance;

    public static Main getInstance() {
        return instance;
    }

    public void onEnable() {
        createConfigFile();
        reloadConfig();

        this.instance = this;

        teamAmount = getConfigFile().getConfigurationSection("Game").getInt("TeamAmount");
        teams = new String[teamAmount];
        mapSection = getConfigFile().getConfigurationSection("Maps");

        TeamGUI.initiate();
        GameGUI.initiate();

        GameManager.createGames();

        saveConfig();

        ExecutorManager.registerExecutors();

        PluginManager pm = getServer().getPluginManager();

        EventsManager.registerEvents(pm);

        pm.registerEvents(new InvClick(), this);
        pm.registerEvents(new Join(this), this);
        pm.registerEvents(new PlayerInteract(), this);
        pm.registerEvents(new EntityDie(), this);
        pm.registerEvents(new WeatherChange(), this);
        pm.registerEvents(new PlayerLeave(), this);
        pm.registerEvents(new EntitySpawn(), this);

        getCommand("setspawn").setExecutor(new SetSpawn(this));
        getCommand("spawn").setExecutor(new Spawn(this));
        getCommand("tpworld").setExecutor(new TPWorld());
        getCommand("startgame").setExecutor(new StartGame());
        getCommand("addmapspawn").setExecutor(new AddMapSpawn());
        getCommand("listspawns").setExecutor(new ListAllMapSpawns());

        postEnable();
    }

    public void onDisable() {
    }

    public void postEnable() {
        MapManager.initiateMaps();
        Teams.applyRandPrefix();

        postEnabled = true;
    }

    public void createConfigFile() {
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                getLogger().severe("[GoldSiege]" + ChatColor.DARK_RED
                        + "Could not create config file");
            }
            saveConfigFile();
            setupConfigFile();
            reloadConfigFile();
        }

        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void setupConfigFile() {
        List<String> teams = new ArrayList<>();
        teams.add("Red");
        teams.add("Blue");

        configData.set("Game.TeamAmount", 2);
        configData.set("Game.Teams", teams);
        configData.set("Game.Spectate", true);
        configData.set("Game.AmountGames", 3);
        configData.set("Game.MinPlayers", 4);
        configData.set("Game.MaxPlayers", 10);
        configData.set("Game.Prefix", "gs");
        configData.set("Game.ChooseMapBefore", false);
        configData.set("Maps.Example.Name", "Example");
        configData.set("Maps.Example.NumTeams", 2);
        configData.set("Maps.Example.WorldFileName", "example");
        configData.set("Maps.Example.Saving", false);
        configData.set("Maps.Example.Spawns.1.x", 1);
        configData.set("Maps.Example.Spawns.1.y", 1);
        configData.set("Maps.Example.Spawns.1.z", 1);
        configData.set("Maps.Example.Spawns.1.yaw", 1);
        configData.set("Maps.Example.Spawns.1.pitch", 1);
        configData.set("Maps.Example.Spawns.2.x", 1);
        configData.set("Maps.Example.Spawns.2.y", 1);
        configData.set("Maps.Example.Spawns.2.z", 1);
        configData.set("Maps.Example.Spawns.2.yaw", 1);
        configData.set("Maps.Example.Spawns.2.pitch", 1);

        saveConfigFile();
    }

    public void saveConfigFile() {
        try {
            configData.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfigFile() {
        if (configData == null) {
            reloadConfigFile();
        }
        return configData;
    }

    public final void reloadConfigFile() {
        if (configFile == null) {
            configFile = new File(getDataFolder() + "/config.yml");
        }
        config = YamlConfiguration.loadConfiguration(configFile);
        InputStream defConfigStream = getResource("config.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration
                    .loadConfiguration(defConfigStream);
            config.setDefaults(defConfig);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        return false;
    }
}
