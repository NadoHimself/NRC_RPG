package de.nightraid.nrcrpg.managers;

import de.nightraid.nrcrpg.NRCRPGPlugin;
import de.nightraid.nrcrpg.skills.SkillData;
import de.nightraid.nrcrpg.skills.SkillType;
import de.nightraid.nrcrpg.util.XPCalculator;

import java.util.logging.Level;

public class SkillManager {
    
    private final NRCRPGPlugin plugin;
    
    public SkillManager(NRCRPGPlugin plugin) {
        this.plugin = plugin;
        plugin.getLogger().at(Level.INFO).log("SkillManager initialized");
    }
    
    public void addSkillXP(Object player, SkillType skillType, double amount) {
        try {
            plugin.getLogger().at(Level.FINE).log(
                "Adding " + amount + " XP to " + skillType
            );
        } catch (Exception e) {
            plugin.getLogger().at(Level.SEVERE).log("Error adding skill XP", e);
        }
    }
    
    public void checkLevelUp(Object player, SkillType skillType, SkillData skillData) {
        int currentLevel = skillData.getLevel();
        double currentXP = skillData.getXp();
        
        int requiredXP = XPCalculator.getRequiredXP(currentLevel);
        
        if (currentXP >= requiredXP && currentLevel < 100) {
            skillData.levelUp();
            skillData.setXp(currentXP - requiredXP);
            
            plugin.getLogger().at(Level.INFO).log(
                "Player leveled up " + skillType + " to " + (currentLevel + 1)
            );
        }
    }
    
    public void shutdown() {
        plugin.getLogger().at(Level.INFO).log("SkillManager shut down");
    }
}
