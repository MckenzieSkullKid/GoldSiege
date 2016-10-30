package com.pixelyeti.goldsiege.Executors;

import com.pixelyeti.goldsiege.GameMechs.Map;
import com.pixelyeti.goldsiege.GameMechs.MapManager;
import com.pixelyeti.goldsiege.Util.StringUtilities;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Callum on 30/10/2016.
 */
public class AddMapSpawn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(StringUtilities.errorMessage);
            return false;
        }
        Player p = (Player) commandSender;
        if (!p.hasPermission("goldsiege.admin.AddMapSpawn") || !p.hasPermission("goldsiege.admin.*")) {
            p.sendMessage(StringUtilities.noPermission);
            return false;
        }
        if(args.length != 2) {
            p.sendMessage(StringUtilities.invalidArguments);
            return false;
        }
        Map m = MapManager.getMap(args[0]);
        m.addMapSpawn(args[0], args[1], p.getUniqueId());
        p.sendMessage(StringUtilities.prefix + ChatColor.AQUA + "Spawn for " + args[1] + " added in the map " + args[0]);

        return false;
    }
}
