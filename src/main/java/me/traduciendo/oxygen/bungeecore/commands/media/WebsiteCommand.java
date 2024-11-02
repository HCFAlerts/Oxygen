package me.traduciendo.oxygen.bungeecore.commands.media;

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
public class WebsiteCommand extends Command {

    private final CreatorYML config = Oxygen.getInstance().getConfigYML();

    public WebsiteCommand() {
        super("website", "", "web");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            return;
        }

        if (!config.getConfiguration().getBoolean("WEBSITE.ENABLED", true)) {
            sender.sendMessage(CC.translate("&cThis command is currently disabled."));
            return;
        }

        config.getConfiguration().getStringList("WEBSITE.MESSAGE").stream()
                .map(CC::translate)
                .forEach(sender::sendMessage);
    }
}
