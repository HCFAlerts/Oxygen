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
public class WebsiteCommand extends Command {

    private final CreatorYML config = Oxygen.getInstance().getConfigYML();

    public WebsiteCommand() {
        super(
                "website",
                Oxygen.getInstance().getConfiguration().getString("WEBSITE.PERMISSION", ""),
                Oxygen.getInstance().getConfiguration().getStringList("WEBSITE.ALIASES").toArray(new String[0])
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            return;
        }

        if (!config.getConfiguration().getBoolean("WEBSITE.ENABLED", true)) {
            sender.sendMessage(CC.translate(config.getConfiguration().getString("GENERAL.DISABLED_COMMAND")));
            return;
        }

        config.getConfiguration().getStringList("WEBSITE.MESSAGE").stream()
                .map(msg -> CC.translate(
                        msg.replace("%theme_primary%", Theme.getPrimaryColor())
                                .replace("%theme_secondary%", Theme.getSecondaryColor())
                ))
                .forEach(sender::sendMessage);
    }
}
