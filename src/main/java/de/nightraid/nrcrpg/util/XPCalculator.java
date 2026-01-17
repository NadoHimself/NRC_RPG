package de.nightraid.nrcrpg.util;

/**
 * Utility class for XP and level calculations
 * Uses exponential curve: XP = 100 * (level^2) * 1.5
 */
public class XPCalculator {
    
    // Base XP required for level 2
    private static final double BASE_XP = 100.0;
    
    // Exponential multiplier
    private static final double EXPONENT = 2.0;
    
    // Level scaling multiplier
    private static final double MULTIPLIER = 1.5;
    
    /**
     * Calculate the total XP required to reach a specific level
     * @param level The target level (1-based)
     * @return Total XP needed
     */
    public static double getXPForLevel(int level) {
        if (level <= 1) {
            return 0.0;
        }
        
        // Formula: 100 * (level^2) * 1.5
        return BASE_XP * Math.pow(level, EXPONENT) * MULTIPLIER;
    }
    
    /**
     * Calculate the level for a given amount of XP
     * @param xp The total XP amount
     * @return The level (minimum 1)
     */
    public static int getLevelForXP(double xp) {
        if (xp <= 0) {
            return 1;
        }
        
        // Reverse formula: level = sqrt(xp / (100 * 1.5))
        int level = (int) Math.sqrt(xp / (BASE_XP * MULTIPLIER));
        
        // Ensure level is at least 1
        return Math.max(1, level);
    }
    
    /**
     * Calculate XP progress percentage towards next level
     * @param currentXP Current total XP
     * @param currentLevel Current level
     * @return Progress percentage (0-100)
     */
    public static double getProgressToNextLevel(double currentXP, int currentLevel) {
        double xpForCurrent = getXPForLevel(currentLevel);
        double xpForNext = getXPForLevel(currentLevel + 1);
        double xpIntoLevel = currentXP - xpForCurrent;
        double xpNeeded = xpForNext - xpForCurrent;
        
        if (xpNeeded <= 0) {
            return 100.0;
        }
        
        return (xpIntoLevel / xpNeeded) * 100.0;
    }
}
