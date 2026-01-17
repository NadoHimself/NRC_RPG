package de.nightraid.nrcrpg.skills;

/**
 * Container for skill progression data
 */
public class SkillData {
    
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
    
    public int getLevel() {
        return level;
    }
    
    public void setLevel(int level) {
        this.level = Math.max(1, Math.min(level, 100));
    }
    
    public double getXp() {
        return xp;
    }
    
    public void setXp(double xp) {
        this.xp = Math.max(0, xp);
    }
    
    public void addXp(double amount) {
        this.xp += amount;
        this.totalXP += amount;
    }
    
    public double getTotalXP() {
        return totalXP;
    }
    
    public void setTotalXP(double totalXP) {
        this.totalXP = Math.max(0, totalXP);
    }
    
    public SkillData clone() {
        return new SkillData(level, xp, totalXP);
    }
}