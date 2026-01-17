package de.nightraid.nrcrpg.commands;

import de.nightraid.nrcrpg.NRCRPGPlugin;
import de.nightraid.nrcrpg.skills.SkillType;

/**
 * /skills command - View skill levels and progress
 */
public class SkillsCommand {
    
    private final NRCRPGPlugin plugin;
    
    public SkillsCommand(NRCRPGPlugin plugin) {
        this.plugin = plugin;
    }
    
    /**
     * Execute /skills command
     */
    public void execute(Object sender, String[] args) {
        // if (!(sender instanceof Player player)) {
        //     sender.sendMessage("§cOnly players can use this command!");
        //     return;
        // }
        // 
        // if (args.length == 0) {
        //     displaySkillsOverview(player);
        // } else if (args.length == 1) {
        //     displaySkillDetails(player, args[0]);
        // } else if (args.length == 2 && args[0].equalsIgnoreCase("top")) {
        //     displayLeaderboard(player, args[1]);
        // }
    }
    
    /**
     * Display overview of all skills
     */
    private void displaySkillsOverview(Object player) {
        // Show all skills with levels and XP
    }
    
    /**
     * Display detailed information about a specific skill
     */
    private void displaySkillDetails(Object player, String skillName) {
        // Show abilities, bonuses, progress bar, etc.
    }
    
    /**
     * Display leaderboard for a skill
     */
    private void displayLeaderboard(Object player, String skillName) {
        // Show top 10 players for this skill
    }
}
