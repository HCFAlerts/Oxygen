package me.traduciendo.oxygen;

import me.traduciendo.oxygen.bungeecore.commands.media.*;
import me.traduciendo.oxygen.bungeecore.commands.server.*;
import me.traduciendo.oxygen.utils.CC;
import lombok.Getter;
import lombok.Setter;
import me.traduciendo.oxygen.bungeecore.handlers.BungeeHandler;
import me.traduciendo.oxygen.bungeecore.listeners.BungeeListener;
import me.traduciendo.oxygen.utils.CreatorYML;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * @author Traduciendo
 * @Oxygen project
 * SRC and Jar available at dsc.gg/liteclubdevelopment
 * or github.com/HCFAlerts --> github.com/liteclubdevelopment
 */

@Getter
@Setter
public class Oxygen extends Plugin implements Listener {
    @Getter
    private static Oxygen instance;
    private BungeeHandler bungeeHandler;
    private ScheduledTask scheduledTask;
    private JDA discordBot;
    private CreatorYML configYML, langYML, commandsYML, playersYML;
    private Configuration config, langConfig, commandsConfig, playersConfig;
    private ScheduledTask countdown;

    public void log(String s) {
        BungeeCord.getInstance().getConsole().sendMessage(CC.translate(s));
    }

    public void onEnable() {
        instance = this;
        this.files();
        this.handlers();
        this.listeners();
        this.loadConfig();
        Theme.loadColors();
        this.commands();
        this.getProxy().getPluginManager().registerListener(this, this);

        log(Theme.getSecondaryColor() + "&m==============================");
        log(Theme.getPrimaryColor() + "Oxygen Core &8- &fv" + getDescription().getVersion());
        log("");
        log(Theme.getSecondaryColor() + "┃ " + Theme.getPrimaryColor() + "Author&f: Traduciendo");
        log(Theme.getSecondaryColor() + "┃ " + Theme.getPrimaryColor() + "State&f: &aEnabled");
        log("");
        log("&7&oThank you for using Oxygen Bungee Core");
        log("&7&oJoin our Discord dsc.gg/liteclubdevelopment");
        log(Theme.getSecondaryColor() + "&m==============================");

        this.startCountdown();

        String onlinePlayers = String.valueOf(ProxyServer.getInstance().getOnlineCount());
        String maxOnlinePlayers = String.valueOf(getConfigYML().getConfiguration().getInt("SLOTS"));
        String discordToken = getInstance().getConfig().getString("DISCORD-BOT.TOKEN");
        String activityType = getInstance().getConfig().getString("DISCORD-BOT.ACTIVITY.TYPE").toUpperCase();
        String activityText = getInstance().getConfig().getString("DISCORD-BOT.ACTIVITY.ACTIVITY")
                .replace("%online%", onlinePlayers)
                .replace("%maxonline%", maxOnlinePlayers)
                .replace("%time%", getTime())
                .replace("%servername%", getConfiguration().getString("SOCIAL.SERVER_NAME"))
                .replace("%discord%", getConfiguration().getString("SOCIAL.DISCORD"))
                .replace("%store%", getConfiguration().getString("SOCIAL.STORE"))
                .replace("%teamspeak%", getConfiguration().getString("SOCIAL.TEAMSPEAK"))
                .replace("%twitter%", getConfiguration().getString("SOCIAL.TWITTER"))
                .replace("%website%", getConfiguration().getString("SOCIAL.WEBSITE"))
                .replace("&", "");
        String streamUrl = getInstance().getConfig().getString("DISCORD-BOT.ACTIVITY.STREAM_URL", "https://twitch.tv/liteclubdevelopment");
        OnlineStatus status = OnlineStatus.valueOf(getInstance().getConfig().getString("DISCORD-BOT.ACTIVITY.STATUS").toUpperCase());

        Activity activity;
        switch (activityType) {
            case "PLAYING":
                activity = Activity.playing(activityText);
                break;
            case "WATCHING":
                activity = Activity.watching(activityText);
                break;
            case "LISTENING":
                activity = Activity.listening(activityText);
                break;
            case "STREAMING":
                activity = Activity.streaming(activityText, streamUrl);
                break;
            case "COMPETING":
                activity = Activity.competing(activityText);
                break;
            default:
                activity = Activity.playing(activityText);
                break;
        }

        if (getInstance().getConfig().getBoolean("DISCORD-BOT.ENABLED")) {
            if (getInstance().getConfig().getString("DISCORD-BOT.TOKEN").isEmpty()) {
                log("&4&m===============&c&l[ERROR]&4&m===============");
                log("             &c&lOxygen Core&r");
                log("");
                log("&cDiscord Bot Module - Invalid Token");
                log("");
                log(" &4┃ &cPut the Bot Token in config.yml");
                log(" &4┃ &cDisabling Discord Bot Module...");
                log("");
                log("&c&oJoin our Discord dsc.gg/liteclubdevelopment");
                log("&4&m===============&c&l[ERROR]&4&m===============");
                return;
            } else if (getInstance().getConfig().getString("DISCORD-BOT.TOKEN").equals("INSERT-YOUR-DISCORD-BOT-TOKEN-HERE")) {
                log("&4&m===============&c&l[ERROR]&4&m===============");
                log("             &c&lOxygen Core&r");
                log("");
                log("&cDiscord Bot Module - Token not defined");
                log("");
                log(" &4┃ &cTo use this feature you need");
                log(" &4┃ &cPut the Bot Token in config.yml");
                log("  &4┃ &cDisabling Discord Bot Module...");
                log("");
                log("&c&oJoin our Discord dsc.gg/liteclubdevelopment");
                log("&4&m===============&c&l[ERROR]&4&m===============");
                return;
            }

            this.discordBot = JDABuilder.createDefault(discordToken)
                    .setStatus(status)
                    .setActivity(activity)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS, new GatewayIntent[0])
                    .build();

            int updateInterval = getConfig().getInt("DISCORD-BOT.UPDATE_INTERVAL", 10);

            this.getProxy().getScheduler().schedule(this, () -> {
                String updatedActivityText = activityText
                        .replace("%online%", onlinePlayers)
                        .replace("%maxonline%", maxOnlinePlayers)
                        .replace("%time%", getTime())
                        .replace("%servername%", getConfiguration().getString("SOCIAL.SERVER_NAME"))
                        .replace("%discord%", getConfiguration().getString("SOCIAL.DISCORD"))
                        .replace("%store%", getConfiguration().getString("SOCIAL.STORE"))
                        .replace("%teamspeak%", getConfiguration().getString("SOCIAL.TEAMSPEAK"))
                        .replace("%twitter%", getConfiguration().getString("SOCIAL.TWITTER"))
                        .replace("%website%", getConfiguration().getString("SOCIAL.WEBSITE"))
                        .replace("&", "");
                Activity updatedActivity = Activity.playing(updatedActivityText);
                discordBot.getPresence().setActivity(updatedActivity);
            }, 0, updateInterval, TimeUnit.SECONDS);
        }
    }

    protected Configuration getConfig() {
        return this.config;
    }

    public Configuration getConfiguration() {
        return this.config;
    }

    protected Configuration getLangConfig() {
        return this.langConfig;
    }

    public Configuration getLangConfiguration() {
        return this.langConfig;
    }

    protected Configuration getCommandsConfig() {
        return this.commandsConfig;
    }

    public Configuration getCommandsConfiguration() {
        return this.commandsConfig;
    }

    protected Configuration getPlayersConfig() {
        return this.playersConfig;
    }

    public void reloadConfig() {
        this.loadConfig();
        this.saveConfig();
    }

    protected void saveConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.getConfig(), new File(this.getDataFolder(), "config.yml"));
        } catch (IOException e) {
            throw new RuntimeException("Unable to save main configuration", e);
        }
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.getLangConfig(), new File(this.getDataFolder(), "lang.yml"));
        } catch (IOException e) {
            throw new RuntimeException("Unable to save lang configuration", e);
        }
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.getCommandsConfig(), new File(this.getDataFolder(), "commands.yml"));
        } catch (IOException e) {
            throw new RuntimeException("Unable to save lang configuration", e);
        }
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.getPlayersConfig(), new File(this.getDataFolder(), "whitelisted-players.yml"));
        } catch (IOException e) {
            throw new RuntimeException("Unable to save lang configuration", e);
        }
    }

    private void loadConfig() {
        File file = new File(this.getDataFolder(), "config.yml");
        if (!file.exists()) {
            if (!this.getDataFolder().exists()) {
                this.getDataFolder().mkdir();
            }
            try (InputStream in = this.getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        file = new File(this.getDataFolder(), "lang.yml");
        if (!file.exists()) {
            if (!this.getDataFolder().exists()) {
                this.getDataFolder().mkdir();
            }
            try (InputStream in = this.getResourceAsStream("lang.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            this.langConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        file = new File(this.getDataFolder(), "commands.yml");
        if (!file.exists()) {
            if (!this.getDataFolder().exists()) {
                this.getDataFolder().mkdir();
            }
            try (InputStream in = this.getResourceAsStream("commands.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            this.commandsConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        file = new File(this.getDataFolder(), "whitelisted-players.yml");
        if (!file.exists()) {
            if (!this.getDataFolder().exists()) {
                this.getDataFolder().mkdir();
            }
            try (InputStream in = this.getResourceAsStream("whitelisted-players.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            this.playersConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void files() {
        this.configYML = new CreatorYML(this, "config.yml");
        this.langYML = new CreatorYML(this, "lang.yml");
        this.commandsYML = new CreatorYML(this, "commands.yml");
        this.playersYML = new CreatorYML(this, "whitelisted-players.yml");
    }

    private void handlers() {
        this.bungeeHandler = new BungeeHandler();
    }

    private void listeners() {
        this.getProxy().getPluginManager().registerListener(this, new BungeeListener());
    }

    private void commands() {
        this.getProxy().getPluginManager().registerCommand(this, new OxygenCommand());
        this.getProxy().getPluginManager().registerCommand(this, new HubCommand());
        this.getProxy().getPluginManager().registerCommand(this, new MaintenanceCommand());
        this.getProxy().getPluginManager().registerCommand(this, new FindCommand());
        this.getProxy().getPluginManager().registerCommand(this, new ServerCommand());
        this.getProxy().getPluginManager().registerCommand(this, new TeamSpeakCommand());
        this.getProxy().getPluginManager().registerCommand(this, new DiscordCommand());
        this.getProxy().getPluginManager().registerCommand(this, new TwitterCommand());
        this.getProxy().getPluginManager().registerCommand(this, new WebsiteCommand());
        this.getProxy().getPluginManager().registerCommand(this, new StoreCommand());
        this.getProxy().getPluginManager().registerCommand(this, new HelpCommand());
    }

    public void whitelist(boolean enabled) {
        configYML.getConfiguration().set("MAINTENANCE.ENABLED", enabled);
        configYML.save();
        configYML.reload();
    }

    @EventHandler
    public void onPing(ProxyPingEvent e) {
        ServerPing sp = e.getResponse();
        String message = this.config.getString("MOTD")
                .replaceAll("%time%", getTime())
                .replaceAll("%newline%", "\n")
                .replaceAll("%primary%", Theme.getPrimaryColor())
                .replaceAll("%secondary%", Theme.getSecondaryColor())
                .replaceAll("%middle%", Theme.getMiddleColor())
                .replaceAll("%discord%", Oxygen.getInstance().getConfiguration().getString("SOCIAL.DISCORD"))
                .replaceAll("%store%", Oxygen.getInstance().getConfiguration().getString("SOCIAL.STORE"))
                .replaceAll("%teamspeak%", Oxygen.getInstance().getConfiguration().getString("SOCIAL.TEAMSPEAK"))
                .replaceAll("%twitter%", Oxygen.getInstance().getConfiguration().getString("SOCIAL.TWITTER"))
                .replaceAll("%website%", Oxygen.getInstance().getConfiguration().getString("SOCIAL.WEBSITE"))
                .replaceAll("%servername%", Oxygen.getInstance().getConfiguration().getString("SOCIAL.SERVER_NAME"));
        sp.getPlayers().setMax(this.config.getInt("SLOTS"));
        sp.setDescription(ChatColor.translateAlternateColorCodes('&', message));
        e.setResponse(sp);
    }

    public String getTime() {
        Configuration config = this.getConfig();
        String dateStop = config.getString("TIME.DATE");
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
        Date date = null;

        try {
            format.setTimeZone(TimeZone.getTimeZone(config.getString("TIME.TIMEZONE")));
            date = format.parse(dateStop);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date current = new Date();
        long diff = date.getTime() - current.getTime();

        if (diff < 0L) {
            return config.getString("END.TEXT")
                    .replace("%primary%", Theme.getPrimaryColor())
                    .replace("%middle%", Theme.getMiddleColor())
                    .replace("%secondary%", Theme.getSecondaryColor());
        } else {
            long days = TimeUnit.MILLISECONDS.toDays(diff);
            long hours = TimeUnit.MILLISECONDS.toHours(diff) % 24;
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diff) % 60;
            long seconds = TimeUnit.MILLISECONDS.toSeconds(diff) % 60;

            StringBuilder sb = new StringBuilder();

                if (days > 0) {
                    sb.append(days).append(config.getString(days == 1 ? "TIMER.DAY" : "TIMER.DAYS"));
                }

                if (hours > 0) {
                    if (sb.length() > 0) sb.append(" ");
                    sb.append(hours).append(config.getString(hours == 1 ? "TIMER.HOUR" : "TIMER.HOURS"));
                }

                if (minutes > 0) {
                    if (sb.length() > 0) sb.append(" ");
                    sb.append(minutes).append(config.getString(minutes == 1 ? "TIMER.MINUTE" : "TIMER.MINUTES"));
                }

                if (seconds > 0) {
                    if (sb.length() > 0) sb.append(" ");
                    sb.append(seconds).append(config.getString(seconds == 1 ? "TIMER.SECOND" : "TIMER.SECONDS"));
                }

            return sb.toString();
        }
    }

    public void startCountdown() {
        this.scheduledTask = this.getProxy().getScheduler().schedule(this, () -> {
            if (getCountdown() == 0) {
                this.getProxy().getScheduler().cancel(this.scheduledTask);
                this.getProxy().getPluginManager().dispatchCommand(this.getProxy().getConsole(), this.config.getString("END.COMMAND"));
                System.out.println("End Command has been successfully executed!");
            }
        }, 0L, 1L, TimeUnit.SECONDS);
    }

    public int getCountdown() {
        String dateStop = this.config.getString("TIME.DATE");
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone(this.config.getString("TIME.TIMEZONE")));
        try {
            Date date = format.parse(dateStop);
            long diff = date.getTime() - new Date().getTime();
            return (int) (diff > 0 ? TimeUnit.MILLISECONDS.toSeconds(diff) : 0);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void onDisable() {
        getPlayersYML().getConfiguration().set("PLAYERS", this.bungeeHandler.getWhitelists());
        log(Theme.getSecondaryColor() + "&m==============================");
        log(Theme.getPrimaryColor() + "Oxygen Core &8- &fv" + getDescription().getVersion());
        log("");
        log(Theme.getSecondaryColor() + "┃ " + Theme.getPrimaryColor() + "Author&f: Traduciendo");
        log(Theme.getSecondaryColor() + "┃ " + Theme.getPrimaryColor() + "State&f: &cDisabled");
        log("");
        log("&7&oThank you for using Oxygen Bungee Core");
        log("&7&oJoin our Discord dsc.gg/liteclubdevelopment");
        log(Theme.getSecondaryColor() + "&m==============================");
        this.reloadConfig();
    }
}