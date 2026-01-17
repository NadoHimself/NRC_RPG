package de.nightraid.nrcrpg.tasks;

import de.nightraid.nrcrpg.NRCRPGPlugin;

/**
 * Scheduled task for auto-saving player data
 */
public class AutoSaveTask implements Runnable {
    
    private final NRCRPGPlugin plugin;
    private boolean cancelled = false;
    
    public AutoSaveTask(NRCRPGPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public void run() {
        if (cancelled || !plugin.isEnabled()) {
            return;
        }
        
        try {
            plugin.getDataManager().saveAllPlayerData();
        } catch (Exception e) {
            NRCRPGPlugin.getPluginLogger().error("Error during auto-save!", e);
        }
    }
    
    public void cancel() {
        this.cancelled = true;
    }
}