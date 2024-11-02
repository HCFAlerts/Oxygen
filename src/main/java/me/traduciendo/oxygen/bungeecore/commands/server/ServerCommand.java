package me.traduciendo.oxygen.bungeecore.commands.server;

import me.traduciendo.oxygen.Oxygen;
import me.traduciendo.oxygen.utils.CC;
import me.traduciendo.oxygen.utils.CreatorYML;
import net.md_5.bungee.api.CommandSender;
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
        super("server", "bungeecord.command.server");
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (!config.getConfiguration().getBoolean("SERVER.ENABLED", true)) {
            commandSender.sendMessage(CC.translate("&cThis command is currently disabled."));
            return;
        }

        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) commandSender;
            if (player.hasPermission("bungeecord.command.server")) {
                if (args.length == 0) {
                    player.sendMessage(CC.translate((config.getConfiguration().getString("SERVER.CONNECTED") + " " + player.getServer().getInfo().getName() + (config.getConfiguration().getString("SERVER.CONNECTED_FINAL")))));
                    StringBuilder servers = new StringBuilder(CC.translate((config.getConfiguration().getString("SERVER.SERVERS"))));
                    boolean first = true;
                    for (String name : Oxygen.getInstance().getProxy().getServers().keySet()) {
                        if (first) {
                            servers.append(name);
                        } else {
                            servers.append(CC.translate((config.getConfiguration().getString("SERVER.FORMAT")))).append(name);
                        }
                        first = false;
                    }
                    player.sendMessage(CC.translate(servers.toString()));
                    player.sendMessage(CC.translate((config.getConfiguration().getString("SERVER.USAGE"))));
                } else if (args.length == 1) {
                    ServerInfo si = Oxygen.getInstance().getProxy().getServerInfo(args[0]);
                    if (si != null) {
                        player.sendMessage(CC.translate((config.getConfiguration().getString("SERVER.MESSAGE") + " " + si.getName() + (config.getConfiguration().getString("SERVER.FINAL")))));
                        player.connect(si);
                    } else {
                        player.sendMessage(CC.translate((config.getConfiguration().getString("SERVER.NO_PERMISSION"))));
                    }
                }
            } else {
                player.sendMessage(CC.translate((config.getConfiguration().getString("SERVER.NO_PERMISSION"))));
            }
        }
    }
}
