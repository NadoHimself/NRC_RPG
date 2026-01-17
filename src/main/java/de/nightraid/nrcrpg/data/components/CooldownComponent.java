package de.nightraid.nrcrpg.data.components;

import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ECS Component for tracking ability cooldowns
 * Stores timestamps for when abilities were last used
 */
public class CooldownComponent implements Component<EntityStore> {
    
    private final Map<String, Long> cooldowns;
    
    public CooldownComponent() {
        this.cooldowns = new ConcurrentHashMap<>();
    }
    
    private CooldownComponent(Map<String, Long> cooldowns) {
        this.cooldowns = new ConcurrentHashMap<>(cooldowns);
    }
    
    @Override
    @Nonnull
    public Component<EntityStore> clone() {
        return new CooldownComponent(this.cooldowns);
    }
    
    /**
     * Set cooldown for an ability
     * @param abilityKey Unique ability identifier
     * @param durationSeconds Cooldown duration in seconds
     */
    public void setCooldown(String abilityKey, int durationSeconds) {
        long expiresAt = System.currentTimeMillis() + (durationSeconds * 1000L);
        cooldowns.put(abilityKey, expiresAt);
    }
    
    /**
     * Check if ability is on cooldown
     * @param abilityKey Unique ability identifier
     * @return true if still on cooldown
     */
    public boolean isOnCooldown(String abilityKey) {
        Long expiresAt = cooldowns.get(abilityKey);
        if (expiresAt == null) {
            return false;
        }
        
        if (System.currentTimeMillis() >= expiresAt) {
            cooldowns.remove(abilityKey);
            return false;
        }
        
        return true;
    }
    
    /**
     * Get remaining cooldown time in seconds
     * @param abilityKey Unique ability identifier
     * @return seconds remaining, or 0 if not on cooldown
     */
    public int getRemainingSeconds(String abilityKey) {
        Long expiresAt = cooldowns.get(abilityKey);
        if (expiresAt == null) {
            return 0;
        }
        
        long remaining = expiresAt - System.currentTimeMillis();
        if (remaining <= 0) {
            cooldowns.remove(abilityKey);
            return 0;
        }
        
        return (int) Math.ceil(remaining / 1000.0);
    }
    
    public void removeCooldown(String abilityKey) {
        cooldowns.remove(abilityKey);
    }
    
    public void clearAllCooldowns() {
        cooldowns.clear();
    }
    
    public Map<String, Long> getAllCooldowns() {
        return new ConcurrentHashMap<>(cooldowns);
    }
}
