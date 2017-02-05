package com.pixelyeti.goldsiege.Executors;

import com.pixelyeti.goldsiege.GameMechs.GameManager;
import com.pixelyeti.goldsiege.Main;
import com.pixelyeti.goldsiege.Util.Countdown;
import com.pixelyeti.goldsiege.Util.StringUtilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Callum on 30/10/2016.
 */
public class StartGame implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(StringUtilities.errorMessage);
            return false;
        }
        Player p = (Player) commandSender;
        if (!p.hasPermission("goldsiege.admin.StartGame") || !p.hasPermission("goldsiege.admin.*")) {
            p.sendMessage(StringUtilities.noPermission);
            return false;
        }
        if(args.length != 0) {
            p.sendMessage(StringUtilities.invalidArguments);
            return false;
        }
        String gameName = GameManager.getPlayersGame(p.getUniqueId());
        new Countdown(GameManager.getGame(gameName), 30, 30, 20, 10, 5, 4, 3, 2, 1)
                .runTaskTimer(Main.instance, 0, 20);
        return false;
    }
}
