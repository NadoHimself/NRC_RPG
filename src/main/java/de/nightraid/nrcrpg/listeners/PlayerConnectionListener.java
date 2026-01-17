package de.nightraid.nrcrpg.listeners;

import com.hypixel.hytale.server.core.event.events.player.PlayerConnectEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent;
import de.nightraid.nrcrpg.NRCRPGPlugin;

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
            // Get player from event
            var player = event.getPlayer();
            if (player == null) {
                return;
            }
            
            plugin.getLogger().at(Level.INFO).log(
                "Player connecting: " + player.getUsername()
            );
            
            // Load player data from disk asynchronously
            CompletableFuture.runAsync(() -> {
                try {
                    plugin.getDataManager().loadPlayerData(player);
                    plugin.getLogger().at(Level.INFO).log(
                        "Loaded skill data for " + player.getUsername()
                    );
                } catch (Exception e) {
                    plugin.getLogger().at(Level.SEVERE).log(
                        "Failed to load data for " + player.getUsername(), e
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
            // Get player from event
            var player = event.getPlayer();
            if (player == null) {
                return;
            }
            
            plugin.getLogger().at(Level.INFO).log(
                "Player disconnecting: " + player.getUsername()
            );
            
            // Save player data asynchronously
            // This is already running on an async thread due to registerAsync
            try {
                plugin.getDataManager().savePlayerData(player);
                plugin.getLogger().at(Level.INFO).log(
                    "Saved skill data for " + player.getUsername()
                );
            } catch (Exception e) {
                plugin.getLogger().at(Level.SEVERE).log(
                    "Failed to save data for " + player.getUsername(), e
                );
            }
            
        } catch (Exception e) {
            plugin.getLogger().at(Level.SEVERE).log(
                "Error in PlayerConnectionListener.onPlayerDisconnect", e
            );
        }
    }
}
