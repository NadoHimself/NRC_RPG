package de.nightraid.nrcrpg.skills;

import de.nightraid.nrcrpg.NRCRPGPlugin;

/**
 * Combat skill implementation
 * Handles melee combat, damage, and kills
 */
public class CombatSkill extends Skill {
    
    // XP rates
    private static final double XP_PER_DAMAGE = 2.0;
    private static final double XP_KILL_BONUS = 20.0;
    private static final double XP_BOSS_KILL = 500.0;
    
    // Ability unlock levels
    private static final int BLEEDING_LEVEL = 20;
    private static final int CRITICAL_STRIKE_LEVEL = 40;
    private static final int SERRATED_STRIKES_LEVEL = 20;
    
    public CombatSkill(NRCRPGPlugin plugin) {
        super(plugin, SkillType.COMBAT, 1000.0);
    }
    
    @Override
    public double calculateXP(String action, Object context) {
        switch (action.toLowerCase()) {
            case "damage":
                double damage = context instanceof Number ? ((Number) context).doubleValue() : 0;
                return damage * XP_PER_DAMAGE;
                
            case "kill":
                return XP_KILL_BONUS;
                
            case "boss_kill":
                return XP_BOSS_KILL;
                
            default:
                return 0.0;
        }
    }
    
    @Override
    public boolean isAbilityUnlocked(int level, String abilityName) {
        switch (abilityName.toLowerCase()) {
            case "bleeding":
                return level >= BLEEDING_LEVEL;
            case "critical_strike":
                return level >= CRITICAL_STRIKE_LEVEL;
            case "serrated_strikes":
                return level >= SERRATED_STRIKES_LEVEL;
            default:
                return false;
        }
    }
    
    @Override
    public boolean activateAbility(Object player, String abilityName) {
        // Implementation would use Hytale's player API
        // Check cooldown, activate effect, etc.
        return false;
    }
    
    /**
     * Calculate bleeding damage over time
     */
    public double getBleedingDamage(int level) {
        return 2.0 + (level * 0.1); // Scales with level
    }
    
    /**
     * Get critical strike chance
     */
    public double getCriticalChance(int level) {
        if (level < CRITICAL_STRIKE_LEVEL) return 0.0;
        return 0.15 + ((level - CRITICAL_STRIKE_LEVEL) * 0.005); // 15% + 0.5% per level
    }
    
    /**
     * Get damage bonus from skill level
     */
    public double getDamageBonus(int level) {
        return getPassiveBonus(level);
    }
}
