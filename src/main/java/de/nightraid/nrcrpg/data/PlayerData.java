package de.nightraid.nrcrpg.data;

import de.nightraid.nrcrpg.skills.SkillData;
import de.nightraid.nrcrpg.skills.SkillType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Container for all player skill data
 */
public class PlayerData {
    
    private final UUID uuid;
    private final Map<SkillType, SkillData> skills;
    private final Map<String, Long> cooldowns;
    private final PlayerStatistics statistics;
    
    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        this.skills = new HashMap<>();
        this.cooldowns = new HashMap<>();
        this.statistics = new PlayerStatistics();
        
        // Initialize all skills
        for (SkillType type : SkillType.values()) {
            skills.put(type, new SkillData());
        }
    }
    
    public UUID getUuid() {
        return uuid;
    }
    
    public SkillData getSkillData(SkillType type) {
        return skills.computeIfAbsent(type, k -> new SkillData());
    }
    
    public Map<SkillType, SkillData> getAllSkills() {
        return skills;
    }
    
    public Map<String, Long> getCooldowns() {
        return cooldowns;
    }
    
    public PlayerStatistics getStatistics() {
        return statistics;
    }
}