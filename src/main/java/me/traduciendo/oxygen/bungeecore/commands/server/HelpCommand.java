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
public class HelpCommand extends Command {

    private final CreatorYML config = Oxygen.getInstance().getConfigYML();

    public HelpCommand() {
        super(
                "help",
                Oxygen.getInstance().getConfiguration().getString("HELP.PERMISSION", ""),
                Oxygen.getInstance().getConfiguration().getStringList("HELP.ALIASES").toArray(new String[0])
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!config.getConfiguration().getBoolean("HELP.ENABLED", true)) {
            sender.sendMessage(CC.translate(config.getConfiguration().getString("GENERAL.DISABLED_COMMAND")));
            return;
        }

        if (!(sender instanceof ProxiedPlayer)) {
            return;
        }

        config.getConfiguration().getStringList("HELP.MESSAGE").stream()
                .map(CC::translate)
                .forEach(sender::sendMessage);
    }
}
