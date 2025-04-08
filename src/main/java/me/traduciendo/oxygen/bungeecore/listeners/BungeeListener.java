package me.traduciendo.oxygen.bungeecore.listeners;

import me.traduciendo.oxygen.Oxygen;
import me.traduciendo.oxygen.utils.CC;
import me.traduciendo.oxygen.utils.CreatorYML;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Traduciendo
 * @BungeeUtils project
 * SRC and Jar available at dsc.gg/liteclubdevelopment
 * or github.com/HCFAlerts --> github.com/liteclubdevelopment
 */

public class BungeeListener implements Listener {

    private final CreatorYML config = Oxygen.getInstance().getConfigYML();

    @EventHandler(priority = 64)
    public void onPing(ProxyPingEvent event) {
        ServerPing response = event.getResponse();

        String onlinePlayers = String.valueOf(ProxyServer.getInstance().getOnlineCount());
        String maxOnlinePlayers = String.valueOf(config.getConfiguration().getInt("SLOTS"));

        if (config.getConfiguration().getBoolean("MAINTENANCE.ENABLED")) {
            if (config.getConfiguration().getBoolean("MAINTENANCE.PING-TEXT-ENABLED")) {
                response.setVersion(new ServerPing.Protocol(
                        CC.translate(Oxygen.getInstance().getConfigYML().getConfiguration().getString("MAINTENANCE.PING-TEXT")),
                        9999
                ));
            }

            if (config.getConfiguration().getBoolean("MAINTENANCE.ENABLED")) {
                if (config.getConfiguration().getBoolean("MAINTENANCE.PING-LORE-ENABLED")) {
                    List<String> hoverText = config.getConfiguration().getStringList("MAINTENANCE.PING-LORE")
                            .stream()
                            .map(line -> line
                                    .replace("%time%", Oxygen.getInstance().getTime())
                                    .replace("%servername%", Oxygen.getInstance().getConfiguration().getString("SOCIAL.SERVER_NAME"))
                                    .replace("%discord%", Oxygen.getInstance().getConfiguration().getString("SOCIAL.DISCORD"))
                                    .replace("%store%", Oxygen.getInstance().getConfiguration().getString("SOCIAL.STORE"))
                                    .replace("%teamspeak%", Oxygen.getInstance().getConfiguration().getString("SOCIAL.TEAMSPEAK"))
                                    .replace("%twitter%", Oxygen.getInstance().getConfiguration().getString("SOCIAL.TWITTER"))
                                    .replace("%website%", Oxygen.getInstance().getConfiguration().getString("SOCIAL.WEBSITE"))
                                    .replace("%online%", onlinePlayers)
                                    .replace("%maxonline%", maxOnlinePlayers)
                            )
                            .map(CC::translate)
                            .collect(Collectors.toList());

                        response.setPlayers(new ServerPing.Players(Integer.parseInt(maxOnlinePlayers), ProxyServer.getInstance().getOnlineCount(), new ServerPing.PlayerInfo[0]));
                        response.getPlayers().setSample(hoverText.stream()
                                .map(text -> new ServerPing.PlayerInfo(text, UUID.randomUUID()))
                                .toArray(ServerPing.PlayerInfo[]::new)
                        );
                }
            }
        } else {
            if (config.getConfiguration().getBoolean("NO-MAINTENANCE-PING-TEXT-ENABLED")) {
                response.setVersion(new ServerPing.Protocol(
                        CC.translate(Oxygen.getInstance().getConfigYML().getConfiguration().getString("NO-MAINTENANCE.PING-TEXT")),
                        response.getVersion().getProtocol()
                ));
            }

            if (config.getConfiguration().getBoolean("NO-MAINTENANCE-PING-LORE-ENABLED")) {
                List<String> hoverText = config.getConfiguration().getStringList("NO-MAINTENANCE-PING-LORE")
                        .stream()
                        .map(line -> line
                                .replace("%time%", Oxygen.getInstance().getTime())
                                .replace("%servername%", Oxygen.getInstance().getConfiguration().getString("SOCIAL.SERVER_NAME"))
                                .replace("%discord%", Oxygen.getInstance().getConfiguration().getString("SOCIAL.DISCORD"))
                                .replace("%store%", Oxygen.getInstance().getConfiguration().getString("SOCIAL.STORE"))
                                .replace("%teamspeak%", Oxygen.getInstance().getConfiguration().getString("SOCIAL.TEAMSPEAK"))
                                .replace("%twitter%", Oxygen.getInstance().getConfiguration().getString("SOCIAL.TWITTER"))
                                .replace("%website%", Oxygen.getInstance().getConfiguration().getString("SOCIAL.WEBSITE"))
                                .replace("%online%", onlinePlayers)
                                .replace("%maxonline%", maxOnlinePlayers)
                        )
                        .map(CC::translate)
                        .collect(Collectors.toList());

                response.setPlayers(new ServerPing.Players(Integer.parseInt(maxOnlinePlayers), ProxyServer.getInstance().getOnlineCount(), new ServerPing.PlayerInfo[0]));
                response.getPlayers().setSample(hoverText.stream()
                        .map(text -> new ServerPing.PlayerInfo(text, UUID.randomUUID()))
                        .toArray(ServerPing.PlayerInfo[]::new)
                );
            }
        }

        event.setResponse(response);
    }

    @EventHandler
    public void onKick(ServerKickEvent event) {
        ProxiedPlayer p = event.getPlayer();
        Random random = new Random();
        int rand = random.nextInt(1) + 1;
        ServerInfo connect = BungeeCord.getInstance().getServerInfo(config.getConfiguration().getString("MAINTENANCE.HUB-NAME") + rand);

        String reason = event.getKickReason();
        String serverName = event.getKickedFrom().getName();

        String message = CC.translate(config.getConfiguration().getStringList("KICK.MESSAGE")
                .stream()
                .map(line -> line.replace("%reason%", reason).replace("%server%", serverName))
                .collect(Collectors.joining("\n"))
        );

        if (event.getKickedFrom() == connect) {
            p.disconnect(message);
        } else {
            event.setCancelServer(connect);
            event.setCancelled(true);

            BungeeCord.getInstance().getScheduler().schedule(Oxygen.getInstance(), () -> p.sendMessage(message), 2L, TimeUnit.SECONDS);
        }
    }

    @EventHandler
    public void onJoin(LoginEvent event) {
        CreatorYML config = Oxygen.getInstance().getConfigYML();
        if (config.getConfiguration().getBoolean("MAINTENANCE.ENABLED") && !Oxygen.getInstance().getBungeeHandler().isWhitelisted(event.getConnection().getUniqueId().toString())) {
            event.setCancelled(true);
            event.setCancelReason(CC.translate(config.getConfiguration().getStringList("MAINTENANCE.KICK-MESSAGE"))
                    .stream()
                    .map(line -> line
                    .replace("%servername%", Oxygen.getInstance().getConfiguration().getString("SOCIAL.SERVER_NAME"))
                    .replace("%time%", Oxygen.getInstance().getTime())
                    .replace("%discord%", Oxygen.getInstance().getConfiguration().getString("SOCIAL.DISCORD"))
                    .replace("%store%", Oxygen.getInstance().getConfiguration().getString("SOCIAL.STORE"))
                    .replace("%teamspeak%", Oxygen.getInstance().getConfiguration().getString("SOCIAL.TEAMSPEAK"))
                    .replace("%twitter%", Oxygen.getInstance().getConfiguration().getString("SOCIAL.TWITTER"))
                    .replace("%website%", Oxygen.getInstance().getConfiguration().getString("SOCIAL.WEBSITE")))
                    .collect(Collectors.joining("\n")));
        }
    }
}