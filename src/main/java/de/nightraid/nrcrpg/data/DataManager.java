package de.nightraid.nrcrpg.data;

import com.hypixel.hytale.server.core.universe.PlayerRef;
import de.nightraid.nrcrpg.NRCRPGPlugin;

import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

/**
 * Manages loading and saving of player data
 */
public class DataManager {
    
    private final NRCRPGPlugin plugin;
    private final ConcurrentHashMap<String, PlayerRef> loadedPlayers;
    
    public DataManager(NRCRPGPlugin plugin) {
        this.plugin = plugin;
        this.loadedPlayers = new ConcurrentHashMap<>();
    }
    
    /**
     * Load player data from disk
     * @param playerRef The player to load data for
     */
    public void loadPlayerData(PlayerRef playerRef) {
        try {
            // TODO: Implement JSON file loading
            // File: plugins/nrc_rpg/players/<uuid>.json
            
            loadedPlayers.put(playerRef.getUsername(), playerRef);
            
            plugin.getLogger().at(Level.FINE).log(
                "Loaded data for " + playerRef.getUsername()
            );
        } catch (Exception e) {
            plugin.getLogger().at(Level.SEVERE).log(
                "Failed to load data for " + playerRef.getUsername(), e
            );
        }
    }
    
    /**
     * Save player data to disk
     * @param playerRef The player to save data for
     */
    public void savePlayerData(PlayerRef playerRef) {
        try {
            // TODO: Implement JSON file saving
            // File: plugins/nrc_rpg/players/<uuid>.json
            
            plugin.getLogger().at(Level.FINE).log(
                "Saved data for " + playerRef.getUsername()
            );
        } catch (Exception e) {
            plugin.getLogger().at(Level.SEVERE).log(
                "Failed to save data for " + playerRef.getUsername(), e
            );
        }
    }
    
    /**
     * Save all loaded player data
     */
    public void saveAllPlayers() {
        for (PlayerRef playerRef : loadedPlayers.values()) {
            savePlayerData(playerRef);
        }
        
        plugin.getLogger().at(Level.INFO).log(
            "Saved data for " + loadedPlayers.size() + " players"
        );
    }
}
