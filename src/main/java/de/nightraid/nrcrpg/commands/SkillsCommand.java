package de.nightraid.nrcrpg.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.store.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.entity.EntityStore;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import de.nightraid.nrcrpg.NRCRPGPlugin;
import de.nightraid.nrcrpg.data.components.SkillComponent;
import de.nightraid.nrcrpg.data.components.XPComponent;
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
            // Get player from command sender
            // Based on docs: "The Player component implements CommandSender"
            // CommandSender has getUuid() which we can use to get PlayerRef
            
            // Get PlayerRef from Universe
            PlayerRef playerRef = com.hypixel.hytale.server.core.universe.Universe.get()
                .getPlayerByUuid(context.sender().getUuid());
            
            if (playerRef == null) {
                context.sender().sendMessage(
                    Message.raw("§cYou must be a player to use this command!")
                );
                return CompletableFuture.completedFuture(null);
            }
            
            // Get entity reference
            Ref<EntityStore> entityRef = playerRef.getReference();
            if (entityRef == null || !entityRef.isValid()) {
                context.sender().sendMessage(
                    Message.raw("§cUnable to access your player data!")
                );
                return CompletableFuture.completedFuture(null);
            }
            
            Store<EntityStore> store = entityRef.getStore();
            
            // Get components
            SkillComponent skills = store.getComponent(
                entityRef, plugin.getSkillComponentType()
            );
            XPComponent xp = store.getComponent(
                entityRef, plugin.getXPComponentType()
            );
            
            if (skills == null || xp == null) {
                context.sender().sendMessage(
                    Message.raw("§cNo skill data found! Join a world to initialize.")
                );
                return CompletableFuture.completedFuture(null);
            }
            
            // Display skill overview
            displaySkillOverview(context, skills, xp);
            
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
    private void displaySkillOverview(CommandContext context, SkillComponent skills, XPComponent xp) {
        context.sender().sendMessage(
            Message.raw("§6§l========== Your Skills ==========")
        );
        
        // Display each skill
        for (SkillType skillType : SkillType.values()) {
            SkillData skillData = skills.getSkill(skillType);
            double currentXP = xp.getXP(skillType);
            int currentLevel = skillData.getLevel();
            
            double xpForNext = XPCalculator.getXPForLevel(currentLevel + 1);
            double xpForCurrent = XPCalculator.getXPForLevel(currentLevel);
            double xpIntoLevel = currentXP - xpForCurrent;
            double xpNeededForLevel = xpForNext - xpForCurrent;
            double progressPercent = (xpIntoLevel / xpNeededForLevel) * 100.0;
            
            // Format: §6⛏ Mining §7Level §e15 §7[§a▓▓▓▓▓§7▓▓▓▓▓] §e45%
            String skillIcon = getSkillIcon(skillType);
            String progressBar = createProgressBar(progressPercent, 10);
            
            context.sender().sendMessage(
                Message.raw(String.format(
                    "§6%s %s §7Level §e%d §7%s §e%d%%",
                    skillIcon,
                    skillType.getDisplayName(),
                    currentLevel,
                    progressBar,
                    (int)progressPercent
                ))
            );
            
            // Show XP to next level
            context.sender().sendMessage(
                Message.raw(String.format(
                    "  §7XP: §f%,d §7/ §f%,d §8(+%,d to level up)",
                    (int)currentXP,
                    (int)xpForNext,
                    (int)(xpForNext - currentXP)
                ))
            );
        }
        
        context.sender().sendMessage(
            Message.raw("§6§l==================================")
        );
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
    
    /**
     * Get icon for skill type
     */
    private String getSkillIcon(SkillType skillType) {
        return switch (skillType) {
            case MINING -> "⛏";
            case WOODCUTTING -> "🪓";
            case FARMING -> "🌾";
            case FISHING -> "🎣";
            case COMBAT -> "⚔";
        };
    }
    
    @Override
    @Nullable
    protected String generatePermissionNode() {
        // Auto-generated: de.nightraid.nrc_rpg.command.skills
        return super.generatePermissionNode();
    }
}
