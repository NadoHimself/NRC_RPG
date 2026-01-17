package de.nightraid.nrcrpg.data.components;

import com.hypixel.hytale.server.core.ecs.Component;
import com.hypixel.hytale.server.core.ecs.store.entity.EntityStore;
import de.nightraid.nrcrpg.skills.SkillType;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ECS Component for buffering pending XP gains
 * Used for async XP processing to avoid blocking gameplay
 */
public class XPComponent implements Component<EntityStore> {
    
    private final Map<SkillType, Double> pendingXP;
    
    public XPComponent() {
        this.pendingXP = new ConcurrentHashMap<>();
    }
    
    private XPComponent(Map<SkillType, Double> pendingXP) {
        this.pendingXP = new ConcurrentHashMap<>(pendingXP);
    }
    
    @Override
    @Nonnull
    public Component<EntityStore> clone() {
        return new XPComponent(this.pendingXP);
    }
    
    public void addPending(SkillType type, double amount) {
        pendingXP.merge(type, amount, Double::sum);
    }
    
    public double getPending(SkillType type) {
        return pendingXP.getOrDefault(type, 0.0);
    }
    
    public double getAndClearPending(SkillType type) {
        return pendingXP.remove(type) != null ? pendingXP.get(type) : 0.0;
    }
    
    public boolean hasPendingXP() {
        return !pendingXP.isEmpty();
    }
    
    public Map<SkillType, Double> getAllPending() {
        return new ConcurrentHashMap<>(pendingXP);
    }
    
    public void clearAll() {
        pendingXP.clear();
    }
}
