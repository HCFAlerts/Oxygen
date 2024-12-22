package me.traduciendo.oxygen.bungeecore.commands.server;

import me.traduciendo.oxygen.Oxygen;
import me.traduciendo.oxygen.utils.CC;
import me.traduciendo.oxygen.utils.CreatorYML;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Random;

/**
 * @author Traduciendo
 * @BungeeUtils project
 * SRC and Jar available at dsc.gg/liteclubdevelopment
 * or github.com/HCFAlerts --> github.com/liteclubdevelopment
 */
public class HubCommand extends Command {

    private final CreatorYML config = Oxygen.getInstance().getConfigYML();

    public HubCommand() {
        super("hub", "", "lobby");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!config.getConfiguration().getBoolean("HUB.ENABLED", true)) {
            sender.sendMessage(CC.translate("&cThis command is currently disabled."));
            return;
        }

        if (!(sender instanceof ProxiedPlayer)) return;
        ProxiedPlayer player = (ProxiedPlayer) sender;

        int hubNumber = args.length == 1 ? parseHubNumber(args[0]) : 0;
        if (hubNumber < 0) {
            player.sendMessage(CC.translate("&cInvalid hub number."));
            return;
        }

        String hubName = "Hub-0" + hubNumber;
        ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(hubName);

        if (serverInfo == null) {
            player.sendMessage(CC.translate("&cHub not found. Use: /hub <number>"));
            return;
        }

        player.sendMessage(CC.translate(config.getConfiguration().getString("HUB.MESSAGE").replace("%server%", String.valueOf(hubNumber))));
        player.connect(serverInfo);
    }

    private int parseHubNumber(String arg) {
        try {
            return Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
