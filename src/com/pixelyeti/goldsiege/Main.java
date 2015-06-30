package com.pixelyeti.goldsiege;

import com.pixelyeti.goldsiege.GameMechs.MapManager;
import com.pixelyeti.goldsiege.Listeners.*;
import com.pixelyeti.goldsiege.GameMechs.Teams;
import com.pixelyeti.goldsiege.Util.TeamGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Callum on 09/06/2015.
 */
public class Main extends JavaPlugin {

    public static String[] teams = null;
    public static boolean postEnabled = false;
    public ConfigurationSection mapSection = getConfig().getConfigurationSection("Maps");

    public static final Logger log = Logger.getLogger("Minecraft");

    public static YamlConfiguration config;
    public File configFile = new File(getDataFolder()+"/config.yml");
    public FileConfiguration configData = YamlConfiguration.loadConfiguration(configFile);

    public static Main plugin;

    public void onEnable() {
        createConfigFile();
        reloadConfig();

        this.plugin = this;

        TeamGUI.initiate();

        saveConfig();

        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new InvClick(), this);
        pm.registerEvents(new Join(this), this);
        pm.registerEvents(new NameTag(), this);
        pm.registerEvents(new PlayerInteract(), this);

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
        String[] teams = {"Red", "Blue"};

        configData.set("Game.TeamAmount", 2);
        configData.set("Game.Teams", teams);
        configData.set("Game.Spectate", true);
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

    public void saveConfigFile(){
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
            configFile = new File(getDataFolder() +  "/config.yml");
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
        if (label.equalsIgnoreCase("test")) {
            Player p = (Player) sender;
            sender.sendMessage(Teams.getTeam(p.getUniqueId()).getSize() + "");
        }


        return false;
    }
}
