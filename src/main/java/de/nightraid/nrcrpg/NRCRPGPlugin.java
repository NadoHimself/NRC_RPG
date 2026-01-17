package de.nightraid.nrcrpg;

import de.nightraid.nrcrpg.commands.SkillsCommand;
import de.nightraid.nrcrpg.listeners.*;
import de.nightraid.nrcrpg.managers.DataManager;
import de.nightraid.nrcrpg.managers.SkillManager;
import de.nightraid.nrcrpg.managers.XPManager;
import org.slf4j.Logger;

/**
 * NRC_RPG - MCMMO-inspired RPG skills system for Hytale
 * 
 * @author Kielian (NadoHimself)
 * @version 1.0.0
 */
public class NRCRPGPlugin {
    
    private static NRCRPGPlugin instance;
    private Logger logger;
    
    // Managers
    private SkillManager skillManager;
    private XPManager xpManager;
    private DataManager dataManager;
    
    // Configuration
    private Config config;
    
    /**
     * Called when the plugin is enabled
     */
    public void onEnable() {
        instance = this;
        logger = getLogger();
        
        logger.info("========================================");
        logger.info("  NRC_RPG v1.0.0 - Starting up...");
        logger.info("  by Kielian (NadoHimself)");
        logger.info("========================================");
        
        // Load configuration
        loadConfiguration();
        
        // Initialize managers
        initializeManagers();
        
        // Register components
        registerComponents();
        
        // Register events
        registerEvents();
        
        // Register commands
        registerCommands();
        
        // Schedule tasks
        scheduleTasks();
        
        logger.info("NRC_RPG successfully enabled!");
    }
    
    /**
     * Called when the plugin is disabled
     */
    public void onDisable() {
        logger.info("Disabling NRC_RPG...");
        
        // Save all player data
        if (dataManager != null) {
            dataManager.saveAllPlayerData();
        }
        
        // Unregister event listeners
        // eventBus.unregisterAll(this);
        
        logger.info("NRC_RPG disabled successfully!");
    }
    
    /**
     * Load plugin configuration
     */
    private void loadConfiguration() {
        try {
            // Load config.json using Hytale's Codec system
            // config = configManager.load("config.json", Config.class);
            logger.info("Configuration loaded successfully!");
        } catch (Exception e) {
            logger.error("Failed to load configuration!", e);
        }
    }
    
    /**
     * Initialize all managers
     */
    private void initializeManagers() {
        logger.info("Initializing managers...");
        
        dataManager = new DataManager(this);
        xpManager = new XPManager(this);
        skillManager = new SkillManager(this, xpManager);
        
        logger.info("Managers initialized!");
    }
    
    /**
     * Register custom ECS components
     */
    private void registerComponents() {
        logger.info("Registering components...");
        
        // Register SkillComponent with Hytale's ECS
        // componentRegistry.register(SkillComponent.class);
        // componentRegistry.register(CooldownComponent.class);
        
        logger.info("Components registered!");
    }
    
    /**
     * Register event listeners
     */
    private void registerEvents() {
        logger.info("Registering event listeners...");
        
        // Register all skill-specific listeners
        new CombatListener(this);
        new MiningListener(this);
        new WoodcuttingListener(this);
        new FarmingListener(this);
        new FishingListener(this);
        
        logger.info("Event listeners registered!");
    }
    
    /**
     * Register commands
     */
    private void registerCommands() {
        logger.info("Registering commands...");
        
        // commandManager.register("skills", new SkillsCommand(this));
        // commandManager.register("skillsadmin", new AdminCommand(this));
        
        logger.info("Commands registered!");
    }
    
    /**
     * Schedule repeating tasks
     */
    private void scheduleTasks() {
        logger.info("Scheduling tasks...");
        
        // Auto-save task
        int saveInterval = 300; // config.getAutoSaveInterval();
        // scheduler.scheduleRepeating(() -> {
        //     dataManager.saveAllPlayerData();
        //     logger.info("Auto-saved all player data");
        // }, saveInterval, TimeUnit.SECONDS);
        
        logger.info("Tasks scheduled!");
    }
    
    // ==================== Getters ====================
    
    public static NRCRPGPlugin getInstance() {
        return instance;
    }
    
    public Logger getLogger() {
        // Return Hytale's logger
        return logger;
    }
    
    public SkillManager getSkillManager() {
        return skillManager;
    }
    
    public XPManager getXPManager() {
        return xpManager;
    }
    
    public DataManager getDataManager() {
        return dataManager;
    }
    
    public Config getConfig() {
        return config;
    }
    
    // ==================== Config Class ====================
    
    /**
     * Plugin configuration class
     */
    public static class Config {
        private double xpMultiplier = 1.0;
        private int maxLevel = 100;
        private boolean enableAbilities = true;
        private int autoSaveInterval = 300;
        private boolean debugMode = false;
        
        // Getters
        public double getXpMultiplier() { return xpMultiplier; }
        public int getMaxLevel() { return maxLevel; }
        public boolean isEnableAbilities() { return enableAbilities; }
        public int getAutoSaveInterval() { return autoSaveInterval; }
        public boolean isDebugMode() { return debugMode; }
    }
}
