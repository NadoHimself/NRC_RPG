package de.nightraid.nrcrpg.listeners;

import de.nightraid.nrcrpg.NRCRPGPlugin;

import java.util.logging.Level;

/**
 * Listener for Mining-related events
 * Grants Mining XP for breaking ore blocks
 * 
 * NOTE: Temporarily disabled until Hytale Block API is confirmed
 */
public class MiningListener {
    
    private final NRCRPGPlugin plugin;
    
    public MiningListener(NRCRPGPlugin plugin) {
        this.plugin = plugin;
        registerEvents();
    }
    
    private void registerEvents() {
        // TODO: Register BreakBlockEvent when API is available
        plugin.getLogger().at(Level.INFO).log("MiningListener registered (placeholder)");
    }
}
