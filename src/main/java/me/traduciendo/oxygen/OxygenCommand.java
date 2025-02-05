package me.traduciendo.oxygen;

import me.traduciendo.oxygen.utils.CC;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class OxygenCommand extends Command {
   private final Oxygen plugin;

   public OxygenCommand(Oxygen plugin) {
      super("oxygen", "", "motd", "omotd", "ocore");
      this.plugin = plugin;
   }

   @Override
   public void execute(CommandSender sender, String[] args) {
      if (!sender.hasPermission("oxygen.admin")) {
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
         sender.sendMessage(CC.translate(Theme.getSecondaryColor() + "┃ &7Reloaded files: &fconfig.yml"));
         sender.sendMessage(CC.translate(Theme.getSecondaryColor() + "&m=============================="));
      } else {
         sender.sendMessage(CC.translate("&cInvalid command. Use /oxygen reload."));
      }
   }
}
