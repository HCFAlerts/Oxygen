package me.traduciendo.oxygen.bungeecore.commands.media;

import me.traduciendo.oxygen.Oxygen;
import me.traduciendo.oxygen.Theme;
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

public class TeamSpeakCommand extends Command {

    private final CreatorYML config = Oxygen.getInstance().getConfigYML();

    public TeamSpeakCommand() {
        super(
                "teamspeak",
                Oxygen.getInstance().getCommandsConfiguration().getString("TEAMSPEAK.PERMISSION", ""),
                Oxygen.getInstance().getCommandsConfiguration().getStringList("TEAMSPEAK.ALIASES").toArray(new String[0])
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            return;
        }

        if (!Oxygen.getInstance().getCommandsYML().getConfiguration().getBoolean("TEAMSPEAK.ENABLED", true)) {
            sender.sendMessage(CC.translate(config.getConfiguration().getString("GENERAL.DISABLED_COMMAND")));
            return;
        }

        Oxygen.getInstance().getLangConfiguration().getStringList("SOCIAL.TEAMSPEAK.MESSAGE").stream()
                .map(msg -> CC.translate(
                        msg.replace("%primary%", Theme.getPrimaryColor())
                                .replace("%secondary%", Theme.getSecondaryColor())
                                .replace("%middle%",  Theme.getMiddleColor())
                                .replace("%teamspeak%", Oxygen.getInstance().getConfiguration().getString("SOCIAL.TEAMSPEAK"))
                ))
                .forEach(sender::sendMessage);
    }
}
