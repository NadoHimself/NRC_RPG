package de.nightraid.nrcrpg.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.nightraid.nrcrpg.NRCRPGPlugin;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;

public class ConfigManager {
    
    private final NRCRPGPlugin plugin;
    private final Gson gson;
    
    public ConfigManager(NRCRPGPlugin plugin) {
        this.plugin = plugin;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }
    
    public void loadConfigs() {
        try {
            // Create config directory if it doesn't exist
            File dataFolder = plugin.getPluginDataFolder().toFile();
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
                plugin.getLogger().at(Level.INFO).log("Created plugin data folder");
            }
            
            // Load config.json
            File configFile = new File(dataFolder, "config.json");
            if (!configFile.exists()) {
                // Create default config
                createDefaultConfig(configFile);
                plugin.getLogger().at(Level.INFO).log("Created default config.json");
            } else {
                plugin.getLogger().at(Level.INFO).log("Loaded config.json");
            }
            
        } catch (Exception e) {
            plugin.getLogger().at(Level.SEVERE).log("Failed to load configs", e);
        }
    }
    
    private void createDefaultConfig(File configFile) throws IOException {
        try (FileWriter writer = new FileWriter(configFile)) {
            writer.write("{\n");
            writer.write("  \"xp_multiplier\": 1.0,\n");
            writer.write("  \"max_level\": 100,\n");
            writer.write("  \"enable_abilities\": true,\n");
            writer.write("  \"auto_save_interval\": 300,\n");
            writer.write("  \"debug_mode\": false\n");
            writer.write("}\n");
        }
    }
    
    public int getAutoSaveInterval() {
        return 300; // 5 minutes default
    }
}
