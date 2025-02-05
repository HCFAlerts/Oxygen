package me.traduciendo.oxygen.bungeecore.commands.server;

import me.traduciendo.oxygen.Oxygen;
import me.traduciendo.oxygen.utils.CC;
import me.traduciendo.oxygen.utils.CreatorYML;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * @author Traduciendo
 * @BungeeUtils project
 * SRC and Jar available at dsc.gg/liteclubdevelopment
 * or github.com/HCFAlerts --> github.com/liteclubdevelopment
 */
public class HubCommand extends Command {

    private final CreatorYML config = Oxygen.getInstance().getConfigYML();

    public HubCommand() {
        super(
                "hub",
                Oxygen.getInstance().getConfiguration().getString("HUB.PERMISSION", ""),
                Oxygen.getInstance().getConfiguration().getStringList("HUB.ALIASES").toArray(new String[0])
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!config.getConfiguration().getBoolean("HUB.ENABLED", true)) {
            sender.sendMessage(CC.translate(config.getConfiguration().getString("GENERAL.DISABLED_COMMAND")));
            return;
        }

        if (!(sender instanceof ProxiedPlayer)) return;
        ProxiedPlayer player = (ProxiedPlayer) sender;

        ServerInfo targetHub;

        if (args.length == 1) {
            int hubNumber = parseHubNumber(args[0]);
            if (hubNumber < 0) {
                player.sendMessage(CC.translate(config.getConfiguration().getString("HUB.INVALID_HUB_NUMBER")));
                return;
            }
            targetHub = ProxyServer.getInstance().getServerInfo("Hub-0" + hubNumber);
        } else {
            targetHub = getLeastPopulatedHub();
        }

        if (targetHub == null) {
            player.sendMessage(CC.translate(config.getConfiguration().getString("HUB.NO_AVAILABLE_HUBS")));
            return;
        }

        player.sendMessage(CC.translate(config.getConfiguration().getString("HUB.MESSAGE").replace("%server%", targetHub.getName())));
        player.connect(targetHub);
    }

    private ServerInfo getLeastPopulatedHub() {
        ServerInfo bestHub = null;
        int minPlayers = Integer.MAX_VALUE;

        for (ServerInfo server : ProxyServer.getInstance().getServers().values()) {
            if (server.getName().toLowerCase().startsWith("hub-")) {
                int playerCount = server.getPlayers().size();
                if (playerCount < minPlayers) {
                    minPlayers = playerCount;
                    bestHub = server;
                }
            }
        }

        return bestHub;
    }

    private int parseHubNumber(String arg) {
        try {
            return Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}