# NRC_RPG API Dokumentation

## Übersicht

Diese Dokumentation beschreibt alle verwendeten Hytale APIs und wie sie in NRC_RPG implementiert werden.

---

## 1. Entity Component System (ECS)

### Beschreibung
Hytale verwendet das Flecs ECS (C-basiert mit Java API) für hochperformante Entity-Verwaltung.

### Verwendung in NRC_RPG

#### Custom Components

```java
public class SkillComponent implements IComponent {
 private final Map<SkillType, SkillData> skills = new HashMap<>();
 
 public int getLevel(SkillType type) {
 return skills.getOrDefault(type, new SkillData()).level;
 }
 
 public void addXP(SkillType type, double amount) {
 SkillData data = skills.computeIfAbsent(type, k -> new SkillData());
 data.xp += amount;
 checkLevelUp(type, data);
 }
 
 private void checkLevelUp(SkillType type, SkillData data) {
 int requiredXP = XPCalculator.getRequiredXP(data.level);
 if (data.xp >= requiredXP) {
 data.level++;
 data.xp -= requiredXP;
 // Trigger level up event
 }
 }
}
```

#### Component Registration

```java
public class NRCRPGPlugin extends Plugin {
 @Override
 public void onEnable() {
 // Register custom components
 componentRegistry.register(SkillComponent.class);
 componentRegistry.register(CooldownComponent.class);
 }
}
```

#### Zugriff auf Components

```java
public void grantXP(Player player, SkillType type, double amount) {
 SkillComponent skillComp = player.getComponent(SkillComponent.class);
 if (skillComp != null) {
 skillComp.addXP(type, amount);
 }
}
```

### Vorteile
- ✅ Hochperformant durch Cache-freundliche Datenstrukturen
- ✅ Thread-safe durch Flecs-Architektur
- ✅ Einfache Parallelisierung möglich
- ✅ Modulare Erweiterbarkeit

---

## 2. Event System

### Beschreibung
Hytale's Event-System basiert auf Prioritäten und unterstützt sowohl synchrone als auch asynchrone Events.

### Event-Typen

#### IEvent (Synchronous)
Sofortige Ausführung in Prioritätsreihenfolge:

```java
eventBus.subscribe(PlayerDamageEvent.class, EventPriority.NORMAL, (event) -> {
 Player player = event.getPlayer();
 // Handle event synchronously
});
```

#### IAsyncEvent (Asynchronous)
Ausführung mit CompletableFuture:

```java
eventBus.subscribeAsync(PlayerSaveDataEvent.class, (event) -> {
 return CompletableFuture.runAsync(() -> {
 // Save data asynchronously
 dataManager.savePlayerData(event.getPlayer());
 });
});
```

### Verwendete Events in NRC_RPG

#### Combat Events

```java
// EntityDamageByEntityEvent
eventBus.subscribe(EntityDamageByEntityEvent.class, (event) -> {
 if (event.getDamager() instanceof Player player) {
 double damage = event.getDamage();
 skillManager.addXP(player, SkillType.COMBAT, damage * 2.0);
 }
});

// EntityDeathEvent
eventBus.subscribe(EntityDeathEvent.class, (event) -> {
 if (event.getKiller() instanceof Player player) {
 skillManager.addXP(player, SkillType.COMBAT, 20.0);
 }
});
```

#### Mining/Woodcutting Events

```java
// BlockBreakEvent
eventBus.subscribe(BlockBreakEvent.class, (event) -> {
 Player player = event.getPlayer();
 Block block = event.getBlock();
 
 if (isOre(block)) {
 double xp = getOreXP(block);
 skillManager.addXP(player, SkillType.MINING, xp);
 } else if (isWood(block)) {
 skillManager.addXP(player, SkillType.WOODCUTTING, 8.0);
 }
});
```

#### Farming Events

```java
// PlayerHarvestEvent
eventBus.subscribe(PlayerHarvestEvent.class, (event) -> {
 Player player = event.getPlayer();
 skillManager.addXP(player, SkillType.FARMING, 10.0);
});

// PlayerBreedEntityEvent
eventBus.subscribe(PlayerBreedEntityEvent.class, (event) -> {
 skillManager.addXP(event.getPlayer(), SkillType.FARMING, 20.0);
});
```

#### Fishing Events

```java
// PlayerFishEvent
eventBus.subscribe(PlayerFishEvent.class, (event) -> {
 Player player = event.getPlayer();
 FishType fish = event.getCaught();
 
 double xp = calculateFishingXP(fish);
 skillManager.addXP(player, SkillType.FISHING, xp);
});
```

#### Player Data Events

```java
// PlayerJoinEvent
eventBus.subscribe(PlayerJoinEvent.class, (event) -> {
 Player player = event.getPlayer();
 dataManager.loadPlayerData(player);
});

// PlayerQuitEvent
eventBus.subscribeAsync(PlayerQuitEvent.class, (event) -> {
 return CompletableFuture.runAsync(() -> {
 dataManager.savePlayerData(event.getPlayer());
 });
});
```

### Event Priority

```java
public enum EventPriority {
 FIRST, // Höchste Priorität
 EARLY,
 NORMAL, // Standard
 LATE,
 LAST // Niedrigste Priorität
}
```

### Cancellable Events

```java
eventBus.subscribe(BlockBreakEvent.class, EventPriority.EARLY, (event) -> {
 Player player = event.getPlayer();
 SkillComponent skills = player.getComponent(SkillComponent.class);
 
 // Prüfe Mining-Level für spezielle Blöcke
 if (isRareOre(event.getBlock())) {
 if (skills.getLevel(SkillType.MINING) < 50) {
 event.setCancelled(true);
 player.sendMessage("Du benötigst Mining Level 50!");
 }
 }
});
```

---

## 3. Data Storage (Codec System)

### Beschreibung
Hytale verwendet ein Codec-basiertes System für Konfiguration und Datenspeicherung.

### Config-Registrierung

```java
public class NRCRPGPlugin extends Plugin {
 private ConfigManager configManager;
 
 @Override
 public void onEnable() {
 // Register config files
 configManager = new ConfigManager(this);
 configManager.registerConfig("config.json", MainConfig.class);
 configManager.registerConfig("skills.json", SkillsConfig.class);
 }
}
```

### Config-Klassen

```java
public class MainConfig {
 public double xpMultiplier = 1.0;
 public int maxLevel = 100;
 public boolean enableAbilities = true;
 public int autoSaveInterval = 300; // Sekunden
 public boolean debugMode = false;
 
 public Map<String, SkillConfig> skills = new HashMap<>();
}

public class SkillConfig {
 public boolean enabled = true;
 public Map<String, Double> xpRates = new HashMap<>();
 public List<Integer> abilityUnlockLevels = new ArrayList<>();
}
```

### Player Data Storage

```java
public class PlayerDataManager {
 private final Path dataFolder;
 
 public PlayerDataManager(Plugin plugin) {
 this.dataFolder = plugin.getDataFolder().resolve("players");
 Files.createDirectories(dataFolder);
 }
 
 public void savePlayerData(Player player, SkillComponent skills) {
 UUID uuid = player.getUUID();
 Path playerFile = dataFolder.resolve(uuid + ".json");
 
 PlayerDataDTO data = new PlayerDataDTO();
 data.uuid = uuid.toString();
 data.skills = convertSkillsToDTO(skills);
 
 // Serialize to JSON using Hytale's Codec
 JsonCodec.write(playerFile, data);
 }
 
 public SkillComponent loadPlayerData(Player player) {
 UUID uuid = player.getUUID();
 Path playerFile = dataFolder.resolve(uuid + ".json");
 
 if (!Files.exists(playerFile)) {
 return new SkillComponent(); // New player
 }
 
 PlayerDataDTO data = JsonCodec.read(playerFile, PlayerDataDTO.class);
 return convertDTOToSkills(data);
 }
}
```

### Data Transfer Objects

```java
public class PlayerDataDTO {
 public String uuid;
 public Map<String, SkillDataDTO> skills;
 public Map<String, Long> cooldowns;
 public StatisticsDTO statistics;
}

public class SkillDataDTO {
 public int level;
 public double xp;
 public double totalXP;
}

public class StatisticsDTO {
 public int mobsKilled;
 public int blocksMined;
 public int treesChopped;
 public int fishCaught;
 public int cropsharvested;
}
```

### Auto-Save System

```java
public class AutoSaveTask implements Runnable {
 private final SkillManager skillManager;
 private final PlayerDataManager dataManager;
 
 @Override
 public void run() {
 for (Player player : server.getOnlinePlayers()) {
 SkillComponent skills = player.getComponent(SkillComponent.class);
 if (skills != null) {
 dataManager.savePlayerData(player, skills);
 }
 }
 logger.info("Auto-saved all player data");
 }
}

// Register task
scheduler.scheduleRepeating(new AutoSaveTask(), 
 config.autoSaveInterval, 
 TimeUnit.SECONDS);
```

---

## 4. Command API

### Command-Registrierung

```java
public class NRCRPGPlugin extends Plugin {
 @Override
 public void onEnable() {
 // Register commands
 commandManager.register("skills", new SkillsCommand(this));
 commandManager.register("skillsadmin", new AdminCommand(this));
 }
}
```

### Command-Implementierung

```java
public class SkillsCommand implements CommandExecutor {
 private final SkillManager skillManager;
 
 @Override
 public void execute(CommandSender sender, String[] args) {
 if (!(sender instanceof Player player)) {
 sender.sendMessage("Nur Spieler können diesen Command nutzen!");
 return;
 }
 
 if (args.length == 0) {
 // Show all skills
 displaySkillsOverview(player);
 } else if (args.length == 1) {
 // Show specific skill
 SkillType type = parseSkillType(args[0]);
 if (type != null) {
 displaySkillDetails(player, type);
 } else {
 player.sendMessage("Unbekannter Skill: " + args[0]);
 }
 } else if (args.length == 2 && args[0].equalsIgnoreCase("top")) {
 // Show leaderboard
 SkillType type = parseSkillType(args[1]);
 displayLeaderboard(player, type);
 }
 }
 
 private void displaySkillsOverview(Player player) {
 SkillComponent skills = player.getComponent(SkillComponent.class);
 
 player.sendMessage("=== Deine Skills ===");
 for (SkillType type : SkillType.values()) {
 int level = skills.getLevel(type);
 double xp = skills.getXP(type);
 double required = XPCalculator.getRequiredXP(level);
 
 player.sendMessage(String.format("%s: Level %d (%.1f/%.1f XP)",
 type.getDisplayName(), level, xp, required));
 }
 }
}
```

### Admin Commands

```java
public class AdminCommand implements CommandExecutor {
 @Override
 public void execute(CommandSender sender, String[] args) {
 if (!sender.hasPermission("nrc_rpg.admin")) {
 sender.sendMessage("Keine Berechtigung!");
 return;
 }
 
 if (args.length < 2) {
 sender.sendMessage("Verwendung: /skillsadmin <reset|set> <player> [skill] [level]");
 return;
 }
 
 String action = args[0];
 Player target = server.getPlayer(args[1]);
 
 if (target == null) {
 sender.sendMessage("Spieler nicht gefunden!");
 return;
 }
 
 switch (action.toLowerCase()) {
 case "reset" -> resetSkills(sender, target);
 case "set" -> setSkillLevel(sender, target, args);
 default -> sender.sendMessage("Unbekannte Aktion: " + action);
 }
 }
}
```

---

## 5. Permission System

### Permission-Definitionen

```java
public class Permissions {
 public static final String USE = "nrc_rpg.use";
 public static final String ADMIN = "nrc_rpg.admin";
 public static final String BYPASS_COOLDOWN = "nrc_rpg.bypass.cooldown";
 public static final String XP_MULTIPLIER = "nrc_rpg.xp.multiplier.";
 
 // Per-Skill Permissions
 public static final String SKILL_COMBAT = "nrc_rpg.skill.combat";
 public static final String SKILL_MINING = "nrc_rpg.skill.mining";
 // ...
}
```

### Permission-Checks

```java
public void addXP(Player player, SkillType type, double amount) {
 // Check base permission
 if (!player.hasPermission(Permissions.USE)) {
 return;
 }
 
 // Check skill-specific permission
 String skillPerm = "nrc_rpg.skill." + type.name().toLowerCase();
 if (!player.hasPermission(skillPerm)) {
 return;
 }
 
 // Apply XP multiplier from permissions
 double multiplier = getXPMultiplier(player);
 amount *= multiplier;
 
 // Grant XP
 SkillComponent skills = player.getComponent(SkillComponent.class);
 skills.addXP(type, amount);
}

private double getXPMultiplier(Player player) {
 for (int i = 10; i >= 1; i--) {
 if (player.hasPermission(Permissions.XP_MULTIPLIER + i)) {
 return 1.0 + (i * 0.1); // +10% per level
 }
 }
 return 1.0;
}
```

### Permission-Gruppen

```yaml
# Beispiel permissions.yml (für Hytale Permission Plugin)
groups:
 default:
 permissions:
 - nrc_rpg.use
 - nrc_rpg.skill.*
 
 vip:
 inherits: [default]
 permissions:
 - nrc_rpg.xp.multiplier.2 # +20% XP
 
 moderator:
 inherits: [vip]
 permissions:
 - nrc_rpg.admin.view # Spieler-Skills ansehen
 
 admin:
 inherits: [moderator]
 permissions:
 - nrc_rpg.admin # Volle Admin-Rechte
 - nrc_rpg.bypass.cooldown
```

---

## 6. Scheduler API

### Task-Scheduling

```java
public class NRCRPGPlugin extends Plugin {
 private ScheduledTask autoSaveTask;
 private ScheduledTask abilityTickTask;
 
 @Override
 public void onEnable() {
 // Auto-Save Task
 autoSaveTask = scheduler.scheduleRepeating(
 this::saveAllPlayerData,
 config.autoSaveInterval,
 TimeUnit.SECONDS
 );
 
 // Ability Tick (für aktive Fähigkeiten)
 abilityTickTask = scheduler.scheduleRepeating(
 this::tickAbilities,
 1, // Every second
 TimeUnit.SECONDS
 );
 }
 
 @Override
 public void onDisable() {
 // Cancel tasks
 if (autoSaveTask != null) autoSaveTask.cancel();
 if (abilityTickTask != null) abilityTickTask.cancel();
 }
}
```

### Delayed Tasks

```java
public void activateAbility(Player player, Ability ability) {
 // Activate immediately
 ability.activate(player);
 
 // Deactivate after duration
 scheduler.scheduleDelayed(() -> {
 ability.deactivate(player);
 }, ability.getDuration(), TimeUnit.SECONDS);
}
```

---

## 7. Message/Translation API

### Message-Dateien

```json
// messages/de_DE.json
{
 "skills.level_up": "§a§lLEVEL UP! §e{skill} §aist jetzt Level §e{level}§a!",
 "skills.xp_gained": "§7+{xp} {skill} XP",
 "skills.ability_activated": "§6{ability} §aaktiviert!",
 "skills.ability_cooldown": "§cNoch {seconds} Sekunden Cooldown!",
 "skills.required_level": "§cBenötigt Level {level} in {skill}!"
}
```

### Message-Manager

```java
public class MessageManager {
 private final Map<String, String> messages = new HashMap<>();
 private final String language;
 
 public MessageManager(Plugin plugin, String language) {
 this.language = language;
 loadMessages(plugin, language);
 }
 
 public String get(String key, Object... args) {
 String message = messages.getOrDefault(key, key);
 return formatMessage(message, args);
 }
 
 private String formatMessage(String message, Object... args) {
 for (int i = 0; i < args.length; i += 2) {
 String placeholder = "{" + args[i] + "}";
 String value = String.valueOf(args[i + 1]);
 message = message.replace(placeholder, value);
 }
 return ChatColor.translateAlternateColorCodes('§', message);
 }
}

// Usage
messageManager.get("skills.level_up", 
 "skill", "Combat", 
 "level", 25);
```

---

## 8. Sound & Particle API

### Sound-Effekte

```java
public void playLevelUpSound(Player player) {
 // Spiele Sound am Player-Standort
 player.playSound(
 player.getLocation(),
 Sound.ENTITY_PLAYER_LEVELUP,
 SoundCategory.MASTER,
 1.0f, // Volume
 1.0f // Pitch
 );
}
```

### Partikel-Effekte

```java
public void spawnLevelUpParticles(Player player) {
 Location loc = player.getLocation();
 World world = player.getWorld();
 
 // Spawn particle effect
 world.spawnParticle(
 ParticleType.ENCHANTING_TABLE,
 loc.add(0, 1, 0),
 50, // Anzahl
 0.5, 0.5, 0.5, // Spread X, Y, Z
 0.1 // Speed
 );
}
```

---

## Best Practices

### 1. Thread-Safety
```java
// Use ConcurrentHashMap for thread-safe data
private final Map<UUID, SkillComponent> skillCache = new ConcurrentHashMap<>();
```

### 2. Null-Checks
```java
// Always check for null components
SkillComponent skills = player.getComponent(SkillComponent.class);
if (skills == null) {
 logger.warn("Player " + player.getName() + " has no SkillComponent!");
 return;
}
```

### 3. Async I/O
```java
// Never block main thread with I/O
CompletableFuture.runAsync(() -> {
 dataManager.savePlayerData(player);
});
```

### 4. Event-Handler Cleanup
```java
@Override
public void onDisable() {
 // Unregister all event listeners
 eventBus.unregisterAll(this);
}
```

---

## Ressourcen

- [Hytale Server API Reference](https://www.reddit.com/r/HytaleInfo/comments/1qc8f9n/the_hytale_modding_bible_full_server_api_reference/)
- [Hytale Entity Component System](https://hytale.com/news/2024/6/summer-2024-technical-explainer-hytale-s-entity-component-system-oPwpCAMdI)
- [Flecs ECS Documentation](https://www.flecs.dev/flecs/)
- [Hytale Event System](https://hytale-docs.pages.dev/modding/plugins/events/)
