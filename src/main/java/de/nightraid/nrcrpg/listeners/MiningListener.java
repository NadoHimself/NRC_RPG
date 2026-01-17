package de.nightraid.nrcrpg.listeners;

import de.nightraid.nrcrpg.NRCRPGPlugin;

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
            // TODO: Get player from event and award XP
            // Waiting for clarification on how to get PlayerRef from BreakBlockEvent
            
            String blockType = event.getBlockType().toString().toLowerCase();
            double xpAmount = calculateMiningXP(blockType);
            
            if (xpAmount > 0) {
                plugin.getLogger().at(Level.FINE).log(
                    "Block broken: " + blockType + " (would award " + xpAmount + " Mining XP)"
                );
            }
            
        } catch (Exception e) {
            plugin.getLogger().at(Level.SEVERE).log(
                "Error in MiningListener.onBlockBreak", e
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
