package de.nightraid.nrcrpg.listeners;

import com.hypixel.hytale.server.core.ecs.Ref;
import com.hypixel.hytale.server.core.ecs.store.entity.EntityStore;
import com.hypixel.hytale.server.gameplay.systems.damage.event.DamageEvents;
import de.nightraid.nrcrpg.NRCRPGPlugin;
import de.nightraid.nrcrpg.data.components.SkillComponent;
import de.nightraid.nrcrpg.data.components.StatisticsComponent;
import de.nightraid.nrcrpg.data.components.XPComponent;
import de.nightraid.nrcrpg.skills.SkillType;

import java.util.logging.Level;

/**
 * Listener for Combat-related events
 * Grants Combat XP for dealing damage and killing entities
 */
public class CombatListener {
    
    private final NRCRPGPlugin plugin;
    
    // XP multipliers
    private static final double XP_PER_DAMAGE = 2.0;  // 2 XP per damage point
    private static final double KILL_BONUS_XP = 20.0; // Bonus XP for kills
    
    public CombatListener(NRCRPGPlugin plugin) {
        this.plugin = plugin;
        registerEvents();
    }
    
    private void registerEvents() {
        // Register Damage Event from Hytale's damage system
        // DamageEvents are grouped: Gather -> Filter -> Inspect
        // We want to listen to the Inspect group (after damage is calculated)
        plugin.getEventRegistry().register(
            DamageEvents.Inspect.class,
            this::onDamageInspect
        );
        
        plugin.getLogger().at(Level.INFO).log("CombatListener registered");
    }
    
    /**
     * Called when damage is dealt (after filters, before application)
     * @param event The damage inspect event
     */
    private void onDamageInspect(DamageEvents.Inspect event) {
        try {
            // Get the attacker reference
            Ref<EntityStore> attackerRef = event.attacker();
            if (attackerRef == null || !attackerRef.isAlive()) {
                return; // No attacker or attacker is dead
            }
            
            // Get entity store to access components
            EntityStore store = attackerRef.store();
            
            // Check if attacker is a player (has SkillComponent)
            SkillComponent skillComp = store.getComponent(attackerRef, SkillComponent.class);
            if (skillComp == null) {
                return; // Not a player or doesn't have skills
            }
            
            // Get damage amount
            double damage = event.damage().amount();
            if (damage <= 0) {
                return; // No damage dealt
            }
            
            // Calculate XP gain
            double xpGain = damage * XP_PER_DAMAGE;
            
            // Add pending XP (will be processed asynchronously)
            XPComponent xpComp = store.getComponent(attackerRef, XPComponent.class);
            if (xpComp != null) {
                xpComp.addPending(SkillType.COMBAT, xpGain);
            }
            
            // Update statistics
            StatisticsComponent stats = store.getComponent(attackerRef, StatisticsComponent.class);
            if (stats != null) {
                stats.addDamageDealt((int) damage);
            }
            
            // Check if this damage will kill the target
            Ref<EntityStore> victimRef = event.victim();
            if (victimRef != null && victimRef.isAlive()) {
                // TODO: Check victim health to determine kill
                // For now, we'll handle kills in a separate event when available
            }
            
        } catch (Exception e) {
            plugin.getLogger().at(Level.SEVERE).log("Error in CombatListener.onDamageInspect", e);
        }
    }
    
    /**
     * Called when an entity is killed by another entity
     * Note: This event might not exist yet in Hytale API, placeholder for future
     */
    private void onEntityKilled(Ref<EntityStore> killer, Ref<EntityStore> victim) {
        try {
            if (killer == null || !killer.isAlive()) {
                return;
            }
            
            EntityStore store = killer.store();
            
            // Check if killer has skill components
            XPComponent xpComp = store.getComponent(killer, XPComponent.class);
            if (xpComp != null) {
                // Grant kill bonus XP
                xpComp.addPending(SkillType.COMBAT, KILL_BONUS_XP);
            }
            
            // Update statistics
            StatisticsComponent stats = store.getComponent(killer, StatisticsComponent.class);
            if (stats != null) {
                stats.incrementMobsKilled();
            }
            
        } catch (Exception e) {
            plugin.getLogger().at(Level.SEVERE).log("Error in CombatListener.onEntityKilled", e);
        }
    }
}
