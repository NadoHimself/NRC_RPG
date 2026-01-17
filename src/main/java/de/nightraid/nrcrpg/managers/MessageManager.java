package de.nightraid.nrcrpg.managers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import de.nightraid.nrcrpg.NRCRPGPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages localized messages
 */
public class MessageManager {
    
    private final Map<String, String> messages;
    private final String language;
    
    public MessageManager(String language) {
        this.language = language;
        this.messages = new HashMap<>();
        loadMessages();
    }
    
    /**
     * Load messages from resource file
     */
    private void loadMessages() {
        String fileName = "messages/" + language + ".json";
        
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (stream == null) {
                NRCRPGPlugin.getPluginLogger().warn("Message file not found: {}", fileName);
                return;
            }
            
            Gson gson = new Gson();
            Map<String, String> loaded = gson.fromJson(
                new InputStreamReader(stream),
                new TypeToken<Map<String, String>>(){}.getType()
            );
            
            messages.putAll(loaded);
            NRCRPGPlugin.getPluginLogger().info("Loaded {} messages for language: {}", messages.size(), language);
            
        } catch (IOException e) {
            NRCRPGPlugin.getPluginLogger().error("Failed to load messages!", e);
        }
    }
    
    /**
     * Get message by key
     */
    public String get(String key) {
        return messages.getOrDefault(key, key);
    }
    
    /**
     * Get message with placeholders
     */
    public String get(String key, Object... replacements) {
        String message = get(key);
        return format(message, replacements);
    }
    
    /**
     * Format message with placeholders
     */
    private String format(String message, Object... replacements) {
        for (int i = 0; i < replacements.length; i += 2) {
            if (i + 1 < replacements.length) {
                String placeholder = "{" + replacements[i] + "}";
                String value = String.valueOf(replacements[i + 1]);
                message = message.replace(placeholder, value);
            }
        }
        return translateColorCodes(message);
    }
    
    /**
     * Translate color codes (§)
     */
    private String translateColorCodes(String message) {
        // TODO: Replace with Hytale's color code system when available
        return message;
    }
}