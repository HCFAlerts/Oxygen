package me.traduciendo.oxygen;

import me.traduciendo.oxygen.utils.CC;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

/**
 * @author Traduciendo
 * @FlameMOTD project
 * SRC and Jar available at dsc.gg/liteclubdevelopment
 * or github.com/HCFAlerts --> github.com/liteclubdevelopment
 */

public class OxygenCommand extends Command {
   private Oxygen plugin;

   public OxygenCommand(Oxygen plugin) {
      super("oxygen", "", "commands", "omotd", "motdcountdown");
      this.plugin = plugin;
   }

   public void execute(CommandSender sender, String[] args) {
      if (args.length == 1) {
         if (args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("oxygen.reload")) {
               this.plugin.reloadConfig();
               sender.sendMessage(t("&3&m=============================="));
               sender.sendMessage(t("&b&lOxygen&7 &areloaded successfully!"));
               sender.sendMessage(t("&b┃ &7Reloaded files: &fconfig.yml"));
               sender.sendMessage(t("&3&m=============================="));
            } else {
               sender.sendMessage(t("&cYou don't have permissions."));
            }
         } else {
            sender.sendMessage(CC.translate("&3&m=============================="));
            sender.sendMessage(CC.translate("&b&lOxygen&7 &8- &7" + Oxygen.getInstance().getDescription().getVersion()));
            sender.sendMessage(CC.translate(""));
            sender.sendMessage(CC.translate("&b┃ &f/oxygen reload"));
            sender.sendMessage(CC.translate("&3&m=============================="));
         }
      } else {
         sender.sendMessage(CC.translate("&3&m=============================="));
         sender.sendMessage(CC.translate("&b&lOxygen&7 &8- &7" + Oxygen.getInstance().getDescription().getVersion()));
         sender.sendMessage(CC.translate(""));
         sender.sendMessage(CC.translate("&b┃ &f/oxygen reload"));
         sender.sendMessage(CC.translate("&3&m=============================="));
      }

   }

   public static String t(String i) {
      return ChatColor.translateAlternateColorCodes('&', i);
   }
}
