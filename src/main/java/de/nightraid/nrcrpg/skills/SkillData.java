package de.nightraid.nrcrpg.skills;

/**
 * Data class representing a single skill's information
 */
public class SkillData {
    
    private final SkillType skillType;
    private int level;
    private double currentXP;
    private double maxXP;
    
    /**
     * Create new skill data with default values
     */
    public SkillData(SkillType skillType) {
        this.skillType = skillType;
        this.level = 1;
        this.currentXP = 0.0;
        this.maxXP = 100.0;
    }
    
    /**
     * Create new skill data with specific level
     */
    public SkillData(SkillType skillType, int level) {
        this.skillType = skillType;
        this.level = level;
        this.currentXP = 0.0;
        this.maxXP = calculateMaxXP(level);
    }
    
    /**
     * Create skill data with all parameters
     */
    public SkillData(SkillType skillType, int level, double currentXP, double maxXP) {
        this.skillType = skillType;
        this.level = level;
        this.currentXP = currentXP;
        this.maxXP = maxXP;
    }
    
    /**
     * Legacy constructor for backwards compatibility
     */
    public SkillData(int level, double currentXP, double maxXP) {
        this(SkillType.MINING, level, currentXP, maxXP);
    }
    
    /**
     * Default constructor
     */
    public SkillData() {
        this(SkillType.MINING);
    }
    
    public SkillType getSkillType() {
        return skillType;
    }
    
    public int getLevel() {
        return level;
    }
    
    public void setLevel(int level) {
        this.level = level;
        this.maxXP = calculateMaxXP(level);
    }
    
    public double getCurrentXP() {
        return currentXP;
    }
    
    public void setCurrentXP(double currentXP) {
        this.currentXP = currentXP;
    }
    
    public double getMaxXP() {
        return maxXP;
    }
    
    public void addXP(double amount) {
        this.currentXP += amount;
    }
    
    /**
     * Calculate max XP required for a level
     * Uses exponential formula: 100 * (level^2) * 1.5
     */
    private double calculateMaxXP(int level) {
        return 100.0 * Math.pow(level, 2.0) * 1.5;
    }
}
