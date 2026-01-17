package de.nightraid.nrcrpg.managers;

import de.nightraid.nrcrpg.NRCRPGPlugin;

import java.util.logging.Level;

public class MessageManager {
    
    private final NRCRPGPlugin plugin;
    
    public MessageManager(NRCRPGPlugin plugin) {
        this.plugin = plugin;
        plugin.getLogger().at(Level.INFO).log("MessageManager initialized");
    }
    
    public String getMessage(String key) {
        return key; // TODO: Load from messages.json
    }
}
