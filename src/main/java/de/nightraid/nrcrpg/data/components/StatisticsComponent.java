package de.nightraid.nrcrpg.data.components;

import com.hypixel.hytale.server.core.ecs.Component;
import com.hypixel.hytale.server.core.ecs.store.entity.EntityStore;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ECS Component for tracking player statistics
 * Thread-safe counters for various actions
 */
public class StatisticsComponent implements Component<EntityStore> {
    
    private final AtomicInteger mobsKilled;
    private final AtomicInteger blocksMined;
    private final AtomicInteger treesChopped;
    private final AtomicInteger cropsHarvested;
    private final AtomicInteger fishCaught;
    private final AtomicInteger damageDealt;
    private final AtomicInteger damageTaken;
    
    public StatisticsComponent() {
        this.mobsKilled = new AtomicInteger(0);
        this.blocksMined = new AtomicInteger(0);
        this.treesChopped = new AtomicInteger(0);
        this.cropsHarvested = new AtomicInteger(0);
        this.fishCaught = new AtomicInteger(0);
        this.damageDealt = new AtomicInteger(0);
        this.damageTaken = new AtomicInteger(0);
    }
    
    private StatisticsComponent(int mobsKilled, int blocksMined, int treesChopped,
                               int cropsHarvested, int fishCaught, int damageDealt,
                               int damageTaken) {
        this.mobsKilled = new AtomicInteger(mobsKilled);
        this.blocksMined = new AtomicInteger(blocksMined);
        this.treesChopped = new AtomicInteger(treesChopped);
        this.cropsHarvested = new AtomicInteger(cropsHarvested);
        this.fishCaught = new AtomicInteger(fishCaught);
        this.damageDealt = new AtomicInteger(damageDealt);
        this.damageTaken = new AtomicInteger(damageTaken);
    }
    
    @Override
    @Nonnull
    public Component<EntityStore> clone() {
        return new StatisticsComponent(
            mobsKilled.get(),
            blocksMined.get(),
            treesChopped.get(),
            cropsHarvested.get(),
            fishCaught.get(),
            damageDealt.get(),
            damageTaken.get()
        );
    }
    
    // Increment methods
    public void incrementMobsKilled() { mobsKilled.incrementAndGet(); }
    public void incrementBlocksMined() { blocksMined.incrementAndGet(); }
    public void incrementTreesChopped() { treesChopped.incrementAndGet(); }
    public void incrementCropsHarvested() { cropsHarvested.incrementAndGet(); }
    public void incrementFishCaught() { fishCaught.incrementAndGet(); }
    public void addDamageDealt(int amount) { damageDealt.addAndGet(amount); }
    public void addDamageTaken(int amount) { damageTaken.addAndGet(amount); }
    
    // Getter methods
    public int getMobsKilled() { return mobsKilled.get(); }
    public int getBlocksMined() { return blocksMined.get(); }
    public int getTreesChopped() { return treesChopped.get(); }
    public int getCropsHarvested() { return cropsHarvested.get(); }
    public int getFishCaught() { return fishCaught.get(); }
    public int getDamageDealt() { return damageDealt.get(); }
    public int getDamageTaken() { return damageTaken.get(); }
    
    // Reset
    public void resetAll() {
        mobsKilled.set(0);
        blocksMined.set(0);
        treesChopped.set(0);
        cropsHarvested.set(0);
        fishCaught.set(0);
        damageDealt.set(0);
        damageTaken.set(0);
    }
}
