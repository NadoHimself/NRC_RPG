package de.nightraid.nrcrpg.listeners;

import de.nightraid.nrcrpg.NRCRPGPlugin;

import java.util.logging.Level;

/**
 * Listener for Farming-related events
 * Grants Farming XP for harvesting crops and breeding animals
 * 
 * NOTE: Temporarily disabled until Hytale Block/Entity API is confirmed
 */
public class FarmingListener {
    
    private final NRCRPGPlugin plugin;
    
    public FarmingListener(NRCRPGPlugin plugin) {
        this.plugin = plugin;
        registerEvents();
    }
    
    private void registerEvents() {
        // TODO: Register crop harvest and animal breeding events
        plugin.getLogger().at(Level.INFO).log("FarmingListener registered (placeholder)");
    }
}
