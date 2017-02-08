package com.pixelyeti.goldsiege.GameMechs;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * Created by Callum on 12/06/2015.
 */
public class StartingItems {

    public static void get(UUID id) {
        ItemStack goldSword = new ItemStack(Material.GOLD_SWORD, 1);
        ItemStack goldChest = new ItemStack(Material.GOLD_CHESTPLATE, 1);
        ItemStack bow = new ItemStack(Material.BOW, 1);
        ItemStack arrows = new ItemStack(Material.ARROW, 8);

        Player p = Bukkit.getPlayer(id);

        bow.addEnchantment(Enchantment.ARROW_DAMAGE, 1);

        p.getInventory().clear();
        p.getInventory().setItem(0, goldSword);
        p.getInventory().setItem(1, bow);
        p.getInventory().setChestplate(goldChest);
        p.getInventory().setItem(9, arrows);

    }

}
