package de.nightraid.nrcrpg.listeners;

import de.nightraid.nrcrpg.NRCRPGPlugin;
import de.nightraid.nrcrpg.skills.SkillType;

/**
 * Handles fishing-related events for Fishing skill
 */
public class FishingListener {
    
    private final NRCRPGPlugin plugin;
    
    public FishingListener(NRCRPGPlugin plugin) {
        this.plugin = plugin;
        registerEvents();
    }
    
    private void registerEvents() {
        // eventBus.subscribe(PlayerFishEvent.class, this::onFish);
    }
}
