package de.nightraid.nrcrpg.data.components;

import com.hypixel.hytale.component.Component;
import de.nightraid.nrcrpg.skills.SkillData;
import de.nightraid.nrcrpg.skills.SkillType;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.Map;

/**
 * Component that stores skill data for an entity
 */
public class SkillComponent implements Component {
    
    private final Map<SkillType, SkillData> skills;
    
    public SkillComponent() {
        this.skills = new EnumMap<>(SkillType.class);
        
        // Initialize all skills
        for (SkillType type : SkillType.values()) {
            skills.put(type, new SkillData(type));
        }
    }
    
    /**
     * Get skill data for a specific skill type
     */
    public SkillData getSkill(SkillType type) {
        return skills.get(type);
    }
    
    /**
     * Get all skills
     */
    public Map<SkillType, SkillData> getAllSkills() {
        return skills;
    }
    
    @Override
    @Nullable
    public Component clone() {
        SkillComponent copy = new SkillComponent();
        
        // Deep copy all skill data
        for (Map.Entry<SkillType, SkillData> entry : skills.entrySet()) {
            SkillData originalData = entry.getValue();
            copy.skills.put(
                entry.getKey(),
                new SkillData(originalData.getSkillType(), originalData.getLevel())
            );
        }
        
        return copy;
    }
}
