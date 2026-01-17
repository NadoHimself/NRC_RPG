package de.nightraid.nrcrpg;

import de.nightraid.nrcrpg.commands.AdminCommand;
import de.nightraid.nrcrpg.commands.SkillsCommand;
import de.nightraid.nrcrpg.listeners.*;
import de.nightraid.nrcrpg.managers.*;
import de.nightraid.nrcrpg.tasks.AutoSaveTask;
import de.nightraid.nrcrpg.util.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

/**
 * Main Plugin Class for NRC_RPG
 * MCMMO-inspired Skills & Leveling System for Hytale
 * 
 * @author Kielian (NadoHimself)
 * @version 1.0.0
 */
public class NRCRPGPlugin {
    
    private static NRCRPGPlugin instance;
    private static final Logger logger = LoggerFactory.getLogger("NRC_RPG");
    
    // Managers
    private ConfigManager configManager;
    private DataManager dataManager;
    private SkillManager skillManager;
    private XPManager xpManager;
    private CooldownManager cooldownManager;
    
    // Tasks
    private AutoSaveTask autoSaveTask;
    
    // Plugin state
    private boolean enabled = false;
    private Path dataFolder;
    
    /**
     * Called when plugin is enabled
     */
    public void onEnable() {
        instance = this;
        long startTime = System.currentTimeMillis();
        
        logger.info("==========================================");
        logger.info("  NRC_RPG v1.0.0 - Initializing...");
        logger.info("  Author: Kielian (NadoHimself)");
        logger.info("  Company: Age of Flair");
        logger.info("==========================================");
        
        try {
            // Initialize data folder
            initializeDataFolder();
            
            // Load configuration
            logger.info("Loading configuration...");
            this.configManager = new ConfigManager(this);
            configManager.loadConfigs();
            
            // Initialize managers
            logger.info("Initializing managers...");
            this.cooldownManager = new CooldownManager();
            this.dataManager = new DataManager(this);
            this.xpManager = new XPManager(this);
            this.skillManager = new SkillManager(this);
            
            // Register components (ECS)
            logger.info("Registering ECS components...");
            registerComponents();
            
            // Register event listeners
            logger.info("Registering event listeners...");
            registerListeners();
            
            // Register commands
            logger.info("Registering commands...");
            registerCommands();
            
            // Start scheduled tasks
            logger.info("Starting scheduled tasks...");
            startTasks();
            
            enabled = true;
            long loadTime = System.currentTimeMillis() - startTime;
            
            logger.info("==========================================");
            logger.info("  NRC_RPG v1.0.0 enabled successfully!");
            logger.info("  Load time: {}ms", loadTime);
            logger.info("  Skills: Combat, Mining, Woodcutting, Farming, Fishing");
            logger.info("==========================================");
            
        } catch (Exception e) {
            logger.error("Failed to enable NRC_RPG!", e);
            enabled = false;
        }
    }
    
    /**
     * Called when plugin is disabled
     */
    public void onDisable() {
        if (!enabled) return;
        
        logger.info("Disabling NRC_RPG...");
        
        try {
            // Stop scheduled tasks
            if (autoSaveTask != null) {
                autoSaveTask.cancel();
            }
            
            // Save all player data
            logger.info("Saving all player data...");
            dataManager.saveAllPlayerData();
            
            // Cleanup
            skillManager.shutdown();
            
            enabled = false;
            logger.info("NRC_RPG disabled successfully!");
            
        } catch (Exception e) {
            logger.error("Error during plugin disable!", e);
        }
    }
    
    /**
     * Initialize plugin data folder
     */
    private void initializeDataFolder() {
        // TODO: Get from Hytale API when available
        // For now use placeholder path
        this.dataFolder = Path.of("plugins/NRC_RPG");
        logger.info("Data folder: {}", dataFolder.toAbsolutePath());
    }
    
    /**
     * Register ECS components
     */
    private void registerComponents() {
        // TODO: Register with Hytale's Component Registry when API is available
        logger.info("ECS components registered (placeholder - awaiting Hytale API)");
    }
    
    /**
     * Register event listeners
     */
    private void registerListeners() {
        // TODO: Register with Hytale's Event Bus when API is available
        new CombatListener(this);
        new MiningListener(this);
        new WoodcuttingListener(this);
        new FarmingListener(this);
        new FishingListener(this);
        new PlayerConnectionListener(this);
        
        logger.info("Event listeners registered (6 listeners active)");
    }
    
    /**
     * Register commands
     */
    private void registerCommands() {
        // TODO: Register with Hytale's Command Manager when API is available
        new SkillsCommand(this);
        new AdminCommand(this);
        
        logger.info("Commands registered: /skills, /skillsadmin");
    }
    
    /**
     * Start scheduled tasks
     */
    private void startTasks() {
        int saveInterval = configManager.getAutoSaveInterval();
        
        // Auto-save task
        this.autoSaveTask = new AutoSaveTask(this);
        // TODO: Schedule with Hytale's Scheduler when API is available
        
        logger.info("Auto-save scheduled every {} seconds", saveInterval);
    }
    
    // Getters
    
    public static NRCRPGPlugin getInstance() {
        return instance;
    }
    
    public static Logger getPluginLogger() {
        return logger;
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
    
    public Path getDataFolder() {
        return dataFolder;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public String getVersion() {
        return "1.0.0";
    }
}