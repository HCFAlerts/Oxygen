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

public class StoreCommand extends Command {

    private final CreatorYML config = Oxygen.getInstance().getConfigYML();

    public StoreCommand() {
        super(
                "store",
                Oxygen.getInstance().getCommandsConfiguration().getString("STORE.PERMISSION", ""),
                Oxygen.getInstance().getCommandsConfiguration().getStringList("STORE.ALIASES").toArray(new String[0])
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            return;
        }

        if (!Oxygen.getInstance().getCommandsYML().getConfiguration().getBoolean("STORE.ENABLED", true)) {
            sender.sendMessage(CC.translate(config.getConfiguration().getString("GENERAL.DISABLED_COMMAND")));
            return;
        }

        Oxygen.getInstance().getLangConfiguration().getStringList("SOCIAL.STORE.MESSAGE").stream()
                .map(msg -> CC.translate(
                        msg.replace("%primary%", Theme.getPrimaryColor())
                                .replace("%secondary%", Theme.getSecondaryColor())
                                .replace("%middle%", Theme.getMiddleColor())
                                .replace("%store%", Oxygen.getInstance().getConfiguration().getString("SOCIAL.STORE"))
                ))
                .forEach(sender::sendMessage);
    }
}
