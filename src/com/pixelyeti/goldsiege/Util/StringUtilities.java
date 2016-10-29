package com.pixelyeti.goldsiege.Util;

import org.bukkit.ChatColor;

/**
 * Created by Callum on 09/06/2015.
 */
public class StringUtilities {

    public static String prefix = ChatColor.GRAY + "[" + ChatColor.GOLD + "Gold Siege" + ChatColor.GRAY + "]" + ChatColor.RESET;
    public static String errorMessage = prefix + ChatColor.RED + "An error has occured!" + ChatColor.RESET;
    public static String invalidArguments = prefix + ChatColor.RED + "You entered invalid arguments!" + ChatColor.RESET;
    public static String noPermission = prefix + ChatColor.RED + "You do not have permission to do this!" + ChatColor.RESET;

}
