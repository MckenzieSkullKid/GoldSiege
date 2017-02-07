package com.pixelyeti.goldsiege.Util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Callum on 09/06/2015.
 */
public class ItemStackBuilder {

    public static ItemStack createCustomItemStack(Material m, String name, ChatColor colour,  int size, int data, String... lore) {
        ItemStack i = new ItemStack(m, size, (short)0, (byte)data);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(colour + name);
        List<String> text = new ArrayList<>();
        for (String s : lore) {
            text.add(ChatColor.DARK_PURPLE.toString() + ChatColor.ITALIC + s);
        }
        im.setLore(text);
        i.setItemMeta(im);
        return i;
    }

    public static ItemStack createCustomItemStack(Material m, String name, ChatColor colour,  int size) {
        return createCustomItemStack(m, name, colour, size, 0);
    }

    public static ItemStack createShopItem(Material m, String name, ChatColor colour, String price, String description) {
        ItemStack i = createCustomItemStack(m, name, colour, 1, 0);
        ItemMeta im = i.getItemMeta();
        List<String> is = new ArrayList<>();
        is.add(ChatColor.DARK_GRAY + description);
        is.add(ChatColor.LIGHT_PURPLE + price + " xp levels!");
        im.setLore(is);
        return i;
    }

}
