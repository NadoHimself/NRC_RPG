package de.nightraid.nrcrpg.listeners;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.store.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.EntityStore;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import de.nightraid.nrcrpg.NRCRPGPlugin;
import de.nightraid.nrcrpg.data.components.SkillComponent;
import de.nightraid.nrcrpg.data.components.StatisticsComponent;
import de.nightraid.nrcrpg.data.components.XPComponent;
import de.nightraid.nrcrpg.skills.SkillData;
import de.nightraid.nrcrpg.skills.SkillType;
import de.nightraid.nrcrpg.util.XPCalculator;

import java.util.logging.Level;

/**
 * Listener for mining events
 * Awards XP based on block type and handles level-ups
 */
public class MiningListener {
    
    private final NRCRPGPlugin plugin;
    
    public MiningListener(NRCRPGPlugin plugin) {
        this.plugin = plugin;
        registerEvents();
    }
    
    private void registerEvents() {
        // Register BreakBlockEvent listener
        plugin.getEventRegistry().register(
            com.hypixel.hytale.server.core.event.events.ecs.BreakBlockEvent.class,
            this::onBlockBreak
        );
        
        plugin.getLogger().at(Level.INFO).log("MiningListener registered");
    }
    
    /**
     * Called when a player breaks a block
     * Awards mining XP based on block type
     */
    private void onBlockBreak(com.hypixel.hytale.server.core.event.events.ecs.BreakBlockEvent event) {
        try {
            // Get the player who broke the block
            // BreakBlockEvent provides: ItemStack, Vector3i (position), BlockType
            // We need to get the Player entity from the event
            // Based on docs: events provide the entity that triggered them
            
            // Get block type name for XP calculation
            String blockType = event.getBlockType().toString().toLowerCase();
            
            // Calculate XP based on block type
            double xpGained = calculateMiningXP(blockType);
            
            if (xpGained <= 0) {
                return; // No XP for this block type
            }
            
            // TODO: Get PlayerRef from event when API is clarified
            // For now, we need to find a way to get the player who broke the block
            // This will require additional event information or context
            
            plugin.getLogger().at(Level.FINE).log(
                "Block broken: " + blockType + " (would award " + xpGained + " Mining XP)"
            );
            
        } catch (Exception e) {
            plugin.getLogger().at(Level.SEVERE).log(
                "Error in MiningListener.onBlockBreak", e
            );
        }
    }
    
    /**
     * Awards mining XP to a player and handles level-ups
     */
    private void awardMiningXP(PlayerRef playerRef, double xpAmount) {
        try {
            // Get player entity reference
            Ref<EntityStore> entityRef = playerRef.getReference();
            if (entityRef == null || !entityRef.isValid()) {
                return;
            }
            
            Store<EntityStore> store = entityRef.getStore();
            
            // Get player component to send messages
            Player player = store.getComponent(entityRef, Player.getComponentType());
            if (player == null) {
                return;
            }
            
            // Get or create components
            SkillComponent skills = store.ensureAndGetComponent(
                entityRef, plugin.getSkillComponentType()
            );
            XPComponent xp = store.ensureAndGetComponent(
                entityRef, plugin.getXPComponentType()
            );
            StatisticsComponent stats = store.ensureAndGetComponent(
                entityRef, plugin.getStatisticsComponentType()
            );
            
            // Get current skill data
            SkillData skillData = skills.getSkill(SkillType.MINING);
            int oldLevel = skillData.getLevel();
            double oldXP = xp.getXP(SkillType.MINING);
            
            // Add XP
            xp.addXP(SkillType.MINING, xpAmount);
            double newXP = xp.getXP(SkillType.MINING);
            
            // Check for level-up
            int newLevel = XPCalculator.getLevelForXP(newXP);
            
            if (newLevel > oldLevel) {
                // LEVEL UP!
                skillData.setLevel(newLevel);
                
                // Send level-up message
                player.sendMessage(
                    Message.raw("§6§l✦ LEVEL UP! §r§6Mining: " + oldLevel + " → " + newLevel)
                );
                player.sendMessage(
                    Message.raw("§7Next level in: §e" + 
                        (int)(XPCalculator.getXPForLevel(newLevel + 1) - newXP) + " XP")
                );
                
                plugin.getLogger().at(Level.INFO).log(
                    playerRef.getUsername() + " leveled up Mining to " + newLevel
                );
            } else {
                // Send XP gain message
                player.sendMessage(
                    Message.raw("§a+" + (int)xpAmount + " Mining XP §7(" + 
                        (int)(XPCalculator.getXPForLevel(newLevel + 1) - newXP) + " to level " + (newLevel + 1) + ")")
                );
            }
            
            // Update statistics
            stats.incrementStat("mining_blocks_broken");
            stats.addToStat("mining_xp_earned", xpAmount);
            
        } catch (Exception e) {
            plugin.getLogger().at(Level.SEVERE).log(
                "Error awarding mining XP to " + playerRef.getUsername(), e
            );
        }
    }
    
    /**
     * Calculate XP based on block type
     * @param blockType The type of block broken
     * @return XP amount to award
     */
    private double calculateMiningXP(String blockType) {
        // Check for ore types
        if (blockType.contains("stone")) {
            return 5.0;
        } else if (blockType.contains("coal")) {
            return 10.0;
        } else if (blockType.contains("iron")) {
            return 20.0;
        } else if (blockType.contains("gold")) {
            return 30.0;
        } else if (blockType.contains("diamond")) {
            return 50.0;
        } else if (blockType.contains("emerald")) {
            return 75.0;
        } else if (blockType.contains("ore")) {
            return 15.0; // Generic ore
        }
        
        return 0.0; // No XP for non-mining blocks
    }
}
