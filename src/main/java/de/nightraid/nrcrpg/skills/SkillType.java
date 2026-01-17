package de.nightraid.nrcrpg.skills;

/**
 * Enum representing all available skill types in NRC_RPG
 */
public enum SkillType {
    
    COMBAT("Combat", "⚔️"),
    MINING("Mining", "⛏️"),
    WOODCUTTING("Woodcutting", "🪓"),
    FARMING("Farming", "🌾"),
    FISHING("Fishing", "🎣");
    
    private final String displayName;
    private final String icon;
    
    SkillType(String displayName, String icon) {
        this.displayName = displayName;
        this.icon = icon;
    }
    
    /**
     * Get the display name of this skill
     */
    public String getDisplayName() {
        return displayName;
    }
    
    /**
     * Get the icon/emoji for this skill
     */
    public String getIcon() {
        return icon;
    }
    
    /**
     * Get formatted name with icon
     */
    public String getFormattedName() {
        return icon + " " + displayName;
    }
    
    /**
     * Parse skill type from string (case-insensitive)
     */
    public static SkillType fromString(String name) {
        for (SkillType type : values()) {
            if (type.name().equalsIgnoreCase(name) || 
                type.displayName.equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}
