# NRC_RPG - Hytale Skills & Leveling System

![Hytale](https://img.shields.io/badge/Hytale-Early_Access-blue)
![Java](https://img.shields.io/badge/Java-25-orange)
![Status](https://img.shields.io/badge/Status-In_Development-yellow)

## 📋 Übersicht

**NRC_RPG** ist ein MCMMO-inspiriertes Skill- und Leveling-System für Hytale. Es fügt RPG-Mechaniken hinzu, die Spieleraktionen belohnen und langfristige Progression ermöglichen.

### Version 1.0 - Abgespeckte Basis
Diese Version konzentriert sich auf die Kernfunktionalität mit 5 Skills:
- ⚔️ **Combat** (Kampf)
- ⛏️ **Mining** (Bergbau)
- 🪓 **Woodcutting** (Holzfällen)
- 🌾 **Farming** (Landwirtschaft)
- 🎣 **Fishing** (Angeln)

---

## 🎯 Konzept

### Kernmechanik: XP & Leveling

#### XP-Vergabe
- Spieler erhalten XP für relevante Aktionen in jedem Skill
- XP-Menge variiert basierend auf Schwierigkeit/Seltenheit
- Passive XP-Gewinne für bestimmte Aktionen

#### Level-System
- **Max Level pro Skill:** 100
- **XP-Formel:** `XP_needed = base_xp * (1.05 ^ current_level)`
- **Base XP:** 1000
- Exponentielles Wachstum für herausfordernde Endgame-Progression

#### Skill-Boni
Jeder Skill gewährt passive Boni alle 10 Level:
- **Level 10:** +5% Effizienz
- **Level 20:** +10% Effizienz + Spezialfähigkeit 1
- **Level 30:** +15% Effizienz
- **Level 40:** +20% Effizienz + Spezialfähigkeit 2
- **Level 50:** +25% Effizienz + Rare Drop Bonus
- **Level 60-100:** Weitere Steigerungen

---

## ⚔️ Skill-Details

### 1. Combat (Kampf)

**XP-Quellen:**
- Nahkampfschaden gegen Mobs: 10-50 XP (basierend auf Mob-Stärke)
- Tödlicher Schlag: Bonus 20 XP
- Boss-Kämpfe: 200-500 XP

**Passive Boni:**
- **Level 10:** +5% Nahkampfschaden
- **Level 20:** +10% Schaden + "Bleeding" (3 Sek DOT)
- **Level 30:** +15% Schaden
- **Level 40:** +20% Schaden + "Critical Strike" (15% Chance für 2x Schaden)
- **Level 50:** +25% Schaden + Rare Weapon Drop Bonus

**Aktive Fähigkeit (ab Level 20):**
- **Serrated Strikes:** Flächenangriff, 150% Schaden, 10 Sek Cooldown

### 2. Mining (Bergbau)

**XP-Quellen:**
- Stein abbauen: 5 XP
- Kohle: 10 XP
- Kupfer/Eisen: 15 XP
- Thorium/Seltene Erze: 30-50 XP

**Passive Boni:**
- **Level 10:** +5% Mining Speed
- **Level 20:** +10% Speed + "Double Drop" (10% Chance)
- **Level 30:** +15% Speed
- **Level 40:** +20% Speed + "Blast Mining" (2x2 Bereich)
- **Level 50:** +25% Speed + Increased Rare Ore Chance

**Aktive Fähigkeit (ab Level 30):**
- **Super Breaker:** 30 Sek. 3x Mining Speed + garantierte Double Drops, 60 Sek Cooldown

### 3. Woodcutting (Holzfällen)

**XP-Quellen:**
- Holz fällen: 8-15 XP (abhängig von Baumart)
- Große Bäume: Bonus XP basierend auf Höhe

**Passive Boni:**
- **Level 10:** +5% Woodcutting Speed
- **Level 20:** +10% Speed + "Tree Feller" (ganzer Baum fällt)
- **Level 30:** +15% Speed
- **Level 40:** +20% Speed + "Double Drop" (15% Chance)
- **Level 50:** +25% Speed + Rare Sapling Drops

**Aktive Fähigkeit (ab Level 25):**
- **Leaf Blower:** Entfernt automatisch Blätter in 10 Block Radius

### 4. Farming (Landwirtschaft)

**XP-Quellen:**
- Pflanzen ernten: 5-12 XP
- Tiere züchten: 20 XP
- Crops pflanzen: 2 XP

**Passive Boni:**
- **Level 10:** +5% Crop Yield
- **Level 20:** +10% Yield + "Green Terra" (temporärer Growth Boost)
- **Level 30:** +15% Yield
- **Level 40:** +20% Yield + "Auto Replant"
- **Level 50:** +25% Yield + Rare Seed Drops

**Aktive Fähigkeit (ab Level 30):**
- **Harvest Time:** 20 Sek. Auto-Harvest in 5x5 Bereich, 90 Sek Cooldown

### 5. Fishing (Angeln)

**XP-Quellen:**
- Fisch gefangen: 15-30 XP
- Seltene Items: 50-100 XP
- Schätze: 150 XP

**Passive Boni:**
- **Level 10:** +5% Bite Rate
- **Level 20:** +10% Bite Rate + "Treasure Hunter" (höhere Chance auf Schätze)
- **Level 30:** +15% Bite Rate
- **Level 40:** +20% Bite Rate + "Master Angler" (seltenere Fische)
- **Level 50:** +25% Bite Rate + Legendary Catch Chance

**Aktive Fähigkeit (ab Level 20):**
- **Fishing Frenzy:** 30 Sek. instant catch, 120 Sek Cooldown

---

## 🔧 Technische Implementierung

### Verwendete Hytale APIs

#### 1. Entity Component System (ECS)
```java
// Zugriff auf Player Components
val component = player.getComponent(ComponentType)
```

**Benötigte Components:**
- `PlayerComponent` - Basis-Spielerdaten
- Custom: `SkillComponent` - Skill-Daten speichern
- Custom: `XPComponent` - XP-Tracking

#### 2. Event System
```java
// Event Registration
eventBus.subscribe(EventType.class, (event) -> {
 // Handle event
});
```

**Benötigte Events:**
- `EntityDamageByEntityEvent` - Combat XP
- `BlockBreakEvent` - Mining/Woodcutting XP
- `PlayerHarvestEvent` - Farming XP
- `PlayerFishEvent` - Fishing XP
- `PlayerJoinEvent` - Daten laden
- `PlayerQuitEvent` - Daten speichern

#### 3. Data Storage (Codec System)
```java
// Custom Config für Skill-Daten
public class SkillData {
 private Map<String, Integer> levels;
 private Map<String, Double> xp;
}
```

**Speicherung:**
- JSON-basierte Persistenz via Hytale Codec
- Pro-Spieler Dateien in `/universe/players/{uuid}/skills.json`
- Auto-Save alle 5 Minuten + bei Logout

#### 4. Command API
```java
// Commands registrieren
commandManager.register("skills", skillsCommand);
```

**Commands:**
- `/skills` - Zeigt alle Skills und Level
- `/skills <skill>` - Details zu einem Skill
- `/skills top <skill>` - Leaderboard
- `/skills admin reset <player>` - Admin: Reset Skills

#### 5. Permission System
```java
// Permission Checks
if (player.hasPermission("nrc_rpg.use")) {
 // Grant access
}
```

**Permissions:**
- `nrc_rpg.use` - Plugin nutzen
- `nrc_rpg.admin` - Admin-Commands
- `nrc_rpg.bypass.cooldown` - Cooldown umgehen

---

## 📊 Datenstruktur

### Player Skill Data (JSON)
```json
{
 "uuid": "player-uuid-here",
 "skills": {
 "combat": {
 "level": 25,
 "xp": 1250.5,
 "totalXP": 15430.2
 },
 "mining": {
 "level": 30,
 "xp": 890.3,
 "totalXP": 22100.8
 }
 },
 "cooldowns": {
 "combat_ability": 1736891234000
 },
 "statistics": {
 "mobs_killed": 1523,
 "blocks_mined": 8934
 }
}
```

---

## 🏗️ Projektstruktur

```
NRC_RPG/
├── src/
│ └── main/
│ ├── java/
│ │ └── de/nightraid/nrcrpg/
│ │ ├── NRCRPGPlugin.java (Main Class)
│ │ ├── skills/
│ │ │ ├── Skill.java (Abstract Base)
│ │ │ ├── CombatSkill.java
│ │ │ ├── MiningSkill.java
│ │ │ ├── WoodcuttingSkill.java
│ │ │ ├── FarmingSkill.java
│ │ │ └── FishingSkill.java
│ │ ├── managers/
│ │ │ ├── SkillManager.java
│ │ │ ├── XPManager.java
│ │ │ └── DataManager.java
│ │ ├── listeners/
│ │ │ ├── CombatListener.java
│ │ │ ├── MiningListener.java
│ │ │ ├── WoodcuttingListener.java
│ │ │ ├── FarmingListener.java
│ │ │ └── FishingListener.java
│ │ ├── commands/
│ │ │ ├── SkillsCommand.java
│ │ │ └── AdminCommand.java
│ │ ├── data/
│ │ │ ├── SkillData.java
│ │ │ └── PlayerData.java
│ │ └── util/
│ │ ├── XPCalculator.java
│ │ └── MessageUtil.java
│ └── resources/
│ ├── manifest.json
│ ├── config.json
│ └── messages/
│ ├── de_DE.json
│ └── en_US.json
├── docs/
│ ├── API.md
│ └── CONFIGURATION.md
├── pom.xml
└── README.md
```

---

## 🚀 Installation & Setup

### Voraussetzungen
- Hytale Early Access
- Java 25 JDK
- IntelliJ IDEA oder Eclipse
- Hytale Plugin Template

### Development Setup

1. **Repository klonen:**
```bash
git clone https://github.com/NadoHimself/NRC_RPG.git
cd NRC_RPG
```

2. **Projekt in IDE öffnen:**
- IntelliJ: `File > Open` → `pom.xml` auswählen
- Warte auf Maven/Gradle Dependencies

3. **Build:**
```bash
./gradlew build
```

4. **Plugin installieren:**
- JAR aus `build/libs/` kopieren
- Nach `%AppData%/Roaming/Hytale/UserData/Mods/` einfügen
- Hytale starten

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
 "xp_rates": {
 "melee_damage": 1.0,
 "kill_bonus": 20
 }
 }
 }
}
```

---

## 📈 Roadmap

### Version 1.0 (Aktuell) ✅
- [x] 5 Basis-Skills
- [x] XP & Leveling System
- [x] Passive Boni
- [ ] Aktive Fähigkeiten (In Arbeit)
- [ ] Data Persistence

### Version 1.1 (Geplant)
- [ ] Weitere Skills (Excavation, Archery, Repair)
- [ ] Skill-Synergien
- [ ] Party-Bonus-System
- [ ] Globale Leaderboards

### Version 2.0 (Zukunft)
- [ ] Custom Abilities mit Visual Scripting
- [ ] Achievement-System
- [ ] Prestige-System
- [ ] API für andere Plugins

---

## 🤝 Contributing

Beiträge sind willkommen! Bitte:
1. Fork das Repository
2. Erstelle einen Feature-Branch
3. Committe deine Änderungen
4. Push zum Branch
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

- **Hytale Team** - Für das fantastische Modding-System
- **MCMMO** - Original-Inspiration
- **Darkhax & Jared** - Hytale Plugin Template
- **Root Team** - API Documentation

---

## 📚 Ressourcen

- [Hytale Modding Documentation](https://britakee-studios.gitbook.io/hytale-modding-documentation)
- [Hytale Plugin Template](https://github.com/HytaleModding/plugin-template)
- [Hytale Docs](https://hytale-docs.com/)
- [Community Discord](https://discord.gg/hytale)
