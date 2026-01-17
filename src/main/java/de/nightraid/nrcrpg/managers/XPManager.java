package de.nightraid.nrcrpg.managers;

import de.nightraid.nrcrpg.NRCRPGPlugin;
import de.nightraid.nrcrpg.skills.SkillType;

import java.util.logging.Level;

public class XPManager {
    
    private final NRCRPGPlugin plugin;
    
    public XPManager(NRCRPGPlugin plugin) {
        this.plugin = plugin;
        plugin.getLogger().at(Level.INFO).log("XPManager initialized");
    }
    
    public void processXP(Object player, SkillType skillType, double amount) {
        try {
            plugin.getLogger().at(Level.FINE).log(
                "Processing " + amount + " XP for " + skillType
            );
        } catch (Exception e) {
            plugin.getLogger().at(Level.SEVERE).log("Error processing XP", e);
        }
    }
}
