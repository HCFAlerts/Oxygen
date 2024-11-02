package me.traduciendo.oxygen.bungeecore.handlers;

import me.traduciendo.oxygen.Oxygen;
import lombok.Getter;
import me.traduciendo.oxygen.utils.CreatorYML;

import java.util.*;

/**
 * @author Traduciendo
 * @BungeeUtils project
 * SRC and Jar available at dsc.gg/liteclubdevelopment
 * or github.com/HCFAlerts --> github.com/liteclubdevelopment
 */

@Getter
public class BungeeHandler {

    private final List<String> whitelists;
    private final CreatorYML config = Oxygen.getInstance().getConfigYML();
    
    public BungeeHandler() {
        this.whitelists = config.getConfiguration().getStringList("MAINTENANCE.PLAYERS");
    }
    
    public boolean isWhitelisted(String uuid) {
        return whitelists.contains(uuid);
    }
    
    public void setWhitelisted(String uuid, boolean whitelisted) {
        if (whitelisted) {
            if (!this.isWhitelisted(uuid)) {
                this.whitelists.add(uuid);
                config.getConfiguration().set("MAINTENANCE.PLAYERS", this.whitelists);
                config.save();
            }
        }
        else if (this.isWhitelisted(uuid)) {
            this.whitelists.remove(uuid);
            config.getConfiguration().set("MAINTENANCE.PLAYERS", this.whitelists);
            config.save();
        }
    }
}
