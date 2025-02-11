package me.traduciendo.oxygen;

import me.traduciendo.oxygen.utils.CC;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

/**
 * @author Traduciendo
 * @Oxygen project
 * SRC and Jar available at dsc.gg/liteclubdevelopment
 * or github.com/HCFAlerts --> github.com/liteclubdevelopment
 */

public class OxygenCommand extends Command {
   private final Oxygen plugin;

   public OxygenCommand(Oxygen plugin) {
      super(
              "oxygen",
              "",
              Oxygen.getInstance().getConfiguration().getStringList("OXYGEN_COMMAND.ALIASES").toArray(new String[0])
      );
      this.plugin = plugin;
   }

   @Override
   public void execute(CommandSender sender, String[] args) {
      if (!sender.hasPermission(Oxygen.getInstance().getConfiguration().getString("OXYGEN_COMMAND.PERMISSION"))) {
         sender.sendMessage(CC.translate(Oxygen.getInstance().getConfig().getString("GENERAL.NO_PERMISSIONS")));
         return;
      }

      if (args.length == 0) {
         sender.sendMessage(CC.translate(Theme.getSecondaryColor() + "&m=============================="));
         sender.sendMessage(CC.translate(Theme.getPrimaryColor() + "Oxygen&7 &8- &fv" + plugin.getDescription().getVersion()));
         sender.sendMessage(CC.translate(""));
         sender.sendMessage(CC.translate(Theme.getSecondaryColor() + "┃ &f/oxygen reload"));
         sender.sendMessage(CC.translate(Theme.getSecondaryColor() + "&m=============================="));
         return;
      }

      if (args[0].equalsIgnoreCase("reload")) {
         plugin.reloadConfig();
         sender.sendMessage(CC.translate(Theme.getSecondaryColor() + "&m=============================="));
         sender.sendMessage(CC.translate(Theme.getPrimaryColor() + "Oxygen&7 &areloaded successfully!"));
         sender.sendMessage(CC.translate(Theme.getSecondaryColor() + "┃ &7Reloaded files: &fconfig.yml&7, &flang.yml"));
         sender.sendMessage(CC.translate(Theme.getSecondaryColor() + "&m=============================="));
      } else {
         sender.sendMessage(CC.translate("&cInvalid command. Use /oxygen reload."));
      }
   }
}
