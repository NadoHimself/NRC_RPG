package de.nightraid.nrcrpg.managers;

import de.nightraid.nrcrpg.NRCRPGPlugin;
import de.nightraid.nrcrpg.skills.SkillType;
import de.nightraid.nrcrpg.util.XPCalculator;

/**
 * Manages XP calculations and distribution
 */
public class XPManager {
    
    private final NRCRPGPlugin plugin;
    
    public XPManager(NRCRPGPlugin plugin) {
        this.plugin = plugin;
    }
    
    /**
     * Calculate XP with all modifiers applied
     * 
     * @param player The player
     * @param baseXP Base XP amount
     * @return Modified XP amount
     */
    public double calculateFinalXP(Object player, double baseXP) {
        double multiplier = plugin.getConfig().getXpMultiplier();
        
        // Apply global multiplier
        double finalXP = baseXP * multiplier;
        
        // Apply permission-based multipliers
        // double permMultiplier = getPermissionMultiplier(player);
        // finalXP *= permMultiplier;
        
        return finalXP;
    }
    
    /**
     * Grant XP to a player for a specific skill
     */
    public void grantXP(Object player, SkillType type, double amount) {
        // Calculate final XP
        double finalXP = calculateFinalXP(player, amount);
        
        // Add to player's skill
        plugin.getSkillManager().addXP(player, type, finalXP);
        
        // Send XP gain message
        sendXPMessage(player, type, finalXP);
    }
    
    /**
     * Send XP gain message to player
     */
    private void sendXPMessage(Object player, SkillType type, double xp) {
        // Format: +15.5 Combat XP
        String message = String.format("§7+%.1f %s XP", xp, type.getDisplayName());
        // player.sendMessage(message);
    }
    
    /**
     * Handle level up for a player
     */
    public void handleLevelUp(Object player, SkillType type, int newLevel) {
        // Play sound
        // player.playSound(Sound.LEVEL_UP);
        
        // Spawn particles
        // spawnLevelUpParticles(player);
        
        // Send message
        String message = String.format("§a§lLEVEL UP! §e%s §ais now Level §e%d§a!",
            type.getDisplayName(), newLevel);
        // player.sendMessage(message);
        
        // Check for ability unlocks
        checkAbilityUnlocks(player, type, newLevel);
        
        // Broadcast milestones
        broadcastMilestone(player, type, newLevel);
    }
    
    /**
     * Check and notify about ability unlocks
     */
    private void checkAbilityUnlocks(Object player, SkillType type, int level) {
        // Check if any abilities are unlocked at this level
        // Send notification if so
    }
    
    /**
     * Broadcast level milestones to server
     */
    private void broadcastMilestone(Object player, SkillType type, int level) {
        // Check if this level is a milestone (25, 50, 75, 100)
        if (level == 25 || level == 50 || level == 75 || level == 100) {
            // Broadcast to all players
            // server.broadcast(message);
        }
    }
}
