package de.nightraid.nrcrpg.commands;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.player.PlayerRef;
import de.nightraid.nrcrpg.NRCRPGPlugin;
import de.nightraid.nrcrpg.data.components.SkillComponent;
import de.nightraid.nrcrpg.skills.SkillData;
import de.nightraid.nrcrpg.skills.SkillType;
import de.nightraid.nrcrpg.util.XPCalculator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

/**
 * Main command for viewing skill information
 * Usage: /skills [skill]
 */
public class SkillsCommand extends AbstractCommand {
    
    private final NRCRPGPlugin plugin;
    
    public SkillsCommand(NRCRPGPlugin plugin) {
        super("skills", "View your skill levels and progression");
        this.plugin = plugin;
        
        // Add aliases
        addAliases("skill", "s");
    }
    
    @Override
    @Nullable
    protected CompletableFuture<Void> execute(@Nonnull CommandContext context) {
        try {
            // Check if sender is a player
            if (!(context.sender() instanceof PlayerRef)) {
                context.sender().sendMessage(
                    Message.raw("§cThis command can only be used by players!")
                );
                return CompletableFuture.completedFuture(null);
            }
            
            PlayerRef playerRef = (PlayerRef) context.sender();
            
            // Get player's SkillComponent
            var store = playerRef.store();
            SkillComponent skillComp = store.getComponent(playerRef, SkillComponent.class);
            
            if (skillComp == null) {
                context.sender().sendMessage(
                    Message.raw("§cError: Skill data not found! Please rejoin the server.")
                );
                return CompletableFuture.completedFuture(null);
            }
            
            // Display all skills
            displaySkillsOverview(context, skillComp);
            
        } catch (Exception e) {
            plugin.getLogger().at(Level.SEVERE).log("Error in SkillsCommand.execute", e);
            context.sender().sendMessage(
                Message.raw("§cAn error occurred while displaying skills!")
            );
        }
        
        return CompletableFuture.completedFuture(null);
    }
    
    /**
     * Display overview of all skills
     */
    private void displaySkillsOverview(CommandContext context, SkillComponent skillComp) {
        var sender = context.sender();
        
        // Header
        sender.sendMessage(Message.raw("§6§l========== Your Skills =========="));
        sender.sendMessage(Message.raw(""));
        
        // Display each skill
        for (SkillType type : SkillType.values()) {
            SkillData data = skillComp.getSkillData(type);
            int level = data.getLevel();
            double currentXP = data.getXp();
            int requiredXP = XPCalculator.getRequiredXP(level);
            
            // Calculate progress percentage
            double progress = (currentXP / requiredXP) * 100.0;
            
            // Create progress bar
            String progressBar = createProgressBar(progress, 20);
            
            // Format: [Icon] Skill Name - Level XX [Progress Bar] (XP/Total XP)
            String line = String.format(
                "§e%s §f%s §7- §aLevel %d §8%s §7(%.1f/%.0f XP)",
                type.getIcon(),
                type.getName(),
                level,
                progressBar,
                currentXP,
                (double) requiredXP
            );
            
            sender.sendMessage(Message.raw(line));
        }
        
        // Footer
        sender.sendMessage(Message.raw(""));
        sender.sendMessage(Message.raw("§7Use §e/skills <skill> §7for detailed info"));
        sender.sendMessage(Message.raw("§6§l================================="));
    }
    
    /**
     * Create a visual progress bar
     * @param progress Progress percentage (0-100)
     * @param length Number of characters in bar
     * @return Formatted progress bar string
     */
    private String createProgressBar(double progress, int length) {
        int filled = (int) Math.round((progress / 100.0) * length);
        int empty = length - filled;
        
        StringBuilder bar = new StringBuilder("[");
        
        // Filled portion (green)
        bar.append("§a");
        for (int i = 0; i < filled; i++) {
            bar.append("■");
        }
        
        // Empty portion (gray)
        bar.append("§7");
        for (int i = 0; i < empty; i++) {
            bar.append("■");
        }
        
        bar.append("§8]");
        
        return bar.toString();
    }
    
    @Override
    @Nullable
    protected String generatePermissionNode() {
        // Auto-generated: de.nightraid.nrc_rpg.command.skills
        // Can be customized or return null to disable permission check
        return super.generatePermissionNode();
    }
}
