package de.nightraid.nrcrpg.data.components;

import com.hypixel.hytale.component.Component;
import de.nightraid.nrcrpg.skills.SkillType;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.Map;

/**
 * Component that stores XP for each skill
 */
public class XPComponent implements Component {
    
    private final Map<SkillType, Double> xpMap;
    
    public XPComponent() {
        this.xpMap = new EnumMap<>(SkillType.class);
        
        // Initialize all XP values to 0
        for (SkillType type : SkillType.values()) {
            xpMap.put(type, 0.0);
        }
    }
    
    /**
     * Get XP for a specific skill
     */
    public double getXP(SkillType type) {
        return xpMap.getOrDefault(type, 0.0);
    }
    
    /**
     * Add XP to a skill
     */
    public void addXP(SkillType type, double amount) {
        double currentXP = getXP(type);
        xpMap.put(type, currentXP + amount);
    }
    
    /**
     * Set XP for a skill
     */
    public void setXP(SkillType type, double amount) {
        xpMap.put(type, Math.max(0, amount));
    }
    
    /**
     * Get all XP values
     */
    public Map<SkillType, Double> getAllXP() {
        return xpMap;
    }
    
    @Override
    @Nullable
    public Component clone() {
        XPComponent copy = new XPComponent();
        copy.xpMap.putAll(this.xpMap);
        return copy;
    }
}
