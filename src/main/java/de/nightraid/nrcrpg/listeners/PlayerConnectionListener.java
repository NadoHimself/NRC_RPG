package de.nightraid.nrcrpg.listeners;

import com.hypixel.hytale.server.core.event.events.player.PlayerConnectEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent;
import de.nightraid.nrcrpg.NRCRPGPlugin;
import de.nightraid.nrcrpg.data.components.CooldownComponent;
import de.nightraid.nrcrpg.data.components.SkillComponent;
import de.nightraid.nrcrpg.data.components.StatisticsComponent;
import de.nightraid.nrcrpg.data.components.XPComponent;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

/**
 * Listener for player connection/disconnection events
 * Handles loading and saving player skill data
 */
public class PlayerConnectionListener {
    
    private final NRCRPGPlugin plugin;
    
    public PlayerConnectionListener(NRCRPGPlugin plugin) {
        this.plugin = plugin;
        registerEvents();
    }
    
    private void registerEvents() {
        // Register player connect event
        plugin.getEventRegistry().register(
            PlayerConnectEvent.class,
            this::onPlayerConnect
        );
        
        // Register player disconnect event (async)
        plugin.getEventRegistry().registerAsync(
            PlayerDisconnectEvent.class,
            future -> future.thenApply(event -> {
                onPlayerDisconnect(event);
                return event;
            })
        );
        
        plugin.getLogger().at(Level.INFO).log("PlayerConnectionListener registered");
    }
    
    /**
     * Called when a player connects to the server
     * Loads player data and initializes components
     */
    private void onPlayerConnect(PlayerConnectEvent event) {
        try {
            // Get player reference
            var playerRef = event.playerRef();
            if (playerRef == null || !playerRef.isAlive()) {
                return;
            }
            
            plugin.getLogger().at(Level.INFO).log(
                "Player connecting: " + playerRef.username() + " (" + playerRef.uuid() + ")"
            );
            
            // Get entity store
            var store = playerRef.store();
            
            // Initialize or load components
            // Components should already be added by ECS on entity creation,
            // but we need to load saved data
            
            // Get existing components (created by ECS registration)
            SkillComponent skillComp = store.getComponent(playerRef, SkillComponent.class);
            XPComponent xpComp = store.getComponent(playerRef, XPComponent.class);
            CooldownComponent cooldownComp = store.getComponent(playerRef, CooldownComponent.class);
            StatisticsComponent statsComp = store.getComponent(playerRef, StatisticsComponent.class);
            
            // Check if components exist
            if (skillComp == null) {
                plugin.getLogger().at(Level.WARNING).log(
                    "SkillComponent not found for player " + playerRef.username() + 
                    " - components may not be registered correctly"
                );
                return;
            }
            
            // Load player data from disk asynchronously
            CompletableFuture.runAsync(() -> {
                try {
                    plugin.getDataManager().loadPlayerData(playerRef);
                    plugin.getLogger().at(Level.INFO).log(
                        "Loaded skill data for " + playerRef.username()
                    );
                } catch (Exception e) {
                    plugin.getLogger().at(Level.SEVERE).log(
                        "Failed to load data for " + playerRef.username(), e
                    );
                }
            });
            
        } catch (Exception e) {
            plugin.getLogger().at(Level.SEVERE).log(
                "Error in PlayerConnectionListener.onPlayerConnect", e
            );
        }
    }
    
    /**
     * Called when a player disconnects from the server
     * Saves player data asynchronously
     */
    private void onPlayerDisconnect(PlayerDisconnectEvent event) {
        try {
            // Get player reference
            var playerRef = event.playerRef();
            if (playerRef == null) {
                return;
            }
            
            plugin.getLogger().at(Level.INFO).log(
                "Player disconnecting: " + playerRef.username() + 
                " (Reason: " + event.reason() + ")"
            );
            
            // Save player data asynchronously
            // This is already running on an async thread due to registerAsync
            try {
                plugin.getDataManager().savePlayerData(playerRef);
                plugin.getLogger().at(Level.INFO).log(
                    "Saved skill data for " + playerRef.username()
                );
            } catch (Exception e) {
                plugin.getLogger().at(Level.SEVERE).log(
                    "Failed to save data for " + playerRef.username(), e
                );
            }
            
        } catch (Exception e) {
            plugin.getLogger().at(Level.SEVERE).log(
                "Error in PlayerConnectionListener.onPlayerDisconnect", e
            );
        }
    }
}
