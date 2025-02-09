package me.traduciendo.oxygen;

import net.md_5.bungee.api.ChatColor;

/**
 * @author Traduciendo
 * @Oxygen project
 * SRC and Jar available at dsc.gg/liteclubdevelopment
 * or github.com/HCFAlerts --> github.com/liteclubdevelopment
 */

public class Theme {
    private static String primaryColor;
    private static String secondaryColor;

    public static void loadColors() {
        primaryColor = Oxygen.getInstance().getConfig().getString("GENERAL.PRIMARY_COLOR", "&b");
        secondaryColor = Oxygen.getInstance().getConfig().getString("GENERAL.SECONDARY_COLOR", "&3");
    }

    public static String getPrimaryColor() {
        return ChatColor.translateAlternateColorCodes('&', primaryColor);
    }

    public static String getSecondaryColor() {
        return ChatColor.translateAlternateColorCodes('&', secondaryColor);
    }
}


