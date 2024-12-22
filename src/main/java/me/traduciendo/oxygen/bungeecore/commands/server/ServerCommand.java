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

public class ServerCommand extends Command {

    private final CreatorYML config = Oxygen.getInstance().getConfigYML();

    public ServerCommand() {
        super("server", "oxygen.command.server");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!config.getConfiguration().getBoolean("SERVER.ENABLED", true)) {
            sender.sendMessage(CC.translate("&cThis command is currently disabled."));
            return;
        }

        if (!(sender instanceof ProxiedPlayer)) return;
        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (args.length == 0) {
            showCurrentServer(player);
        } else if (args.length == 1) {
            connectToServer(player, args[0]);
        } else {
            player.sendMessage(CC.translate(config.getConfiguration().getString("SERVER.USAGE")));
        }
    }

    private void showCurrentServer(ProxiedPlayer player) {
        String currentServer = player.getServer().getInfo().getName();
        player.sendMessage(CC.translate(config.getConfiguration().getString("SERVER.CONNECTED").replace("%server%", currentServer)));

        String serversList = ProxyServer.getInstance().getServers().keySet().stream()
                .reduce(config.getConfiguration().getString("SERVER.SERVERS"), (list, server) -> list + server + config.getConfiguration().getString("SERVER.FORMAT"));

        player.sendMessage(CC.translate(serversList));
    }

    private void connectToServer(ProxiedPlayer player, String serverName) {
        ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(serverName);
        if (serverInfo == null) {
            player.sendMessage(CC.translate(config.getConfiguration().getString("SERVER.NO_PERMISSION")));
            return;
        }

        player.sendMessage(CC.translate(config.getConfiguration().getString("SERVER.MESSAGE").replace("%server%", serverName)));
        player.connect(serverInfo);
    }
}
