package de.nightraid.nrcrpg.managers;

import de.nightraid.nrcrpg.NRCRPGPlugin;
import de.nightraid.nrcrpg.data.PlayerData;
import de.nightraid.nrcrpg.skills.SkillData;
import de.nightraid.nrcrpg.skills.SkillType;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages skill progression for all players
 */
public class SkillManager {
    
    private final NRCRPGPlugin plugin;
    private final Map<UUID, PlayerData> playerDataCache;
    
    public SkillManager(NRCRPGPlugin plugin) {
        this.plugin = plugin;
        this.playerDataCache = new ConcurrentHashMap<>();
    }
    
    /**
     * Get player data from cache or create new
     */
    public PlayerData getPlayerData(UUID uuid) {
        return playerDataCache.computeIfAbsent(uuid, k -> new PlayerData(uuid));
    }
    
    /**
     * Load player data (called on join)
     */
    public void loadPlayerData(UUID uuid) {
        PlayerData data = plugin.getDataManager().loadPlayerData(uuid);
        if (data != null) {
            playerDataCache.put(uuid, data);
            NRCRPGPlugin.getPluginLogger().debug("Loaded data for player {}", uuid);
        }
    }
    
    /**
     * Unload player data (called on quit)
     */
    public void unloadPlayerData(UUID uuid) {
        PlayerData data = playerDataCache.remove(uuid);
        if (data != null) {
            plugin.getDataManager().savePlayerData(data);
            NRCRPGPlugin.getPluginLogger().debug("Unloaded data for player {}", uuid);
        }
    }
    
    /**
     * Add XP to a skill
     */
    public void addXP(UUID uuid, SkillType skill, double amount) {
        PlayerData data = getPlayerData(uuid);
        SkillData skillData = data.getSkillData(skill);
        
        // Apply XP multiplier from config
        double multiplier = plugin.getConfigManager().getXPMultiplier();
        amount *= multiplier;
        
        // Add XP
        skillData.addXp(amount);
        
        // Check for level up
        plugin.getXPManager().checkLevelUp(uuid, skill, skillData);
    }
    
    /**
     * Get skill level
     */
    public int getLevel(UUID uuid, SkillType skill) {
        return getPlayerData(uuid).getSkillData(skill).getLevel();
    }
    
    /**
     * Get skill XP
     */
    public double getXP(UUID uuid, SkillType skill) {
        return getPlayerData(uuid).getSkillData(skill).getXp();
    }
    
    /**
     * Set skill level (admin command)
     */
    public void setLevel(UUID uuid, SkillType skill, int level) {
        PlayerData data = getPlayerData(uuid);
        SkillData skillData = data.getSkillData(skill);
        skillData.setLevel(level);
        skillData.setXp(0); // Reset XP when setting level
        
        NRCRPGPlugin.getPluginLogger().info("Set {} skill level to {} for player {}", 
            skill.getName(), level, uuid);
    }
    
    /**
     * Reset all skills for a player
     */
    public void resetSkills(UUID uuid) {
        PlayerData data = getPlayerData(uuid);
        for (SkillType skill : SkillType.values()) {
            SkillData skillData = data.getSkillData(skill);
            skillData.setLevel(1);
            skillData.setXp(0);
            skillData.setTotalXP(0);
        }
        
        NRCRPGPlugin.getPluginLogger().info("Reset all skills for player {}", uuid);
    }
    
    /**
     * Get passive bonus for a skill level
     */
    public double getPassiveBonus(UUID uuid, SkillType skill) {
        int level = getLevel(uuid, skill);
        return plugin.getXPManager().calculatePassiveBonus(level);
    }
    
    /**
     * Check if player has unlocked an ability
     */
    public boolean hasAbilityUnlocked(UUID uuid, SkillType skill, int requiredLevel) {
        return getLevel(uuid, skill) >= requiredLevel;
    }
    
    /**
     * Shutdown manager
     */
    public void shutdown() {
        // Save all cached player data
        for (PlayerData data : playerDataCache.values()) {
            plugin.getDataManager().savePlayerData(data);
        }
        playerDataCache.clear();
    }
    
    /**
     * Get all cached player UUIDs
     */
    public java.util.Set<UUID> getCachedPlayers() {
        return playerDataCache.keySet();
    }
}