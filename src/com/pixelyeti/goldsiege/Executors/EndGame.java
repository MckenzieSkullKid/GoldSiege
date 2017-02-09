package com.pixelyeti.goldsiege.Executors;

import com.pixelyeti.goldsiege.GameMechs.Game;
import com.pixelyeti.goldsiege.GameMechs.GameManager;
import com.pixelyeti.goldsiege.GameMechs.Teams;
import com.pixelyeti.goldsiege.Util.StringUtilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Callum on 09/02/2017.
 */
public class EndGame implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(StringUtilities.errorMessage);
            return false;
        }
        Player p = (Player) commandSender;
        if (!p.hasPermission("goldsiege.admin.EndGame") || !p.hasPermission("goldsiege.admin.*")) {
            p.sendMessage(StringUtilities.noPermission);
            return false;
        }
        if (args.length != 0) {
            p.sendMessage(StringUtilities.invalidArguments);
            return false;
        }
        Game g = GameManager.getGame(GameManager.getPlayersGame(p.getUniqueId()));
        g.winningTeam = null;
        GameManager.endGame(g.gameName);
        return false;
    }
}
