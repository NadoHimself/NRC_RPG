package de.nightraid.nrcrpg.commands;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.player.Player;
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
            if (!(context.sender() instanceof Player)) {
                context.sender().sendMessage(
                    Message.raw("§cThis command can only be used by players!")
                );
                return CompletableFuture.completedFuture(null);
            }
            
            Player player = (Player) context.sender();
            
            // TODO: Get player's SkillComponent from EntityStore
            // For now, send placeholder message
            context.sender().sendMessage(
                Message.raw("§6§l========== Your Skills ==========")
            );
            context.sender().sendMessage(
                Message.raw("§7Skills system integration pending...")
            );
            context.sender().sendMessage(
                Message.raw("§7Component access will be added once API is confirmed")
            );
            context.sender().sendMessage(
                Message.raw("§6§l=================================")
            );
            
        } catch (Exception e) {
            plugin.getLogger().at(Level.SEVERE).log("Error in SkillsCommand.execute", e);
            context.sender().sendMessage(
                Message.raw("§cAn error occurred while displaying skills!")
            );
        }
        
        return CompletableFuture.completedFuture(null);
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
