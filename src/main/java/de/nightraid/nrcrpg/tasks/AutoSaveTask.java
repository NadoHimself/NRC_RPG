package de.nightraid.nrcrpg.tasks;

import de.nightraid.nrcrpg.NRCRPGPlugin;

import java.util.logging.Level;

public class AutoSaveTask implements Runnable {
    
    private final NRCRPGPlugin plugin;
    
    public AutoSaveTask(NRCRPGPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public void run() {
        try {
            plugin.getLogger().at(Level.INFO).log("Auto-saving player data...");
            plugin.getDataManager().saveAllPlayerData();
        } catch (Exception e) {
            plugin.getLogger().at(Level.SEVERE).log("Auto-save failed!", e);
        }
    }
}
