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
   public OxygenCommand() {
      super(
              "oxygen",
              "",
              Oxygen.getInstance().getCommandsConfiguration().getStringList("OXYGEN_COMMAND.ALIASES").toArray(new String[0])
      );
   }

   @Override
   public void execute(CommandSender sender, String[] args) {
      if (!sender.hasPermission(Oxygen.getInstance().getCommandsConfiguration().getString("OXYGEN_COMMAND.PERMISSION"))) {
         sender.sendMessage(CC.translate(Oxygen.getInstance().getConfig().getString("GENERAL.NO_PERMISSIONS")));
         return;
      }

      if (args.length == 0) {
         sender.sendMessage(CC.translate(Theme.getSecondaryColor() + "&m=============================="));
         sender.sendMessage(CC.translate(Theme.getPrimaryColor() + "Oxygen&7 &8- &fv" + Oxygen.getInstance().getDescription().getVersion()));
         sender.sendMessage(CC.translate(""));
         sender.sendMessage(CC.translate(Theme.getSecondaryColor() + "┃ &f/oxygen reload"));
         sender.sendMessage(CC.translate(Theme.getSecondaryColor() + "&m=============================="));
         return;
      }

      if (args[0].equalsIgnoreCase("reload")) {
         Oxygen.getInstance().reloadConfig();
         sender.sendMessage(CC.translate(Theme.getSecondaryColor() + "&m=============================="));
         sender.sendMessage(CC.translate(Theme.getPrimaryColor() + "Oxygen&7 &areloaded successfully!"));
         sender.sendMessage(CC.translate(Theme.getSecondaryColor() + "┃ &fAll config files reloaded"));
         sender.sendMessage(CC.translate(Theme.getSecondaryColor() + "&m=============================="));
      } else {
         sender.sendMessage(CC.translate("&cInvalid command. Use /oxygen reload."));
      }
   }
}
