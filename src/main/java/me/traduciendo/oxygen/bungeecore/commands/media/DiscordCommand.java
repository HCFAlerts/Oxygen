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

public class DiscordCommand extends Command {

    private final CreatorYML config = Oxygen.getInstance().getConfigYML();

    public DiscordCommand() {
        super(
                "discord",
                Oxygen.getInstance().getConfiguration().getString("DISCORD.PERMISSION", ""),
                Oxygen.getInstance().getConfiguration().getStringList("DISCORD.ALIASES").toArray(new String[0])
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            return;
        }

        if (!config.getConfiguration().getBoolean("DISCORD.ENABLED", true)) {
            sender.sendMessage(CC.translate(config.getConfiguration().getString("GENERAL.DISABLED_COMMAND")));
            return;
        }

        config.getConfiguration().getStringList("DISCORD.MESSAGE").stream()
                .map(CC::translate)
                .forEach(sender::sendMessage);
    }
}
