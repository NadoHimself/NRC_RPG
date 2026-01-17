package de.nightraid.nrcrpg.managers;

import de.nightraid.nrcrpg.NRCRPGPlugin;

import java.io.File;
import java.util.logging.Level;

public class DataManager {
    
    private final NRCRPGPlugin plugin;
    private final File playersFolder;
    
    public DataManager(NRCRPGPlugin plugin) {
        this.plugin = plugin;
        this.playersFolder = new File(plugin.getPluginDataFolder().toFile(), "players");
        
        if (!playersFolder.exists()) {
            playersFolder.mkdirs();
            plugin.getLogger().at(Level.INFO).log("Created players data folder");
        }
    }
    
    public void loadPlayerData(Object playerRef) {
        try {
            // TODO: Implement JSON loading from players/<uuid>.json
            plugin.getLogger().at(Level.INFO).log("Loading player data...");
        } catch (Exception e) {
            plugin.getLogger().at(Level.SEVERE).log("Failed to load player data", e);
        }
    }
    
    public void savePlayerData(Object playerRef) {
        try {
            // TODO: Implement JSON saving to players/<uuid>.json
            plugin.getLogger().at(Level.INFO).log("Saving player data...");
        } catch (Exception e) {
            plugin.getLogger().at(Level.SEVERE).log("Failed to save player data", e);
        }
    }
    
    public void saveAllPlayerData() {
        try {
            // TODO: Iterate through all online players and save
            plugin.getLogger().at(Level.INFO).log("Saved all player data");
        } catch (Exception e) {
            plugin.getLogger().at(Level.SEVERE).log("Failed to save all player data", e);
        }
    }
}
