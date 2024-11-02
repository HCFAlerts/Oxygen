package me.traduciendo.oxygen.bungeecore.commands.server;

import me.traduciendo.oxygen.Oxygen;
import me.traduciendo.oxygen.utils.CC;
import me.traduciendo.oxygen.utils.CreatorYML;
import net.md_5.bungee.api.CommandSender;
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
        super("find", "bungeecord.command.find", "findplayer", "find player", "look", "lookup", "where", "whereis");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!config.getConfiguration().getBoolean("FIND.ENABLED", true)) {
            sender.sendMessage(CC.translate("&cThis command is currently disabled."));
            return;
        }

        if (args.length == 0) {
            sender.sendMessage(CC.translate(config.getConfiguration().getString("FIND.USAGE")));
            return;
        }

        ProxiedPlayer player = Oxygen.getInstance().getProxy().getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage(CC.translate(
                    config.getConfiguration().getString("FIND.OFFLINE_PLAYER_COLOR") + args[0] + " " +
                            config.getConfiguration().getString("FIND.OFFLINE")
            ));
            return;
        }

        sender.sendMessage(CC.translate(
                config.getConfiguration().getString("FIND.PLAYER_COLOR") + player.getName() +
                        config.getConfiguration().getString("FIND.MESSAGE") + " " +
                        config.getConfiguration().getString("FIND.SERVER_COLOR") + player.getServer().getInfo().getName() +
                        config.getConfiguration().getString("FIND.FINAL")
        ));
    }
}
