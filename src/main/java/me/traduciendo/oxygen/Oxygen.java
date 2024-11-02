package me.traduciendo.oxygen;

import me.traduciendo.oxygen.bungeecore.commands.media.*;
import me.traduciendo.oxygen.bungeecore.commands.server.*;
import me.traduciendo.oxygen.utils.CC;
import lombok.Getter;
import lombok.Setter;
import me.traduciendo.oxygen.bungeecore.handlers.BungeeHandler;
import me.traduciendo.oxygen.bungeecore.listeners.BungeeListener;
import me.traduciendo.oxygen.utils.CreatorYML;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
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
import java.nio.file.CopyOption;
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
    private CreatorYML configYML;
    private Configuration config;
    private ScheduledTask countdown;

    public void onEnable() {
        Oxygen.instance = this;
        this.files();
        this.handlers();
        this.listeners();
        this.commands();
        this.getProxy().getPluginManager().registerListener(this, this);
        this.getProxy().getPluginManager().registerCommand(this, new OxygenCommand(this));

        BungeeCord.getInstance().getConsole().sendMessage(CC.translate("&3&m=============================="));
        BungeeCord.getInstance().getConsole().sendMessage(CC.translate("&bOxygen Core &8- &fv" + getDescription().getVersion()));
        BungeeCord.getInstance().getConsole().sendMessage(CC.translate(""));
        BungeeCord.getInstance().getConsole().sendMessage(CC.translate("&3┃ &bAuthor&f: Traduciendo"));
        BungeeCord.getInstance().getConsole().sendMessage(CC.translate("&3┃ &bState&f: &aEnabled"));
        BungeeCord.getInstance().getConsole().sendMessage(CC.translate(""));
        BungeeCord.getInstance().getConsole().sendMessage(CC.translate("&7&oThank you for using Oxygen Bungee Core"));
        BungeeCord.getInstance().getConsole().sendMessage(CC.translate("&7&oJoin our Discord dsc.gg/liteclubdevelopment"));
        BungeeCord.getInstance().getConsole().sendMessage(CC.translate("&3&m=============================="));

        this.loadConfig();
        this.startCountdown();
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
            File file = new File(this.getDataFolder(), "config.yml");
            if (!file.exists()) {
                try {
                    Object t = null;

                    try {
                        InputStream in = this.getResourceAsStream("config.yml");

                        try {
                            Files.copy(in, file.toPath(), new CopyOption[0]);
                        } finally {
                            if (in != null) {
                                in.close();
                            }

                        }
                    } finally {
                        Object t2;
                        if (t == null) {
                            t2 = null;
                        } else {
                            t2 = null;
                            if (t != t2) {
                                ((Throwable)t).addSuppressed((Throwable)t2);
                            }
                        }

                    }
                } catch (IOException var15) {
                    var15.printStackTrace();
                }
            }
        }

        this.saveDefaultConfig();
        this.reloadConfig();
    }

    protected Configuration getConfig() {
        return this.config;
    }

    public void reloadConfig() {
        try {
            this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(this.getDataFolder(), "config.yml"));
        } catch (IOException var2) {
            throw new RuntimeException("Unable to load configuration", var2);
        }
    }

    protected void saveConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.getConfig(), new File(this.getDataFolder(), "config.yml"));
        } catch (IOException var2) {
            throw new RuntimeException("Unable to save configuration", var2);
        }
    }

    private void saveDefaultConfig() {
    }

    public void onDisable() {
        configYML.getConfiguration().set("WHITELIST.PLAYERS", this.bungeeHandler.getWhitelists());
        BungeeCord.getInstance().getConsole().sendMessage(CC.translate("&3&m=============================="));
        BungeeCord.getInstance().getConsole().sendMessage(CC.translate("&bOxygen Core &8- &fv" + getDescription().getVersion()));
        BungeeCord.getInstance().getConsole().sendMessage(CC.translate(""));
        BungeeCord.getInstance().getConsole().sendMessage(CC.translate("&3┃ &bAuthor&f: Traduciendo"));
        BungeeCord.getInstance().getConsole().sendMessage(CC.translate("&3┃ &bState&f: &cDisabled"));
        BungeeCord.getInstance().getConsole().sendMessage(CC.translate(""));
        BungeeCord.getInstance().getConsole().sendMessage(CC.translate("&7&oThank you for using Oxygen Bungee Core"));
        BungeeCord.getInstance().getConsole().sendMessage(CC.translate("&7&oJoin our Discord dsc.gg/liteclubdevelopment"));
        BungeeCord.getInstance().getConsole().sendMessage(CC.translate("&3&m=============================="));
    }

    private void files() {
        this.configYML = new CreatorYML(this, "config.yml");
    }

    private void handlers() {
        this.bungeeHandler = new BungeeHandler();
    }

    private void listeners() {
        this.getProxy().getPluginManager().registerListener(this, new BungeeListener());
    }

    private void commands() {
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
    }

    @EventHandler
    public void onPing(ProxyPingEvent e) {
        ServerPing sp = e.getResponse();
        String message = this.config.getString("MOTD")
                .replaceAll("%time%", getTime())
                .replaceAll("%newline%", "\n");
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
            return config.getString("END.TEXT");
        } else {
            long days;
            long hours;
            long minutes;
            long seconds;
            long rhours;
            long rminutes;
            long rseconds;

            if (config.getInt("CLOCK-TYPE") == 1) {
                days = TimeUnit.MILLISECONDS.toDays(diff);
                hours = TimeUnit.MILLISECONDS.toHours(diff);
                minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
                seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
                rhours = days == 0L ? hours : hours % (days * 24L);
                rminutes = hours == 0L ? minutes : minutes % (hours * 60L);
                rseconds = minutes == 0L ? seconds : seconds % (minutes * 60L);

                StringBuilder sb = new StringBuilder();

                if (days > 1L) {
                    sb.append(days).append(config.getString("DAYS"));
                }
                if (days == 1L) {
                    sb.append(days).append(config.getString("DAY"));
                }
                if (rhours > 1L) {
                    sb.append((days > 0L ? " " : "")).append(rhours).append(config.getString("HOURS"));
                }
                if (rhours == 1L) {
                    sb.append((days > 0L ? " " : "")).append(rhours).append(config.getString("HOUR"));
                }
                if (rminutes > 1L) {
                    sb.append((days <= 0L && hours <= 0L ? "" : " ")).append(rminutes).append(config.getString("MINUTES"));
                }
                if (rminutes == 1L) {
                    sb.append((days <= 0L && hours <= 0L ? "" : " ")).append(rminutes).append(config.getString("MINUTE"));
                }
                if (rseconds > 1L) {
                    sb.append((days <= 0L && hours <= 0L && minutes <= 0L ? "" : " ")).append(rseconds).append(config.getString("SECONDS"));
                }
                if (rseconds == 1L) {
                    sb.append((days <= 0L && hours <= 0L && minutes <= 0L ? "" : " ")).append(rseconds).append(config.getString("SECOND"));
                }

                return sb.toString();
            } else if (config.getInt("CLOCK-TYPE") == 2) {
                days = TimeUnit.MILLISECONDS.toSeconds(diff);
                hours = TimeUnit.MILLISECONDS.toMinutes(diff);
                minutes = TimeUnit.MILLISECONDS.toHours(diff);
                seconds = TimeUnit.MILLISECONDS.toDays(diff);
                rhours = seconds == 0L ? minutes : minutes % (seconds * 24L);
                rminutes = minutes == 0L ? hours : hours % (minutes * 60L);
                rseconds = hours == 0L ? days : days % (hours * 60L);

                return days + ":" + rhours + ":" + rminutes + ":" + rseconds;
            } else {
                return null;
            }
        }
    }

    public void startCountdown() {
        this.scheduledTask = this.getProxy().getScheduler().schedule(this, () -> {
            if (getCountdown() == 0) {
                this.getProxy().getScheduler().cancel(this.scheduledTask);
                this.getProxy().getPluginManager().dispatchCommand(this.getProxy().getConsole(), this.config.getString("END.COMMAND"));
                System.out.println("Finish Command has been successfully executed!");
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
}