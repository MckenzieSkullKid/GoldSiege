package com.pixelyeti.goldsiege.GameMechs;

import com.pixelyeti.goldsiege.Util.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * Created by Callum on 09/01/2016.
 */
public class ShopGUI {

    public static Inventory shopGUI;

    public static void initiate() {
        shopGUI = Bukkit.createInventory(null, 9, "Shop");
    }

    public static void setItems() {
        shopGUI = Bukkit.createInventory(null, 2 * 9, "Shop");

        //shopGUI.setItem(0, ItemStackBuilder.createCustomItemStack(Material.STONE_SWORD, ));

        for (int i = 0; i <= (GameManager.getGames().length - 1); i++) {
            System.out.println(GameManager.getGames()[i].gameName);
            shopGUI.setItem(i, ItemStackBuilder.createCustomItemStack(Material.HARD_CLAY, "Game " + GameManager.getGames()[i].gameName
                    , ChatColor.GOLD, 1, i));
        }
    }

    public static void openInventory(UUID uuid) {
        Player p = Bukkit.getPlayer(uuid);
        setItems();
        p.openInventory(shopGUI);
    }

}
