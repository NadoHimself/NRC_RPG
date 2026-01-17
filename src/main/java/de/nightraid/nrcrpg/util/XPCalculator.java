package de.nightraid.nrcrpg.util;

/**
 * Utility class for XP calculations
 */
public class XPCalculator {
    
    private static final double BASE_XP = 1000.0;
    private static final double EXPONENT = 1.05;
    
    /**
     * Calculate required XP for a specific level
     * Formula: BASE_XP * (EXPONENT ^ level)
     * 
     * @param level The level
     * @return Required XP amount
     */
    public static double getRequiredXP(int level) {
        if (level <= 0) return 0;
        return BASE_XP * Math.pow(EXPONENT, level);
    }
    
    /**
     * Calculate total XP required to reach a level from level 0
     * 
     * @param level The target level
     * @return Total XP required
     */
    public static double getTotalXPForLevel(int level) {
        double total = 0;
        for (int i = 1; i <= level; i++) {
            total += getRequiredXP(i);
        }
        return total;
    }
    
    /**
     * Calculate what level a player would be with given total XP
     * 
     * @param totalXP The total XP amount
     * @return The level
     */
    public static int getLevelFromTotalXP(double totalXP) {
        int level = 0;
        double accumulated = 0;
        
        while (accumulated < totalXP) {
            level++;
            accumulated += getRequiredXP(level);
        }
        
        return Math.max(0, level - 1);
    }
    
    /**
     * Calculate progress percentage to next level
     * 
     * @param currentXP Current XP in this level
     * @param currentLevel Current level
     * @return Percentage (0.0 to 1.0)
     */
    public static double getProgressPercentage(double currentXP, int currentLevel) {
        double required = getRequiredXP(currentLevel + 1);
        return Math.min(1.0, currentXP / required);
    }
    
    /**
     * Format XP amount for display
     * 
     * @param xp The XP amount
     * @return Formatted string (e.g., "1.5K", "2.3M")
     */
    public static String formatXP(double xp) {
        if (xp < 1000) {
            return String.format("%.0f", xp);
        } else if (xp < 1_000_000) {
            return String.format("%.1fK", xp / 1000);
        } else {
            return String.format("%.1fM", xp / 1_000_000);
        }
    }
    
    /**
     * Calculate XP remaining to next level
     * 
     * @param currentXP Current XP in this level
     * @param currentLevel Current level
     * @return XP remaining
     */
    public static double getXPToNextLevel(double currentXP, int currentLevel) {
        return getRequiredXP(currentLevel + 1) - currentXP;
    }
}
