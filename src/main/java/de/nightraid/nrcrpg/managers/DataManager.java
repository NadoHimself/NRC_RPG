package de.nightraid.nrcrpg.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.nightraid.nrcrpg.NRCRPGPlugin;
import de.nightraid.nrcrpg.data.PlayerData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

/**
 * Manages player data persistence
 */
public class DataManager {
    
    private final NRCRPGPlugin plugin;
    private final Path playersFolder;
    private final Gson gson;
    
    public DataManager(NRCRPGPlugin plugin) {
        this.plugin = plugin;
        this.playersFolder = plugin.getDataFolder().resolve("players");
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        
        // Create players folder
        try {
            Files.createDirectories(playersFolder);
        } catch (IOException e) {
            NRCRPGPlugin.getPluginLogger().error("Failed to create players folder!", e);
        }
    }
    
    /**
     * Load player data from file
     */
    public PlayerData loadPlayerData(UUID uuid) {
        Path playerFile = getPlayerFile(uuid);
        
        if (!Files.exists(playerFile)) {
            return new PlayerData(uuid); // New player
        }
        
        try {
            String json = Files.readString(playerFile);
            PlayerData data = gson.fromJson(json, PlayerData.class);
            NRCRPGPlugin.getPluginLogger().debug("Loaded data for player {}", uuid);
            return data;
        } catch (IOException e) {
            NRCRPGPlugin.getPluginLogger().error("Failed to load data for player {}!", uuid, e);
            return new PlayerData(uuid);
        }
    }
    
    /**
     * Save player data to file
     */
    public void savePlayerData(PlayerData data) {
        Path playerFile = getPlayerFile(data.getUuid());
        
        try {
            String json = gson.toJson(data);
            Files.writeString(playerFile, json);
            NRCRPGPlugin.getPluginLogger().debug("Saved data for player {}", data.getUuid());
        } catch (IOException e) {
            NRCRPGPlugin.getPluginLogger().error("Failed to save data for player {}!", data.getUuid(), e);
        }
    }
    
    /**
     * Save all online player data
     */
    public void saveAllPlayerData() {
        SkillManager skillManager = plugin.getSkillManager();
        int saved = 0;
        
        for (UUID uuid : skillManager.getCachedPlayers()) {
            PlayerData data = skillManager.getPlayerData(uuid);
            savePlayerData(data);
            saved++;
        }
        
        NRCRPGPlugin.getPluginLogger().info("Saved data for {} players", saved);
    }
    
    /**
     * Delete player data
     */
    public boolean deletePlayerData(UUID uuid) {
        Path playerFile = getPlayerFile(uuid);
        
        try {
            boolean deleted = Files.deleteIfExists(playerFile);
            if (deleted) {
                NRCRPGPlugin.getPluginLogger().info("Deleted data for player {}", uuid);
            }
            return deleted;
        } catch (IOException e) {
            NRCRPGPlugin.getPluginLogger().error("Failed to delete data for player {}!", uuid, e);
            return false;
        }
    }
    
    /**
     * Get player file path
     */
    private Path getPlayerFile(UUID uuid) {
        return playersFolder.resolve(uuid.toString() + ".json");
    }
    
    /**
     * Check if player data exists
     */
    public boolean hasPlayerData(UUID uuid) {
        return Files.exists(getPlayerFile(uuid));
    }
}