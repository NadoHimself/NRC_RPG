package de.nightraid.nrcrpg.commands;

import de.nightraid.nrcrpg.NRCRPGPlugin;
import de.nightraid.nrcrpg.skills.SkillType;

import java.util.UUID;

/**
 * Admin command for managing player skills
 * Usage: /skillsadmin <action> <player> [skill] [value]
 */
public class AdminCommand {
    
    private final NRCRPGPlugin plugin;
    
    public AdminCommand(NRCRPGPlugin plugin) {
        this.plugin = plugin;
        // TODO: Register with Hytale Command Manager when API available
    }
    
    /**
     * Execute admin command
     * TODO: Replace with actual Hytale command interface
     */
    public void execute(Object sender, String[] args) {
        // Placeholder for Hytale command execution
        /*
        // Check permission
        if (!sender.hasPermission("nrc_rpg.admin")) {
            sender.sendMessage("§cYou don't have permission to use this command!");
            return;
        }
        
        if (args.length < 2) {
            displayUsage(sender);
            return;
        }
        
        String action = args[0].toLowerCase();
        String targetName = args[1];
        
        // TODO: Get player UUID from name
        UUID targetUUID = getPlayerUUID(targetName);
        if (targetUUID == null) {
            sender.sendMessage("§cPlayer not found: " + targetName);
            return;
        }
        
        switch (action) {
            case "reset" -> resetPlayerSkills(sender, targetUUID, targetName);
            case "set" -> setSkillLevel(sender, targetUUID, targetName, args);
            case "add" -> addSkillXP(sender, targetUUID, targetName, args);
            case "view" -> viewPlayerSkills(sender, targetUUID, targetName);
            default -> displayUsage(sender);
        }
        */
    }
    
    /**
     * Reset all skills for a player
     */
    private void resetPlayerSkills(Object sender, UUID uuid, String playerName) {
        plugin.getSkillManager().resetSkills(uuid);
        // TODO: Send success message
        /*
        sender.sendMessage("§aReset all skills for player: " + playerName);
        */
    }
    
    /**
     * Set skill level for a player
     */
    private void setSkillLevel(Object sender, UUID uuid, String playerName, String[] args) {
        if (args.length < 4) {
            // TODO: Send usage message
            return;
        }
        
        SkillType skill = SkillType.fromString(args[2]);
        if (skill == null) {
            // TODO: Send error message
            return;
        }
        
        try {
            int level = Integer.parseInt(args[3]);
            if (level < 1 || level > 100) {
                // TODO: Send error message
                return;
            }
            
            plugin.getSkillManager().setLevel(uuid, skill, level);
            // TODO: Send success message
            /*
            sender.sendMessage(String.format(
                "§aSet %s level to %d for player: %s",
                skill.getName(), level, playerName
            ));
            */
        } catch (NumberFormatException e) {
            // TODO: Send error message
        }
    }
    
    /**
     * Add XP to a skill
     */
    private void addSkillXP(Object sender, UUID uuid, String playerName, String[] args) {
        if (args.length < 4) {
            // TODO: Send usage message
            return;
        }
        
        SkillType skill = SkillType.fromString(args[2]);
        if (skill == null) {
            // TODO: Send error message
            return;
        }
        
        try {
            double xp = Double.parseDouble(args[3]);
            plugin.getSkillManager().addXP(uuid, skill, xp);
            // TODO: Send success message
        } catch (NumberFormatException e) {
            // TODO: Send error message
        }
    }
    
    /**
     * View player skills
     */
    private void viewPlayerSkills(Object sender, UUID uuid, String playerName) {
        // TODO: Display player's skills to admin
        /*
        sender.sendMessage("§e===== Skills for " + playerName + " =====");
        for (SkillType skill : SkillType.values()) {
            int level = plugin.getSkillManager().getLevel(uuid, skill);
            double xp = plugin.getSkillManager().getXP(uuid, skill);
            sender.sendMessage(String.format(
                "%s §f%s: §aLevel %d §7(§f%.1f XP§7)",
                skill.getIcon(), skill.getName(), level, xp
            ));
        }
        sender.sendMessage("§e====================================");
        */
    }
    
    /**
     * Display command usage
     */
    private void displayUsage(Object sender) {
        // TODO: Send usage information
        /*
        sender.sendMessage("§e===== Admin Command Usage =====");
        sender.sendMessage("§7/skillsadmin reset <player>");
        sender.sendMessage("§7/skillsadmin set <player> <skill> <level>");
        sender.sendMessage("§7/skillsadmin add <player> <skill> <xp>");
        sender.sendMessage("§7/skillsadmin view <player>");
        sender.sendMessage("§e===============================");
        */
    }
    
    /**
     * Get player UUID from name
     */
    private UUID getPlayerUUID(String name) {
        // TODO: Implement player lookup
        return null;
    }
}