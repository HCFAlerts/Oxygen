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
         sender.sendMessage(CC.translate("&cYou don't have permission to execute this command."));
         return;
      }

      if (args.length == 0) {
         sender.sendMessage(CC.translate("&3&m=============================="));
         sender.sendMessage(CC.translate("&bOxygen&7 &8- &7" + plugin.getDescription().getVersion()));
         sender.sendMessage(CC.translate(""));
         sender.sendMessage(CC.translate("&3┃ &f/oxygen reload"));
         sender.sendMessage(CC.translate("&3&m=============================="));
         return;
      }

      if (args[0].equalsIgnoreCase("reload")) {
         plugin.reloadConfig();
         sender.sendMessage(CC.translate("&3&m=============================="));
         sender.sendMessage(CC.translate("&bOxygen&7 &areloaded successfully!"));
         sender.sendMessage(CC.translate("&3┃ &7Reloaded files: &fconfig.yml"));
         sender.sendMessage(CC.translate("&3&m=============================="));
      } else {
         sender.sendMessage(CC.translate("&cInvalid command. Use /oxygen reload."));
      }
   }
}
