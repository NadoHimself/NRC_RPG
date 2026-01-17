package de.nightraid.nrcrpg.data;

/**
 * Main configuration class
 */
public class MainConfig {
    
    public double xpMultiplier = 1.0;
    public int maxLevel = 100;
    public boolean enableAbilities = true;
    public int autoSaveInterval = 300; // seconds
    public boolean debugMode = false;
    
    public MainConfig() {
        // Default values set above
    }
}