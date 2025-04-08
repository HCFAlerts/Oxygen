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
    public ServerCommand() {
        super(
                "server",
                Oxygen.getInstance().getCommandsConfiguration().getString("SERVER.PERMISSION", "oxygen.command.server"),
                Oxygen.getInstance().getCommandsConfiguration().getStringList("SERVER.ALIASES").toArray(new String[0])
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!Oxygen.getInstance().getCommandsYML().getConfiguration().getBoolean("SERVER.ENABLED", true)) {
            sender.sendMessage(CC.translate(Oxygen.getInstance().getConfigYML().getConfiguration().getString("GENERAL.DISABLED_COMMAND")));
            return;
        }

        if (!(sender instanceof ProxiedPlayer)) return;
        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (args.length == 0) {
            showCurrentServer(player);
        } else if (args.length == 1) {
            connectToServer(player, args[0]);
        } else {
            player.sendMessage(CC.translate(Oxygen.getInstance().getLangConfiguration().getString("SERVER_COMMAND.USAGE")));
        }
    }

    private void showCurrentServer(ProxiedPlayer player) {
        String currentServer = player.getServer().getInfo().getName();
        player.sendMessage(CC.translate(Oxygen.getInstance().getLangConfiguration().getString("SERVER_COMMAND.CONNECTED")
                .replace("%server%", currentServer)));

        String serversList = ProxyServer.getInstance().getServers().keySet().stream()
                .reduce(Oxygen.getInstance().getLangConfiguration().getString("SERVER_COMMAND.SERVERS"), (list, server) -> list + server + Oxygen.getInstance().getLangConfiguration().getString("SERVER_COMMAND.FORMAT"));

        player.sendMessage(CC.translate(serversList));
    }

    private void connectToServer(ProxiedPlayer player, String serverName) {
        ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(serverName);
        if (serverInfo == null) {
            player.sendMessage(CC.translate(Oxygen.getInstance().getLangConfiguration().getString("SERVER_COMMAND.INVALID_SERVER").replace("%server%", serverName)));
            return;
        }

        player.sendMessage(CC.translate(Oxygen.getInstance().getLangConfiguration().getString("SERVER_COMMAND.CONNECTING").replace("%server%", serverName)));
        player.connect(serverInfo);
    }
}
