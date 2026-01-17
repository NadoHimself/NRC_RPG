package de.nightraid.nrcrpg.skills;

import de.nightraid.nrcrpg.NRCRPGPlugin;

/**
 * Abstract base class for all skills
 */
public abstract class Skill {
    
    protected final NRCRPGPlugin plugin;
    protected final SkillType type;
    protected final double baseXP;
    
    public Skill(NRCRPGPlugin plugin, SkillType type, double baseXP) {
        this.plugin = plugin;
        this.type = type;
        this.baseXP = baseXP;
    }
    
    /**
     * Get the skill type
     */
    public SkillType getType() {
        return type;
    }
    
    /**
     * Get base XP for this skill
     */
    public double getBaseXP() {
        return baseXP;
    }
    
    /**
     * Calculate XP gain for a specific action
     * 
     * @param action The action performed
     * @param context Additional context (e.g., item type, damage dealt)
     * @return XP amount to grant
     */
    public abstract double calculateXP(String action, Object context);
    
    /**
     * Get passive bonus for a specific level
     * 
     * @param level The skill level
     * @return Bonus multiplier (e.g., 1.05 for +5%)
     */
    public double getPassiveBonus(int level) {
        // Base formula: +5% every 10 levels
        int tier = level / 10;
        return 1.0 + (tier * 0.05);
    }
    
    /**
     * Check if a specific ability is unlocked at this level
     * 
     * @param level The skill level
     * @param abilityName The ability name
     * @return true if unlocked
     */
    public abstract boolean isAbilityUnlocked(int level, String abilityName);
    
    /**
     * Activate a skill ability
     * 
     * @param player The player activating the ability
     * @param abilityName The ability name
     * @return true if successfully activated
     */
    public abstract boolean activateAbility(Object player, String abilityName);
}
