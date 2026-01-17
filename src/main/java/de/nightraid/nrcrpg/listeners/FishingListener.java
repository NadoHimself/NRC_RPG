package de.nightraid.nrcrpg.listeners;

import de.nightraid.nrcrpg.NRCRPGPlugin;

import java.util.logging.Level;

/**
 * Listener for Fishing-related events
 * Grants Fishing XP for catching fish
 * 
 * NOTE: Temporarily disabled until Hytale Fishing API is confirmed
 */
public class FishingListener {
    
    private final NRCRPGPlugin plugin;
    
    public FishingListener(NRCRPGPlugin plugin) {
        this.plugin = plugin;
        registerEvents();
    }
    
    private void registerEvents() {
        // TODO: Register fishing events when API is available
        plugin.getLogger().at(Level.INFO).log("FishingListener registered (placeholder)");
    }
}
