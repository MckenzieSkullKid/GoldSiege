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

    public static Main instance;

    public static Main getInstance() {
        return instance;
    }

    public void onEnable() {
        this.saveDefaultConfig();
        this.saveConfig();
        this.reloadConfig();

        this.instance = this;

        teamAmount = this.getConfig().getConfigurationSection("Game").getInt("TeamAmount");
        teams = new String[teamAmount];
        mapSection = this.getConfig().getConfigurationSection("Maps");

        TeamGUI.initiate();
        GameGUI.initiate();

        GameManager.createGames();
        saveConfig();

        ExecutorManager.registerExecutors(this);

        PluginManager pm = getServer().getPluginManager();
        EventsManager.registerEvents(pm);

        postEnable();
    }

    public void onDisable() {
    }

    public void postEnable() {
        MapManager.initiateMaps();
        Teams.applyRandPrefix();

        postEnabled = true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        return false;
    }
}
