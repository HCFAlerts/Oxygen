package me.traduciendo.oxygen.bungeecore.commands.server;

import me.traduciendo.oxygen.Oxygen;
import me.traduciendo.oxygen.Theme;
import me.traduciendo.oxygen.utils.CC;
import me.traduciendo.oxygen.utils.CreatorYML;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * @author Traduciendo
 * @BungeeUtils project
 * SRC and Jar available at dsc.gg/liteclubdevelopment
 * or github.com/HCFAlerts --> github.com/liteclubdevelopment
 */

public class FindCommand extends Command {

    private final CreatorYML config = Oxygen.getInstance().getConfigYML();

    public FindCommand() {
        super(
                "find",
                Oxygen.getInstance().getConfiguration().getString("FIND.PERMISSION", "oxygen.command.find"),
                Oxygen.getInstance().getConfiguration().getStringList("FIND.ALIASES").toArray(new String[0])
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!config.getConfiguration().getBoolean("FIND.ENABLED", true)) {
            sender.sendMessage(CC.translate(config.getConfiguration().getString("GENERAL.DISABLED_COMMAND")));
            return;
        }

        if (args.length == 0) {
            sender.sendMessage(CC.translate(config.getConfiguration().getString("FIND.USAGE")));
            return;
        }

        ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
        String message = target == null
                ? config.getConfiguration().getString("FIND.OFFLINE")
                .replace("%player%", args[0])
                : config.getConfiguration().getString("FIND.MESSAGE")
                .replace("%player%", target.getName())
                .replace("%server%", target.getServer().getInfo().getName());

        message = message.replace("%theme_primary%", Theme.getPrimaryColor())
                .replace("%theme_secondary%", Theme.getSecondaryColor());

        sender.sendMessage(CC.translate(message));
    }
}

