package de.nightraid.nrcrpg.data.components;

import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import de.nightraid.nrcrpg.skills.SkillData;
import de.nightraid.nrcrpg.skills.SkillType;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ECS Component storing skill data for players
 * Stores levels, XP, and total XP for all skills
 */
public class SkillComponent implements Component<EntityStore> {
    
    private final Map<SkillType, SkillData> skills;
    
    /**
     * Default constructor required by ECS
     */
    public SkillComponent() {
        this.skills = new ConcurrentHashMap<>();
        
        // Initialize all skills with default values
        for (SkillType type : SkillType.values()) {
            skills.put(type, new SkillData());
        }
    }
    
    /**
     * Clone constructor for ECS operations
     */
    private SkillComponent(Map<SkillType, SkillData> skills) {
        this.skills = new ConcurrentHashMap<>();
        // Deep copy skill data
        skills.forEach((type, data) -> 
            this.skills.put(type, data.clone())
        );
    }
    
    /**
     * Required by ECS - creates a deep copy of this component
     */
    @Override
    @Nonnull
    public Component<EntityStore> clone() {
        return new SkillComponent(this.skills);
    }
    
    // === Skill Data Access ===
    
    public int getLevel(SkillType type) {
        return skills.getOrDefault(type, new SkillData()).getLevel();
    }
    
    public double getXP(SkillType type) {
        return skills.getOrDefault(type, new SkillData()).getXp();
    }
    
    public double getTotalXP(SkillType type) {
        return skills.getOrDefault(type, new SkillData()).getTotalXP();
    }
    
    public SkillData getSkillData(SkillType type) {
        return skills.getOrDefault(type, new SkillData());
    }
    
    public Map<SkillType, SkillData> getAllSkills() {
        return new ConcurrentHashMap<>(skills);
    }
    
    // === Skill Data Modification ===
    
    public void setLevel(SkillType type, int level) {
        skills.computeIfAbsent(type, k -> new SkillData()).setLevel(level);
    }
    
    public void setXP(SkillType type, double xp) {
        skills.computeIfAbsent(type, k -> new SkillData()).setXp(xp);
    }
    
    public void addXP(SkillType type, double amount) {
        SkillData data = skills.computeIfAbsent(type, k -> new SkillData());
        data.addXP(amount);
    }
    
    public void setSkillData(SkillType type, SkillData data) {
        skills.put(type, data);
    }
    
    public void resetSkill(SkillType type) {
        skills.put(type, new SkillData());
    }
    
    public void resetAllSkills() {
        skills.clear();
        for (SkillType type : SkillType.values()) {
            skills.put(type, new SkillData());
        }
    }
}
