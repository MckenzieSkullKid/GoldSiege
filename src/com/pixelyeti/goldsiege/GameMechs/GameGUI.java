package com.pixelyeti.goldsiege.GameMechs;

import com.pixelyeti.goldsiege.Util.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

/**
 * Created by Callum on 04/08/2015.
 */
public class GameGUI {

    public static Inventory gameGUI;

    public static void initiate() {
        gameGUI = Bukkit.createInventory(null, 9, "Games!");
    }

    public static void setItems() {
        int numSlots = GameManager.getGames().length / 9;
        gameGUI = Bukkit.createInventory(null, (numSlots + 1) * 9, "Games!");

        for (int i = 0; i <= (GameManager.getGames().length - 1); i++) {
            System.out.println(GameManager.getGames()[i].gameName);
            int metavalue;
            String gameState;
            switch (GameManager.getGames()[i].gameState) {
                case WAITING:
                    metavalue = 5;
                    gameState = ChatColor.GREEN + "Waiting!";
                    break;
                case INGAME:
                    metavalue = 14;
                    gameState = ChatColor.RED + "Ingame!";
                    break;
                case RESTARTING:
                    metavalue = 4;
                    gameState = ChatColor.YELLOW + "Restarting!";
                    break;
                default:
                    metavalue = i;
                    gameState = ChatColor.RED + "ERROR!";
                    break;
            }
            gameGUI.setItem(i, ItemStackBuilder.createCustomItemStack(Material.STAINED_CLAY, "Game " + GameManager.getGames()[i].gameName
                    , ChatColor.GOLD, 1, metavalue, gameState));
        }
    }

    public static void openInventory(UUID uuid) {
        Player p = Bukkit.getPlayer(uuid);
        setItems();
        p.openInventory(gameGUI);
    }

}
