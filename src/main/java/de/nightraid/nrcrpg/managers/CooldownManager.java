package de.nightraid.nrcrpg.managers;

import de.nightraid.nrcrpg.skills.SkillType;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages ability cooldowns
 */
public class CooldownManager {
    
    private final Map<UUID, Map<SkillType, Long>> cooldowns;
    
    public CooldownManager() {
        this.cooldowns = new ConcurrentHashMap<>();
    }
    
    /**
     * Set cooldown for a skill ability
     */
    public void setCooldown(UUID uuid, SkillType skill, int seconds) {
        Map<SkillType, Long> playerCooldowns = cooldowns.computeIfAbsent(uuid, k -> new ConcurrentHashMap<>());
        long expireTime = System.currentTimeMillis() + (seconds * 1000L);
        playerCooldowns.put(skill, expireTime);
    }
    
    /**
     * Check if ability is on cooldown
     */
    public boolean isOnCooldown(UUID uuid, SkillType skill) {
        Map<SkillType, Long> playerCooldowns = cooldowns.get(uuid);
        if (playerCooldowns == null) return false;
        
        Long expireTime = playerCooldowns.get(skill);
        if (expireTime == null) return false;
        
        // Check if cooldown expired
        if (System.currentTimeMillis() >= expireTime) {
            playerCooldowns.remove(skill);
            return false;
        }
        
        return true;
    }
    
    /**
     * Get remaining cooldown time in seconds
     */
    public int getRemainingCooldown(UUID uuid, SkillType skill) {
        Map<SkillType, Long> playerCooldowns = cooldowns.get(uuid);
        if (playerCooldowns == null) return 0;
        
        Long expireTime = playerCooldowns.get(skill);
        if (expireTime == null) return 0;
        
        long remaining = expireTime - System.currentTimeMillis();
        return Math.max(0, (int) (remaining / 1000));
    }
    
    /**
     * Clear all cooldowns for a player
     */
    public void clearCooldowns(UUID uuid) {
        cooldowns.remove(uuid);
    }
    
    /**
     * Clear specific cooldown
     */
    public void clearCooldown(UUID uuid, SkillType skill) {
        Map<SkillType, Long> playerCooldowns = cooldowns.get(uuid);
        if (playerCooldowns != null) {
            playerCooldowns.remove(skill);
        }
    }
}