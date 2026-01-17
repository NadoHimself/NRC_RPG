package de.nightraid.nrcrpg.listeners;

import de.nightraid.nrcrpg.NRCRPGPlugin;
import de.nightraid.nrcrpg.skills.SkillType;

/**
 * Handles mining-related events for Mining skill
 */
public class MiningListener {
    
    private final NRCRPGPlugin plugin;
    
    public MiningListener(NRCRPGPlugin plugin) {
        this.plugin = plugin;
        registerEvents();
    }
    
    private void registerEvents() {
        // eventBus.subscribe(BlockBreakEvent.class, this::onBlockBreak);
    }
    
    public void onBlockBreak(Object event) {
        // BlockBreakEvent e = (BlockBreakEvent) event;
        // Block block = e.getBlock();
        // Player player = e.getPlayer();
        // 
        // if (isOre(block)) {
        //     double xp = getOreXP(block);
        //     plugin.getXPManager().grantXP(player, SkillType.MINING, xp);
        // }
    }
}
