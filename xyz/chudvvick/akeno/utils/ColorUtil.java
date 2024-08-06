package xyz.chudvvick.akeno.utils;

import net.md_5.bungee.api.ChatColor;

public class ColorUtil {
   public static String fixColor(String x) {
      return ChatColor.translateAlternateColorCodes('&', x);
   }
}
