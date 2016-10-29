package com.pixelyeti.goldsiege.Executors;

import com.pixelyeti.goldsiege.Main;
import com.pixelyeti.goldsiege.Util.StringUtilities;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

/**
 * Created by Callum on 09/01/2016.
 */
public class SetSpawn implements CommandExecutor {
    public SetSpawn(Main plugin) {
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(StringUtilities.errorMessage);
            return false;
        }
        Player p = (Player) commandSender;
        if (!p.hasPermission("goldsiege.admin.setspawn") || !p.hasPermission("goldsiege.admin.*")) {
            p.sendMessage(StringUtilities.noPermission);
            return false;
        }
        if(args.length >= 0) {
            p.sendMessage(StringUtilities.invalidArguments);
            return false;
        }

        ConfigurationSection serverSection = Main.plugin.getConfigFile().getConfigurationSection("Server");

        Location spawn = p.getLocation();
        serverSection.set("Spawn.World", spawn.getWorld().getName());
        serverSection.set("Spawn.X", spawn.getX());
        serverSection.set("Spawn.Y", spawn.getY());
        serverSection.set("Spawn.Z", spawn.getZ());
        serverSection.set("Spawn.Yaw", spawn.getYaw());
        serverSection.set("Spawn.Pitch", spawn.getPitch());
        Main.plugin.saveConfigFile();

        p.sendMessage(StringUtilities.prefix + ChatColor.GREEN + "The Server spawn has been set!");

        return false;
    }
}
