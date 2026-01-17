# NRC_RPG - Hytale Skills & Leveling System

![Hytale](https://img.shields.io/badge/Hytale-Ready-blue)
![Java](https://img.shields.io/badge/Java-25-orange)
![Status](https://img.shields.io/badge/Status-Active_Development-green)
![API](https://img.shields.io/badge/Hytale_API-Integrated-success)

## 📋 Übersicht

**NRC_RPG** ist ein MCMMO-inspiriertes Skill- und Leveling-System für Hytale mit vollständiger **Hytale API Integration**. Das Plugin nutzt Hytales modernes **Entity Component System (ECS)**, Event-System und Command-Framework für maximale Performance und Kompatibilität.

### ✨ Version 1.0 Features

#### 5 Skills verfügbar:
- ⚔️ **Combat** - Nahkampf mit Damage Tracking
- ⛏️ **Mining** - Bergbau mit Erz-Erkennung  
- 🪓 **Woodcutting** - Holzfällen mit Tree Tracking
- 🌾 **Farming** - Landwirtschaft mit Ernte & Zucht
- 🎣 **Fishing** - Angeln mit Seltenheits-System

---

## 🎯 Implementierungsstatus

### ✅ Vollständig Implementiert

| Komponente | Status | Details |
|-----------|--------|----------|
| **Plugin System** | ✅ 100% | JavaPlugin mit setup/start/shutdown Lifecycle |
| **ECS Components** | ✅ 100% | SkillComponent, XPComponent, CooldownComponent, StatisticsComponent |
| **Event System** | ✅ 70% | Combat (DamageEvents), Player Connect/Disconnect |
| **Commands** | ✅ 80% | /skills mit AbstractCommand, Permissions |
| **Data Persistence** | 🟡 50% | Struktur vorhanden, I/O ausstehend |
| **Manifest** | ✅ 100% | Korrekte Hytale manifest.json |

### 🔧 In Arbeit

- Block Events (Mining, Woodcutting)
- Farming Events (Harvest, Breed)
- Fishing Events
- Admin Commands (/skillsadmin)
- Data I/O Implementation
- XP Processing System

---

## 🏗️ Technische Architektur

### Hytale API Integration

#### **Entity Component System (ECS)**
```java
// Components werden mit EntityStoreRegistry registriert
getEntityStoreRegistry().registerComponent(SkillComponent.class, SkillComponent::new);

// Zugriff via Store und Ref
SkillComponent skills = store.getComponent(playerRef, SkillComponent.class);
```

#### **Event System**
```java
// Synchrone Events
getEventRegistry().register(DamageEvents.Inspect.class, this::onDamage);

// Asynchrone Events
getEventRegistry().registerAsync(
    PlayerDisconnectEvent.class,
    future -> future.thenApply(event -> { /* ... */ })
);
```

#### **Command System**
```java
// Commands mit AbstractCommand
public class SkillsCommand extends AbstractCommand {
    @Override
    protected CompletableFuture<Void> execute(CommandContext context) {
        // Command logic
    }
}
```

### Plugin Lifecycle

```
1. setup()    → ECS Components, Events, Commands registrieren
2. start()    → Tasks starten, State initialisieren  
3. shutdown() → Daten speichern, Resources aufräumen
```

---

## ⚔️ Skill-Details

### Combat (Kampf)

**XP-Quellen:**
- Schaden gegen Mobs: `damage * 2.0 XP`
- Tödlicher Schlag: `+20 XP Bonus`

**Passive Boni:**
- **Level 10:** +5% Nahkampfschaden
- **Level 20:** +10% + "Bleeding" (3 Sek DOT)
- **Level 40:** +20% + "Critical Strike" (15% Chance für 2x Schaden)
- **Level 50:** +25% + Rare Weapon Drops

**Aktive Fähigkeit (Level 20):**
- **Serrated Strikes:** Flächenangriff, 150% Schaden, 10s Cooldown

### Mining (Bergbau)

**XP-Quellen:**
- Stein: 5 XP
- Kohle: 10 XP  
- Eisen/Kupfer: 15 XP
- Seltene Erze: 30-50 XP

**Passive Boni:**
- **Level 20:** +10% Speed + "Double Drop" (10%)
- **Level 40:** +20% Speed + "Blast Mining" (2x2 Bereich)
- **Level 50:** +25% Speed + Erhöhte Rare Ore Chance

**Aktive Fähigkeit (Level 30):**
- **Super Breaker:** 30s 3x Speed + garantierte Double Drops, 60s CD

### Woodcutting (Holzfällen)

**XP-Quellen:**
- Holz fällen: 8-15 XP (abhängig von Baumart)
- Große Bäume: Bonus-XP basierend auf Höhe

**Passive Boni:**
- **Level 20:** +10% Speed + "Tree Feller" (ganzer Baum fällt)
- **Level 40:** +20% Speed + "Double Drop" (15%)
- **Level 50:** +25% Speed + Rare Sapling Drops

**Aktive Fähigkeit (Level 25):**
- **Leaf Blower:** Auto-Blätter entfernen in 10 Block Radius

### Farming (Landwirtschaft)

**XP-Quellen:**
- Pflanzen ernten: 5-12 XP
- Tiere züchten: 20 XP
- Crops pflanzen: 2 XP

**Passive Boni:**
- **Level 20:** +10% Yield + "Green Terra" (temp. Growth Boost)
- **Level 40:** +20% Yield + "Auto Replant"
- **Level 50:** +25% Yield + Rare Seed Drops

**Aktive Fähigkeit (Level 30):**
- **Harvest Time:** 20s Auto-Harvest 5x5, 90s CD

### Fishing (Angeln)

**XP-Quellen:**
- Fisch gefangen: 15-30 XP
- Seltene Items: 50-100 XP  
- Schätze: 150 XP

**Passive Boni:**
- **Level 20:** +10% Bite Rate + "Treasure Hunter"
- **Level 40:** +20% Bite Rate + "Master Angler" (seltenere Fische)
- **Level 50:** +25% Bite Rate + Legendary Catch Chance

**Aktive Fähigkeit (Level 20):**
- **Fishing Frenzy:** 30s Instant Catch, 120s CD

---

## 📊 Progression System

### Level & XP System

- **Max Level:** 100 pro Skill
- **XP Formula:** `XP_needed = 1000 * (1.05 ^ current_level)`
- **Exponentielles Wachstum** für Endgame-Herausforderung

### Boni alle 10 Level

- **Level 10, 30, 60, 70, 80, 90, 100:** +5% Effizienz pro Stufe
- **Level 20, 40:** Spezialfähigkeiten freischalten  
- **Level 50:** Rare Drop Bonus

---

## 🔧 Installation & Setup

### Voraussetzungen

- Hytale Early Access
- Java 25 JDK
- Maven oder Gradle
- HytaleServer.jar im `lib/` Ordner

### Build

```bash
# Mit Maven
mvn clean package

# Output: target/NRC_RPG-1.0.0.jar
```

### Installation

1. **Plugin JAR bauen** (siehe oben)
2. **JAR nach `mods/` kopieren:**
   ```bash
   cp target/NRC_RPG-1.0.0.jar /path/to/hytale/mods/
   ```
3. **Server starten**
4. **Plugin wird automatisch geladen**

---

## ⚙️ Konfiguration

### config.json

```json
{
  "xp_multiplier": 1.0,
  "max_level": 100,
  "enable_abilities": true,
  "auto_save_interval": 300,
  "debug_mode": false,
  "skills": {
    "combat": {
      "enabled": true,
      "xp_per_damage": 2.0,
      "kill_bonus": 20.0
    }
  }
}
```

---

## 🎮 Commands

| Command | Permission | Description |
|---------|-----------|-------------|
| `/skills` | `de.nightraid.nrc_rpg.command.skills` | Zeigt alle Skills und Level |
| `/skills <skill>` | ↑ | Details zu einem Skill |
| `/skillsadmin reset <player>` | `de.nightraid.nrc_rpg.command.skillsadmin` | Reset Skills |
| `/skillsadmin set <player> <skill> <level>` | ↑ | Set Skill Level |

---

## 📁 Projektstruktur

```
NRC_RPG/
├── src/main/java/de/nightraid/nrcrpg/
│   ├── NRCRPGPlugin.java           # Main Plugin (JavaPlugin)
│   ├── commands/
│   │   ├── SkillsCommand.java      # /skills Command
│   │   └── AdminCommand.java       # /skillsadmin Command
│   ├── data/components/
│   │   ├── SkillComponent.java     # ECS: Skill Levels & XP
│   │   ├── XPComponent.java        # ECS: Pending XP Buffer
│   │   ├── CooldownComponent.java  # ECS: Ability Cooldowns
│   │   └── StatisticsComponent.java # ECS: Player Stats
│   ├── listeners/
│   │   ├── CombatListener.java     # DamageEvents
│   │   ├── MiningListener.java     # Block Events
│   │   ├── PlayerConnectionListener.java # Join/Quit
│   │   └── ...
│   ├── managers/
│   │   ├── SkillManager.java
│   │   ├── XPManager.java
│   │   ├── DataManager.java
│   │   └── ...
│   └── skills/
│       ├── SkillType.java          # Enum: All Skills
│       ├── SkillData.java          # Data Class
│       └── ...
└── src/main/resources/
    ├── manifest.json               # Hytale Plugin Manifest
    ├── config.json
    └── messages/
        ├── de_DE.json
        └── en_US.json
```

---

## 🚀 Roadmap

### Version 1.0 (Current)
- [x] Hytale API Integration
- [x] ECS Components
- [x] Combat System
- [x] Player Persistence Events
- [x] Basic Commands
- [ ] Mining/Woodcutting Events
- [ ] Farming/Fishing Events
- [ ] Data I/O Implementation

### Version 1.1 (Planned)
- [ ] Aktive Fähigkeiten
- [ ] Skill-Synergien
- [ ] Party-Bonus-System
- [ ] Leaderboards

### Version 2.0 (Future)
- [ ] Weitere Skills (Excavation, Archery, Repair)
- [ ] Achievement-System
- [ ] Prestige-System
- [ ] API für andere Plugins

---

## 🤝 Contributing

Beiträge sind willkommen! Bitte:

1. Fork das Repository
2. Erstelle einen Feature-Branch (`git checkout -b feature/AmazingFeature`)
3. Committe deine Änderungen (`git commit -m 'Add AmazingFeature'`)
4. Push zum Branch (`git push origin feature/AmazingFeature`)
5. Öffne einen Pull Request

---

## 📝 Lizenz

MIT License - Siehe [LICENSE](LICENSE) für Details

---

## 👤 Autor

**Kielian (NadoHimself)**

- GitHub: [@NadoHimself](https://github.com/NadoHimself)
- Company: Age of Flair
- Website: [ageofflair.de](https://ageofflair.de)

---

## 🙏 Credits

- **Hypixel Studios** - Hytale und Modding-System
- **MCMMO** - Original-Inspiration
- **Hytale Modding Community** - Dokumentation & Support

---

## 📚 Ressourcen

- [Hytale Official](https://hytale.com/)
- [Hytale Server Docs (Unofficial)](https://hytale-docs.pages.dev/)
- [Plugin Development Guide](https://support.curseforge.com/en/support/solutions/articles/9000273186)
- [Community Discord](https://discord.gg/hytale)

---

**Plugin Identifier:** `de.nightraid:NRC_RPG`  
**Version:** 1.0.0  
**Server Version:** >=0.0.1
