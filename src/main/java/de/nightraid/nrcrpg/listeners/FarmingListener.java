package de.nightraid.nrcrpg.listeners;

import de.nightraid.nrcrpg.NRCRPGPlugin;
import de.nightraid.nrcrpg.skills.SkillType;

/**
 * Handles farming-related events for Farming skill
 */
public class FarmingListener {
    
    private final NRCRPGPlugin plugin;
    
    public FarmingListener(NRCRPGPlugin plugin) {
        this.plugin = plugin;
        registerEvents();
    }
    
    private void registerEvents() {
        // eventBus.subscribe(PlayerHarvestEvent.class, this::onHarvest);
        // eventBus.subscribe(PlayerBreedEntityEvent.class, this::onBreed);
    }
}
