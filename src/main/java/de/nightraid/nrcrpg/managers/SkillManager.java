package de.nightraid.nrcrpg.managers;

import de.nightraid.nrcrpg.NRCRPGPlugin;
import de.nightraid.nrcrpg.skills.SkillType;
import de.nightraid.nrcrpg.util.XPCalculator;

import java.util.logging.Level;

/**
 * Manages skill-related operations and calculations
 */
public class SkillManager {
    
    private final NRCRPGPlugin plugin;
    
    public SkillManager(NRCRPGPlugin plugin) {
        this.plugin = plugin;
    }
    
    /**
     * Calculate if a player should level up based on their XP
     * @param currentXP Current XP amount
     * @param currentLevel Current level
     * @return New level (same if no level up)
     */
    public int calculateLevel(double currentXP, int currentLevel) {
        int calculatedLevel = XPCalculator.getLevelForXP(currentXP);
        return Math.max(currentLevel, calculatedLevel);
    }
    
    /**
     * Get XP required for a specific level
     */
    public double getXPForLevel(int level) {
        return XPCalculator.getXPForLevel(level);
    }
    
    /**
     * Get progress percentage to next level
     */
    public double getProgressToNextLevel(double currentXP, int currentLevel) {
        return XPCalculator.getProgressToNextLevel(currentXP, currentLevel);
    }
    
    /**
     * Check if player has reached a new level
     */
    public boolean hasLeveledUp(double oldXP, double newXP, int currentLevel) {
        int oldLevel = XPCalculator.getLevelForXP(oldXP);
        int newLevel = XPCalculator.getLevelForXP(newXP);
        return newLevel > oldLevel && newLevel > currentLevel;
    }
}
