package de.nightraid.nrcrpg.skills;

/**
 * Enum representing all available skill types
 */
public enum SkillType {
    
    COMBAT("Combat", "⚔️", "Kampf"),
    MINING("Mining", "⛏️", "Bergbau"),
    WOODCUTTING("Woodcutting", "🪓", "Holzfällen"),
    FARMING("Farming", "🌾", "Landwirtschaft"),
    FISHING("Fishing", "🎣", "Angeln");
    
    private final String name;
    private final String icon;
    private final String germanName;
    
    SkillType(String name, String icon, String germanName) {
        this.name = name;
        this.icon = icon;
        this.germanName = germanName;
    }
    
    public String getName() {
        return name;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public String getGermanName() {
        return germanName;
    }
    
    public String getDisplayName() {
        return icon + " " + name;
    }
    
    public static SkillType fromString(String str) {
        for (SkillType type : values()) {
            if (type.name.equalsIgnoreCase(str) || type.name().equalsIgnoreCase(str)) {
                return type;
            }
        }
        return null;
    }
}