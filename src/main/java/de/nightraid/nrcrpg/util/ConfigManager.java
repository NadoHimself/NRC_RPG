package de.nightraid.nrcrpg.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.nightraid.nrcrpg.NRCRPGPlugin;
import de.nightraid.nrcrpg.data.MainConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Manages plugin configuration
 */
public class ConfigManager {
    
    private final NRCRPGPlugin plugin;
    private final Gson gson;
    private MainConfig config;
    
    public ConfigManager(NRCRPGPlugin plugin) {
        this.plugin = plugin;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }
    
    /**
     * Load configuration files
     */
    public void loadConfigs() {
        Path configFile = plugin.getDataFolder().resolve("config.json");
        
        // Create config folder
        try {
            Files.createDirectories(plugin.getDataFolder());
        } catch (IOException e) {
            NRCRPGPlugin.getPluginLogger().error("Failed to create config folder!", e);
        }
        
        // Load or create config
        if (Files.exists(configFile)) {
            loadConfig(configFile);
        } else {
            createDefaultConfig(configFile);
        }
    }
    
    /**
     * Load config from file
     */
    private void loadConfig(Path configFile) {
        try {
            String json = Files.readString(configFile);
            this.config = gson.fromJson(json, MainConfig.class);
            NRCRPGPlugin.getPluginLogger().info("Loaded configuration from config.json");
        } catch (IOException e) {
            NRCRPGPlugin.getPluginLogger().error("Failed to load config!", e);
            this.config = new MainConfig();
        }
    }
    
    /**
     * Create default config
     */
    private void createDefaultConfig(Path configFile) {
        this.config = new MainConfig();
        
        try {
            String json = gson.toJson(config);
            Files.writeString(configFile, json);
            NRCRPGPlugin.getPluginLogger().info("Created default configuration");
        } catch (IOException e) {
            NRCRPGPlugin.getPluginLogger().error("Failed to create config!", e);
        }
    }
    
    // Config getters
    
    public double getXPMultiplier() {
        return config.xpMultiplier;
    }
    
    public int getMaxLevel() {
        return config.maxLevel;
    }
    
    public boolean areAbilitiesEnabled() {
        return config.enableAbilities;
    }
    
    public int getAutoSaveInterval() {
        return config.autoSaveInterval;
    }
    
    public boolean isDebugMode() {
        return config.debugMode;
    }
}