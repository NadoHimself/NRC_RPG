package de.nightraid.nrcrpg.util;

import de.nightraid.nrcrpg.NRCRPGPlugin;

import java.io.File;
import java.util.logging.Level;

/**
 * Manages plugin configuration
 */
public class ConfigManager {
    
    private final NRCRPGPlugin plugin;
    private final File dataFolder;
    
    public ConfigManager(NRCRPGPlugin plugin) {
        this.plugin = plugin;
        
        // TODO: Find correct API for plugin data folder
        // Placeholder for now
        this.dataFolder = new File("plugins/nrc_rpg");
        
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
            plugin.getLogger().at(Level.INFO).log(
                "Created plugin data folder: " + dataFolder.getAbsolutePath()
            );
        }
    }
    
    public File getDataFolder() {
        return dataFolder;
    }
}
