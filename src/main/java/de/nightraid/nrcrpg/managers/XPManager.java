package de.nightraid.nrcrpg.managers;

import de.nightraid.nrcrpg.NRCRPGPlugin;
import de.nightraid.nrcrpg.skills.SkillData;
import de.nightraid.nrcrpg.skills.SkillType;
import de.nightraid.nrcrpg.util.XPCalculator;

import java.util.UUID;

/**
 * Manages XP calculations and level progression
 */
public class XPManager {
    
    private final NRCRPGPlugin plugin;
    
    public XPManager(NRCRPGPlugin plugin) {
        this.plugin = plugin;
    }
    
    /**
     * Check if player should level up
     */
    public void checkLevelUp(UUID uuid, SkillType skill, SkillData skillData) {
        int currentLevel = skillData.getLevel();
        int maxLevel = plugin.getConfigManager().getMaxLevel();
        
        // Check if already max level
        if (currentLevel >= maxLevel) {
            return;
        }
        
        double currentXP = skillData.getXp();
        double requiredXP = XPCalculator.getRequiredXP(currentLevel);
        
        // Level up if enough XP
        if (currentXP >= requiredXP) {
            skillData.setLevel(currentLevel + 1);
            skillData.setXp(currentXP - requiredXP); // Carry over excess XP
            
            // Trigger level up event
            onLevelUp(uuid, skill, currentLevel + 1);
            
            // Check for another level up (if gained a lot of XP)
            checkLevelUp(uuid, skill, skillData);
        }
    }
    
    /**
     * Handle level up event
     */
    private void onLevelUp(UUID uuid, SkillType skill, int newLevel) {
        NRCRPGPlugin.getPluginLogger().info("Player {} leveled up {} to level {}", 
            uuid, skill.getName(), newLevel);
        
        // TODO: Send level up message to player
        // TODO: Play level up sound
        // TODO: Spawn level up particles
        // TODO: Check for ability unlocks
        
        // Log special milestones
        if (newLevel % 10 == 0) {
            NRCRPGPlugin.getPluginLogger().info("Player {} reached milestone: {} Level {}", 
                uuid, skill.getName(), newLevel);
        }
    }
    
    /**
     * Calculate passive bonus percentage
     */
    public double calculatePassiveBonus(int level) {
        // +5% every 10 levels
        int milestones = level / 10;
        return milestones * 0.05; // 0.05 = 5%
    }
    
    /**
     * Get XP for next level
     */
    public double getXPForNextLevel(int currentLevel) {
        return XPCalculator.getRequiredXP(currentLevel);
    }
    
    /**
     * Get total XP for a level
     */
    public double getTotalXPForLevel(int level) {
        return XPCalculator.getTotalXPForLevel(level);
    }
    
    /**
     * Calculate level from total XP
     */
    public int getLevelFromTotalXP(double totalXP) {
        return XPCalculator.getLevelFromTotalXP(totalXP);
    }
    
    /**
     * Get progress percentage to next level
     */
    public double getProgressPercentage(SkillData skillData) {
        double currentXP = skillData.getXp();
        double requiredXP = getXPForNextLevel(skillData.getLevel());
        return (currentXP / requiredXP) * 100.0;
    }
}