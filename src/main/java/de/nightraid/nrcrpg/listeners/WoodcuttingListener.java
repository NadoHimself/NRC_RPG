package de.nightraid.nrcrpg.listeners;

import de.nightraid.nrcrpg.NRCRPGPlugin;
import de.nightraid.nrcrpg.skills.SkillType;

/**
 * Handles woodcutting-related events for Woodcutting skill
 */
public class WoodcuttingListener {
    
    private final NRCRPGPlugin plugin;
    
    public WoodcuttingListener(NRCRPGPlugin plugin) {
        this.plugin = plugin;
        registerEvents();
    }
    
    private void registerEvents() {
        // eventBus.subscribe(BlockBreakEvent.class, this::onBlockBreak);
    }
    
    public void onBlockBreak(Object event) {
        // Check if wood block
        // Grant XP based on wood type
    }
}
