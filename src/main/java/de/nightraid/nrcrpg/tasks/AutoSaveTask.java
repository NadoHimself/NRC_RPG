package de.nightraid.nrcrpg.tasks;

import de.nightraid.nrcrpg.NRCRPGPlugin;

import java.util.logging.Level;

/**
 * Task that automatically saves all player data periodically
 */
public class AutoSaveTask implements Runnable {
    
    private final NRCRPGPlugin plugin;
    
    public AutoSaveTask(NRCRPGPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public void run() {
        try {
            plugin.getLogger().at(Level.INFO).log("[Auto-Save] Saving all player data...");
            
            // Fixed method name
            plugin.getDataManager().saveAllPlayers();
            
            plugin.getLogger().at(Level.INFO).log("[Auto-Save] Complete!");
        } catch (Exception e) {
            plugin.getLogger().at(Level.SEVERE).log("[Auto-Save] Failed!", e);
        }
    }
}
