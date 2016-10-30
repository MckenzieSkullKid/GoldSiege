package com.pixelyeti.goldsiege.Executors;

import com.pixelyeti.goldsiege.Util.StringUtilities;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Callum on 29/10/2016.
 */
public class TPWorld implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(StringUtilities.errorMessage);
            return false;
        }
        Player p = (Player) commandSender;
        if (!p.hasPermission("goldsiege.admin.TPWorld") || !p.hasPermission("goldsiege.admin.*")) {
            p.sendMessage(StringUtilities.noPermission);
            return false;
        }
        if(args.length != 1) {
            p.sendMessage(StringUtilities.invalidArguments);
            return false;
        }
        Bukkit.getServer().createWorld(new WorldCreator(args[0]));
        Location l = new Location(Bukkit.getWorld(args[0]), 0, 75, 0);
        p.teleport(l);
        return false;
    }
}
