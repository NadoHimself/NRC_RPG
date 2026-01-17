package de.nightraid.nrcrpg.listeners;

import de.nightraid.nrcrpg.NRCRPGPlugin;
import de.nightraid.nrcrpg.skills.SkillType;

/**
 * Handles combat-related events for Combat skill
 */
public class CombatListener {
    
    private final NRCRPGPlugin plugin;
    
    public CombatListener(NRCRPGPlugin plugin) {
        this.plugin = plugin;
        registerEvents();
    }
    
    /**
     * Register event listeners
     */
    private void registerEvents() {
        // eventBus.subscribe(EntityDamageByEntityEvent.class, this::onEntityDamage);
        // eventBus.subscribe(EntityDeathEvent.class, this::onEntityDeath);
    }
    
    /**
     * Handle entity damage by player
     */
    public void onEntityDamage(Object event) {
        // EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        // 
        // if (e.getDamager() instanceof Player player) {
        //     double damage = e.getDamage();
        //     plugin.getXPManager().grantXP(player, SkillType.COMBAT, damage * 2.0);
        //     
        //     // Apply skill bonuses (bleeding, critical strike, etc.)
        //     applySkillEffects(player, e.getEntity());
        // }
    }
    
    /**
     * Handle entity death (kill bonus)
     */
    public void onEntityDeath(Object event) {
        // EntityDeathEvent e = (EntityDeathEvent) event;
        // 
        // if (e.getKiller() instanceof Player player) {
        //     // Grant kill bonus XP
        //     double xp = isBoss(e.getEntity()) ? 500.0 : 20.0;
        //     plugin.getXPManager().grantXP(player, SkillType.COMBAT, xp);
        // }
    }
    
    /**
     * Apply skill-based effects (bleeding, critical strike)
     */
    private void applySkillEffects(Object player, Object target) {
        // Get player's combat level
        // Check for ability unlocks
        // Apply effects based on level and RNG
    }
}
