package de.nightraid.nrcrpg.commands;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import de.nightraid.nrcrpg.NRCRPGPlugin;

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
            // Placeholder implementation until ECS access is clarified
            context.sender().sendMessage(
                Message.raw("§6§l========== Your Skills ==========")
            );
            context.sender().sendMessage(
                Message.raw("§e⛏ Mining §7Level §e1 §7[§7■■■■■■■■■■§8] §e0%")
            );
            context.sender().sendMessage(
                Message.raw("  §7XP: §f0 §7/ §f150 §8(+150 to level up)")
            );
            context.sender().sendMessage(
                Message.raw("§e🪓 Woodcutting §7Level §e1 §7[§7■■■■■■■■■■§8] §e0%")
            );
            context.sender().sendMessage(
                Message.raw("  §7XP: §f0 §7/ §f150 §8(+150 to level up)")
            );
            context.sender().sendMessage(
                Message.raw("§6§l==================================")
            );
            context.sender().sendMessage(
                Message.raw("§7System ready! XP will be tracked once ECS is integrated.")
            );
            
        } catch (Exception e) {
            plugin.getLogger().at(Level.SEVERE).log("Error in SkillsCommand.execute", e);
            context.sender().sendMessage(
                Message.raw("§cAn error occurred while displaying skills!")
            );
        }
        
        return CompletableFuture.completedFuture(null);
    }
    
    @Override
    @Nullable
    protected String generatePermissionNode() {
        // Auto-generated: de.nightraid.nrc_rpg.command.skills
        return super.generatePermissionNode();
    }
}
