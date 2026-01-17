package de.nightraid.nrcrpg.data.components;

import com.hypixel.hytale.component.Component;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Component that tracks various player statistics
 */
public class StatisticsComponent implements Component {
    
    private final Map<String, Double> stats;
    
    public StatisticsComponent() {
        this.stats = new HashMap<>();
    }
    
    /**
     * Get a statistic value
     */
    public double getStat(String key) {
        return stats.getOrDefault(key, 0.0);
    }
    
    /**
     * Set a statistic value
     */
    public void setStat(String key, double value) {
        stats.put(key, value);
    }
    
    /**
     * Increment a statistic by 1
     */
    public void incrementStat(String key) {
        addToStat(key, 1.0);
    }
    
    /**
     * Add to a statistic
     */
    public void addToStat(String key, double amount) {
        double current = getStat(key);
        stats.put(key, current + amount);
    }
    
    /**
     * Get all statistics
     */
    public Map<String, Double> getAllStats() {
        return stats;
    }
    
    @Override
    @Nullable
    public Component clone() {
        StatisticsComponent copy = new StatisticsComponent();
        copy.stats.putAll(this.stats);
        return copy;
    }
}
