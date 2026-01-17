package de.nightraid.nrcrpg.skills;

/**
 * Data class storing skill progression information
 * Stores level, current XP, and total XP earned
 */
public class SkillData implements Cloneable {
    
    private int level;
    private double xp;
    private double totalXP;
    
    public SkillData() {
        this.level = 1;
        this.xp = 0.0;
        this.totalXP = 0.0;
    }
    
    public SkillData(int level, double xp, double totalXP) {
        this.level = level;
        this.xp = xp;
        this.totalXP = totalXP;
    }
    
    @Override
    public SkillData clone() {
        try {
            return (SkillData) super.clone();
        } catch (CloneNotSupportedException e) {
            // This should never happen since we implement Cloneable
            return new SkillData(this.level, this.xp, this.totalXP);
        }
    }
    
    // Getters
    
    public int getLevel() {
        return level;
    }
    
    public double getXp() {
        return xp;
    }
    
    public double getTotalXP() {
        return totalXP;
    }
    
    // Setters
    
    public void setLevel(int level) {
        this.level = Math.max(1, Math.min(level, 100)); // Clamp between 1-100
    }
    
    public void setXp(double xp) {
        this.xp = Math.max(0, xp);
    }
    
    public void setTotalXP(double totalXP) {
        this.totalXP = Math.max(0, totalXP);
    }
    
    // Utility methods
    
    public void addXP(double amount) {
        if (amount <= 0) return;
        
        this.xp += amount;
        this.totalXP += amount;
    }
    
    public void levelUp() {
        if (level < 100) {
            this.level++;
        }
    }
    
    public void reset() {
        this.level = 1;
        this.xp = 0.0;
        this.totalXP = 0.0;
    }
    
    @Override
    public String toString() {
        return "SkillData{level=" + level + ", xp=" + xp + ", totalXP=" + totalXP + "}";
    }
}
