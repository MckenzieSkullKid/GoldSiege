package com.pixelyeti.goldsiege.Executors;

import com.pixelyeti.goldsiege.GameMechs.Map;
import com.pixelyeti.goldsiege.GameMechs.MapManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Callum on 31/10/2016.
 */
public class ListAllMapSpawns implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        for (Map m : MapManager.getMaps()) {
            for (Location l : m.spawns) {
                System.out.println(m.getName() + ": " + l);
            }
        }
        return false;
    }
}
