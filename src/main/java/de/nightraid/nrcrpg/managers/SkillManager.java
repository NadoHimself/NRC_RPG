package de.nightraid.nrcrpg.managers;

import de.nightraid.nrcrpg.NRCRPGPlugin;
import de.nightraid.nrcrpg.skills.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages all skills and their interactions
 */
public class SkillManager {
    
    private final NRCRPGPlugin plugin;
    private final XPManager xpManager;
    private final Map<SkillType, Skill> skills;
    
    public SkillManager(NRCRPGPlugin plugin, XPManager xpManager) {
        this.plugin = plugin;
        this.xpManager = xpManager;
        this.skills = new HashMap<>();
        
        initializeSkills();
    }
    
    /**
     * Initialize all skills
     */
    private void initializeSkills() {
        // Register all skill implementations
        registerSkill(new CombatSkill(plugin));
        // registerSkill(new MiningSkill(plugin));
        // registerSkill(new WoodcuttingSkill(plugin));
        // registerSkill(new FarmingSkill(plugin));
        // registerSkill(new FishingSkill(plugin));
        
        plugin.getLogger().info("Registered " + skills.size() + " skills");
    }
    
    /**
     * Register a skill
     */
    private void registerSkill(Skill skill) {
        skills.put(skill.getType(), skill);
    }
    
    /**
     * Get a skill by type
     */
    public Skill getSkill(SkillType type) {
        return skills.get(type);
    }
    
    /**
     * Get all registered skills
     */
    public Map<SkillType, Skill> getAllSkills() {
        return new HashMap<>(skills);
    }
    
    /**
     * Add XP to a player's skill
     * 
     * @param player The player
     * @param type The skill type
     * @param amount XP amount
     */
    public void addXP(Object player, SkillType type, double amount) {
        // This would use Hytale's player API and components
        // SkillComponent skills = player.getComponent(SkillComponent.class);
        // if (skills != null) {
        //     skills.addXP(type, amount);
        // }
    }
    
    /**
     * Check if a player has permission for a skill
     */
    public boolean hasSkillPermission(Object player, SkillType type) {
        // Check permission: nrc_rpg.skill.{skill_name}
        // return player.hasPermission("nrc_rpg.skill." + type.name().toLowerCase());
        return true; // Placeholder
    }
}
