package de.nightraid.nrcrpg;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import de.nightraid.nrcrpg.commands.SkillsCommand;
import de.nightraid.nrcrpg.data.DataManager;
import de.nightraid.nrcrpg.data.components.CooldownComponent;
import de.nightraid.nrcrpg.data.components.SkillComponent;
import de.nightraid.nrcrpg.data.components.StatisticsComponent;
import de.nightraid.nrcrpg.data.components.XPComponent;
import de.nightraid.nrcrpg.listeners.*;

import javax.annotation.Nonnull;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * Main plugin class for NRC_RPG
 * Implements a comprehensive skill-based RPG system for Hytale
 */
public class NRCRPGPlugin extends JavaPlugin {
    
    // Singleton instance for easy access
    private static NRCRPGPlugin instance;
    
    // Managers
    private DataManager dataManager;
    
    // Listeners (keep references for cleanup)
    private PlayerConnectionListener playerConnectionListener;
    private MiningListener miningListener;
    
    // Auto-save task
    private ScheduledFuture<?> autoSaveTask;
    
    public NRCRPGPlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }
    
    @Override
    protected void setup() {
        instance = this;
        
        getLogger().at(Level.INFO).log("==========================================");
        getLogger().at(Level.INFO).log("  NRC_RPG v" + getManifest().getVersion() + " - Initializing...");
        getLogger().at(Level.INFO).log("  Skills: Combat, Mining, Woodcutting, Farming, Fishing");
        getLogger().at(Level.INFO).log("==========================================");
        
        // 1. Initialize Managers
        this.dataManager = new DataManager(this);
        
        // 2. Register Commands
        registerCommands();
        
        // 3. Register Event Listeners
        registerListeners();
        
        getLogger().at(Level.INFO).log("NRC_RPG setup complete!");
    }
    
    @Override
    protected void start() {
        getLogger().at(Level.INFO).log("NRC_RPG started successfully!");
        
        // Start auto-save task (runs every 5 minutes)
        autoSaveTask = com.hypixel.hytale.server.core.HytaleServer.SCHEDULED_EXECUTOR.scheduleWithFixedDelay(
            () -> {
                try {
                    getLogger().at(Level.INFO).log("[Auto-Save] Saving all player data...");
                    dataManager.saveAllPlayers();
                    getLogger().at(Level.INFO).log("[Auto-Save] Complete!");
                } catch (Exception e) {
                    getLogger().at(Level.SEVERE).log("[Auto-Save] Failed!", e);
                }
            },
            5, // Initial delay
            5, // Period
            TimeUnit.MINUTES
        );
    }
    
    @Override
    protected void shutdown() {
        getLogger().at(Level.INFO).log("NRC_RPG shutting down...");
        
        // Cancel auto-save task
        if (autoSaveTask != null && !autoSaveTask.isDone()) {
            autoSaveTask.cancel(false);
        }
        
        // Save all player data before shutdown
        try {
            dataManager.saveAllPlayers();
            getLogger().at(Level.INFO).log("All player data saved successfully!");
        } catch (Exception e) {
            getLogger().at(Level.SEVERE).log("Failed to save player data on shutdown!", e);
        }
        
        getLogger().at(Level.INFO).log("NRC_RPG shutdown complete.");
    }
    
    /**
     * Register all commands
     */
    private void registerCommands() {
        getLogger().at(Level.INFO).log("Registering commands...");
        
        getCommandRegistry().registerCommand(new SkillsCommand(this));
        
        getLogger().at(Level.INFO).log("✓ Registered 1 command (/skills)");
    }
    
    /**
     * Register all event listeners
     */
    private void registerListeners() {
        getLogger().at(Level.INFO).log("Registering event listeners...");
        
        // Core listeners
        playerConnectionListener = new PlayerConnectionListener(this);
        miningListener = new MiningListener(this);
        
        // TODO: Add other listeners when implemented
        // new WoodcuttingListener(this);
        // new FarmingListener(this);
        // new FishingListener(this);
        // new CombatListener(this);
        
        getLogger().at(Level.INFO).log("✓ Registered event listeners");
    }
    
    // === Getters ===
    
    public static NRCRPGPlugin getInstance() {
        return instance;
    }
    
    public DataManager getDataManager() {
        return dataManager;
    }
}
