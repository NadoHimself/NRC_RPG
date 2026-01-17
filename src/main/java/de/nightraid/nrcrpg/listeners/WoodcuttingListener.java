package de.nightraid.nrcrpg.listeners;

import de.nightraid.nrcrpg.NRCRPGPlugin;

import java.util.logging.Level;

/**
 * Listener for Woodcutting-related events
 * Grants Woodcutting XP for chopping trees
 * 
 * NOTE: Temporarily disabled until Hytale Block API is confirmed
 */
public class WoodcuttingListener {
    
    private final NRCRPGPlugin plugin;
    
    public WoodcuttingListener(NRCRPGPlugin plugin) {
        this.plugin = plugin;
        registerEvents();
    }
    
    private void registerEvents() {
        // TODO: Register BreakBlockEvent for wood blocks
        plugin.getLogger().at(Level.INFO).log("WoodcuttingListener registered (placeholder)");
    }
}
