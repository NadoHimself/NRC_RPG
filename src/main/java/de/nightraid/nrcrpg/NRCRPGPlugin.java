package de.nightraid.nrcrpg;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import de.nightraid.nrcrpg.commands.SkillsCommand;
import de.nightraid.nrcrpg.data.components.CooldownComponent;
import de.nightraid.nrcrpg.data.components.SkillComponent;
import de.nightraid.nrcrpg.data.components.StatisticsComponent;
import de.nightraid.nrcrpg.data.components.XPComponent;
import de.nightraid.nrcrpg.listeners.*;
import de.nightraid.nrcrpg.managers.*;
import de.nightraid.nrcrpg.tasks.AutoSaveTask;
import de.nightraid.nrcrpg.util.ConfigManager;

import javax.annotation.Nonnull;
import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * Main Plugin Class for NRC_RPG
 * MCMMO-inspired Skills & Leveling System for Hytale
 * 
 * @author Kielian (NadoHimself)
 * @version 1.0.0
 */
public class NRCRPGPlugin extends JavaPlugin {
    
    private static NRCRPGPlugin instance;
    
    // Managers
    private ConfigManager configManager;
    private DataManager dataManager;
    private SkillManager skillManager;
    private XPManager xpManager;
    private CooldownManager cooldownManager;
    
    // Tasks
    private ScheduledFuture<?> autoSaveTask;
    
    // Plugin state
    private Path dataFolder;
    
    /**
     * Constructor required by Hytale plugin system
     */
    public NRCRPGPlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }
    
    /**
     * Called during server initialization
     * Register components, commands, events
     */
    @Override
    protected void setup() {
        instance = this;
        long startTime = System.currentTimeMillis();
        
        getLogger().at(Level.INFO).log("==========================================");
        getLogger().at(Level.INFO).log("  NRC_RPG v1.0.0 - Initializing...");
        getLogger().at(Level.INFO).log("  Author: Kielian (NadoHimself)");
        getLogger().at(Level.INFO).log("  Company: Age of Flair");
        getLogger().at(Level.INFO).log("==========================================");
        
        try {
            // Initialize data folder
            this.dataFolder = getPluginDataFolder();
            getLogger().at(Level.INFO).log("Data folder: " + dataFolder.toAbsolutePath());
            
            // Load configuration
            getLogger().at(Level.INFO).log("Loading configuration...");
            this.configManager = new ConfigManager(this);
            configManager.loadConfigs();
            
            // Initialize managers
            getLogger().at(Level.INFO).log("Initializing managers...");
            this.cooldownManager = new CooldownManager();
            this.dataManager = new DataManager(this);
            this.xpManager = new XPManager(this);
            this.skillManager = new SkillManager(this);
            
            // Register ECS components
            getLogger().at(Level.INFO).log("Registering ECS components...");
            registerComponents();
            
            // Register event listeners
            getLogger().at(Level.INFO).log("Registering event listeners...");
            registerListeners();
            
            // Register commands
            getLogger().at(Level.INFO).log("Registering commands...");
            registerCommands();
            
            long loadTime = System.currentTimeMillis() - startTime;
            
            getLogger().at(Level.INFO).log("==========================================");
            getLogger().at(Level.INFO).log("  NRC_RPG v1.0.0 setup complete!");
            getLogger().at(Level.INFO).log("  Setup time: " + loadTime + "ms");
            getLogger().at(Level.INFO).log("  Skills: Combat, Mining, Woodcutting, Farming, Fishing");
            getLogger().at(Level.INFO).log("==========================================");
            
        } catch (Exception e) {
            getLogger().at(Level.SEVERE).log("Failed to setup NRC_RPG!", e);
            throw new RuntimeException("Plugin setup failed", e);
        }
    }
    
    /**
     * Called after all plugins are set up
     * Start background tasks, initialize state
     */
    @Override
    protected void start() {
        getLogger().at(Level.INFO).log("Starting NRC_RPG...");
        
        try {
            // Start scheduled tasks
            getLogger().at(Level.INFO).log("Starting scheduled tasks...");
            startTasks();
            
            getLogger().at(Level.INFO).log("NRC_RPG started successfully!");
            
        } catch (Exception e) {
            getLogger().at(Level.SEVERE).log("Failed to start NRC_RPG!", e);
        }
    }
    
    /**
     * Called when server is stopping
     * Clean up resources, save data
     */
    @Override
    protected void shutdown() {
        getLogger().at(Level.INFO).log("Shutting down NRC_RPG...");
        
        try {
            // Stop scheduled tasks
            if (autoSaveTask != null && !autoSaveTask.isCancelled()) {
                autoSaveTask.cancel(false);
                getLogger().at(Level.INFO).log("Auto-save task cancelled");
            }
            
            // Save all player data
            getLogger().at(Level.INFO).log("Saving all player data...");
            if (dataManager != null) {
                dataManager.saveAllPlayerData();
            }
            
            // Cleanup managers
            if (skillManager != null) {
                skillManager.shutdown();
            }
            
            getLogger().at(Level.INFO).log("NRC_RPG shutdown complete!");
            
        } catch (Exception e) {
            getLogger().at(Level.SEVERE).log("Error during NRC_RPG shutdown!", e);
        }
    }
    
    /**
     * Register ECS components with Hytale's ComponentRegistry
     */
    private void registerComponents() {
        try {
            // Note: Actual registration depends on available API methods
            // This is a placeholder - adjust based on actual Hytale Component API
            getLogger().at(Level.INFO).log("Component registration queued (4 components)");
            getLogger().at(Level.INFO).log("- SkillComponent");
            getLogger().at(Level.INFO).log("- XPComponent");
            getLogger().at(Level.INFO).log("- CooldownComponent");
            getLogger().at(Level.INFO).log("- StatisticsComponent");
        } catch (Exception e) {
            getLogger().at(Level.SEVERE).log("Failed to register components!", e);
            throw e;
        }
    }
    
    /**
     * Register event listeners with Hytale's EventRegistry
     */
    private void registerListeners() {
        try {
            new CombatListener(this);
            new MiningListener(this);
            new WoodcuttingListener(this);
            new FarmingListener(this);
            new FishingListener(this);
            new PlayerConnectionListener(this);
            
            getLogger().at(Level.INFO).log("Registered 6 event listeners");
        } catch (Exception e) {
            getLogger().at(Level.SEVERE).log("Failed to register listeners!", e);
            throw e;
        }
    }
    
    /**
     * Register commands with Hytale's CommandRegistry
     */
    private void registerCommands() {
        try {
            getCommandRegistry().registerCommand(new SkillsCommand(this));
            // Note: AdminCommand removed until API is clarified
            
            getLogger().at(Level.INFO).log("Registered commands: /skills");
        } catch (Exception e) {
            getLogger().at(Level.SEVERE).log("Failed to register commands!", e);
            throw e;
        }
    }
    
    /**
     * Start scheduled tasks using Hytale's scheduled executor
     */
    private void startTasks() {
        int saveInterval = configManager.getAutoSaveInterval();
        
        // Auto-save task using HytaleServer.SCHEDULED_EXECUTOR
        AutoSaveTask saveTask = new AutoSaveTask(this);
        this.autoSaveTask = com.hypixel.hytale.server.core.HytaleServer.SCHEDULED_EXECUTOR
            .scheduleWithFixedDelay(
                saveTask,
                saveInterval,
                saveInterval,
                TimeUnit.SECONDS
            );
        
        getLogger().at(Level.INFO).log("Auto-save scheduled every " + saveInterval + " seconds");
    }
    
    /**
     * Get plugin data folder path
     * Create directory based on plugin manifest
     * @return Path to plugin data directory
     */
    public Path getPluginDataFolder() {
        if (dataFolder == null) {
            // Create data folder in server's plugins directory
            // Format: plugins/nrc_rpg/
            File serverDir = new File(System.getProperty("user.dir"));
            File pluginsDir = new File(serverDir, "plugins");
            File pluginFolder = new File(pluginsDir, "nrc_rpg");
            
            if (!pluginFolder.exists()) {
                pluginFolder.mkdirs();
            }
            
            dataFolder = pluginFolder.toPath();
        }
        return dataFolder;
    }
    
    // === Getters ===
    
    public static NRCRPGPlugin getInstance() {
        return instance;
    }
    
    public ConfigManager getConfigManager() {
        return configManager;
    }
    
    public DataManager getDataManager() {
        return dataManager;
    }
    
    public SkillManager getSkillManager() {
        return skillManager;
    }
    
    public XPManager getXPManager() {
        return xpManager;
    }
    
    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }
    
    public String getVersion() {
        return "1.0.0";
    }
}
