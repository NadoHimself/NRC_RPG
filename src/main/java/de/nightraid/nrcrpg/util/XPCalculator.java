package de.nightraid.nrcrpg.util;

/**
 * Utility class for XP calculations
 */
public class XPCalculator {
    
    private static final double BASE_XP = 1000.0;
    private static final double GROWTH_RATE = 1.05;
    
    /**
     * Calculate required XP for next level
     * Formula: base_xp * (1.05 ^ current_level)
     */
    public static double getRequiredXP(int currentLevel) {
        return BASE_XP * Math.pow(GROWTH_RATE, currentLevel);
    }
    
    /**
     * Calculate total XP needed to reach a level
     */
    public static double getTotalXPForLevel(int level) {
        double total = 0;
        for (int i = 1; i < level; i++) {
            total += getRequiredXP(i);
        }
        return total;
    }
    
    /**
     * Calculate level from total XP
     */
    public static int getLevelFromTotalXP(double totalXP) {
        int level = 1;
        double accumulated = 0;
        
        while (level < 100) {
            double required = getRequiredXP(level);
            if (accumulated + required > totalXP) {
                break;
            }
            accumulated += required;
            level++;
        }
        
        return level;
    }
    
    /**
     * Calculate XP percentage progress
     */
    public static double getProgressPercentage(double currentXP, int level) {
        double required = getRequiredXP(level);
        return (currentXP / required) * 100.0;
    }
}