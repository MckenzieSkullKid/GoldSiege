package com.pixelyeti.goldsiege.Util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by Callum on 09/06/2015.
 */
public class ItemStackBuilder {

    public static ItemStack createCustomItemStack(Material m, String name, ChatColor colour,  int size, int data) {
        ItemStack i = new ItemStack(m, size, (short)0, (byte)data);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(colour + name);
        i.setItemMeta(im);
        return i;
    }

    public static ItemStack createCustomItemStack(Material m, String name, ChatColor colour,  int size) {
        return createCustomItemStack(m, name, colour, size, 0);
    }

}
