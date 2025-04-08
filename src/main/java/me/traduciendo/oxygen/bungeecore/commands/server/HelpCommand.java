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
public class HelpCommand extends Command {

    private final CreatorYML config = Oxygen.getInstance().getConfigYML();

    public HelpCommand() {
        super(
                "help",
                Oxygen.getInstance().getCommandsConfiguration().getString("HELP.PERMISSION", ""),
                Oxygen.getInstance().getCommandsConfiguration().getStringList("HELP.ALIASES").toArray(new String[0])
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!Oxygen.getInstance().getCommandsYML().getConfiguration().getBoolean("HELP.ENABLED", true)) {
            sender.sendMessage(CC.translate(config.getConfiguration().getString("GENERAL.DISABLED_COMMAND")));
            return;
        }

        if (!(sender instanceof ProxiedPlayer)) {
            return;
        }

        String onlinePlayers = String.valueOf(ProxyServer.getInstance().getOnlineCount());
        String maxOnlinePlayers = String.valueOf(config.getConfiguration().getInt("SLOTS"));

        Oxygen.getInstance().getLangConfiguration().getStringList("HELP_COMMAND.MESSAGE").stream()
                .map(msg -> CC.translate(
                        msg.replace("%primary%", Theme.getPrimaryColor())
                                .replace("%secondary%", Theme.getSecondaryColor())
                                .replace("%middle%", Theme.getMiddleColor())
                                .replace("%time%", Oxygen.getInstance().getTime())
                                .replace("%servername%", Oxygen.getInstance().getConfiguration().getString("SOCIAL.SERVER_NAME"))
                                .replace("%discord%", Oxygen.getInstance().getConfiguration().getString("SOCIAL.DISCORD"))
                                .replace("%store%", Oxygen.getInstance().getConfiguration().getString("SOCIAL.STORE"))
                                .replace("%teamspeak%", Oxygen.getInstance().getConfiguration().getString("SOCIAL.TEAMSPEAK"))
                                .replace("%twitter%", Oxygen.getInstance().getConfiguration().getString("SOCIAL.TWITTER"))
                                .replace("%website%", Oxygen.getInstance().getConfiguration().getString("SOCIAL.WEBSITE"))
                                .replace("%online%", onlinePlayers)
                                .replace("%maxonline%", maxOnlinePlayers)
                ))
                .forEach(sender::sendMessage);
    }
}
