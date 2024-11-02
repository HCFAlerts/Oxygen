package me.traduciendo.oxygen.utils;

import com.google.common.io.ByteStreams;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;

/**
 * @author Traduciendo
 * @BungeeUtils project
 * SRC and Jar available at dsc.gg/liteclubdevelopment
 * or github.com/HCFAlerts --> github.com/liteclubdevelopment
 */

@Getter
public class CreatorYML {

    private final File file;
    private Configuration configuration;

    public CreatorYML(Plugin plugin, String fileName) {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        this.file = new File(plugin.getDataFolder(), fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();

                InputStream inputStream = plugin.getResourceAsStream(fileName);

                try {
                    OutputStream outputStream = new FileOutputStream(file);
                    ByteStreams.copy(inputStream, outputStream);
                }
                catch (IOException ex) {
                    plugin.getLogger().severe("Failed to copy default parameters in file " + fileName);
                }
            }
            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
        }
        catch (IOException ex) {
            plugin.getLogger().severe("Failed to create file " + fileName);
        }
    }

    public void save() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void reload() {
        try {
            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
