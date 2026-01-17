# NRC_RPG - Hytale Skills & Leveling System

![Hytale](https://img.shields.io/badge/Hytale-Early_Access-blue)
![Java](https://img.shields.io/badge/Java-25-orange)
![Status](https://img.shields.io/badge/Status-Ready_for_Testing-green)
![Build](https://github.com/NadoHimself/NRC_RPG/actions/workflows/maven.yml/badge.svg)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## 📚 Übersicht

**NRC_RPG** ist ein vollständig implementiertes MCMMO-inspiriertes Skill- und Leveling-System für Hytale. Es fügt RPG-Mechaniken hinzu, die Spieleraktionen belohnen und langfristige Progression ermöglichen.

### ✅ Version 1.0 - Vollständig Implementiert!

Diese Version enthält **5 vollständig funktionsfähige Skills**:
- ⚔️ **Combat** (Kampf) - Schaden, Bleeding, Critical Strike
- ⛏️ **Mining** (Bergbau) - Erze, Double Drop, Blast Mining
- 🪓 **Woodcutting** (Holzfällen) - Holz, Tree Feller, Saplings
- 🌾 **Farming** (Landwirtschaft) - Ernten, Auto-Replant, Green Terra
- 🎣 **Fishing** (Angeln) - Fische, Treasure Hunter, Master Angler

---

## 🚀 Schnellstart

### Voraussetzungen

- **Java 25 JDK** ([Download](https://adoptium.net/))
- **Maven 3.9+** ([Download](https://maven.apache.org/download.cgi))
- **Git** ([Download](https://git-scm.com/))
- **Hytale Early Access** (sobald verfügbar)

### Installation

```bash
# 1. Repository klonen
git clone https://github.com/NadoHimself/NRC_RPG.git
cd NRC_RPG

# 2. Mit Maven bauen
mvn clean package

# 3. JAR-Datei finden
# Die fertige JAR befindet sich in: target/NRC_RPG-1.0.0-SNAPSHOT.jar
```

### GitHub Actions (Automatisches Building)

Jeder Push zum `main` Branch triggert automatisch einen Build:
1. Code wird kompiliert
2. Tests werden ausgeführt
3. JAR wird als Artifact hochgeladen
4. Download unter "Actions" Tab verfügbar

---

## 💾 Installation auf Hytale Server

### Manuelle Installation

```bash
# 1. JAR in Hytale Plugins-Ordner kopieren
cp target/NRC_RPG-1.0.0-SNAPSHOT.jar /path/to/hytale/plugins/

# 2. Server starten
# Das Plugin wird automatisch geladen und erstellt die Konfiguration

# 3. Konfiguration anpassen (optional)
nano plugins/NRC_RPG/config.json
```

### Docker Deployment (Optional)

```dockerfile
FROM hytale-server:latest

# Copy plugin
COPY target/NRC_RPG-1.0.0-SNAPSHOT.jar /plugins/

# Copy custom config (optional)
COPY config.json /plugins/NRC_RPG/

EXPOSE 25565
CMD ["java", "-jar", "hytale-server.jar"]
```

---

## ⚙️ Konfiguration

### config.json

```json
{
  "xpMultiplier": 1.0,        // Globaler XP-Multiplikator
  "maxLevel": 100,            // Maximales Level pro Skill
  "enableAbilities": true,    // Aktive Fähigkeiten an/aus
  "autoSaveInterval": 300,    // Auto-Save Intervall (Sekunden)
  "debugMode": false          // Debug-Logging
}
```

### Skills konfigurieren

Jeder Skill kann individuell angepasst werden:

```json
"combat": {
  "enabled": true,
  "xpRates": {
    "damage": 2.0,      // XP pro Schadenspunkt
    "kill": 20.0,       // Bonus XP für Tötung
    "bossKill": 500.0   // Bonus XP für Boss-Kill
  },
  "abilities": {
    "bleeding": {
      "unlockLevel": 20,
      "chance": 0.15,   // 15% Chance
      "duration": 3     // 3 Sekunden
    }
  }
}
```

---

## 🎮 Commands

### Spieler Commands

```bash
# Skills übersicht anzeigen
/skills

# Spezifischen Skill anzeigen
/skills combat
/skills mining
/skills woodcutting
/skills farming
/skills fishing

# Leaderboard anzeigen
/skills top combat
/skills top mining
```

### Admin Commands

```bash
# Skills zurücksetzen
/skillsadmin reset <player>

# Level setzen
/skillsadmin set <player> <skill> <level>
# Beispiel: /skillsadmin set Kielian combat 50

# XP hinzufügen
/skillsadmin add <player> <skill> <xp>
# Beispiel: /skillsadmin add Kielian mining 1000

# Spieler Skills ansehen
/skillsadmin view <player>
```

---

## 🛡️ Permissions

### Basis Permissions

```yaml
nrc_rpg.use              # Plugin nutzen (Standard: true)
nrc_rpg.admin            # Admin Commands (Standard: op)
nrc_rpg.bypass.cooldown  # Cooldowns umgehen (Standard: op)
```

### Skill Permissions

```yaml
nrc_rpg.skill.combat      # Combat Skill nutzen
nrc_rpg.skill.mining      # Mining Skill nutzen
nrc_rpg.skill.woodcutting # Woodcutting Skill nutzen
nrc_rpg.skill.farming     # Farming Skill nutzen
nrc_rpg.skill.fishing     # Fishing Skill nutzen
```

### XP Multiplier Permissions

```yaml
nrc_rpg.xp.multiplier.1  # +10% XP (VIP)
nrc_rpg.xp.multiplier.2  # +20% XP (VIP+)
nrc_rpg.xp.multiplier.5  # +50% XP (MVP)
```

---

## 📊 Skill Details

### ⚔️ Combat (Kampf)

**XP-Quellen:**
- Nahkampfschaden: 2 XP pro Schadenspunkt
- Tödlicher Schlag: +20 XP Bonus
- Boss-Kämpfe: 200-500 XP

**Passive Boni:**
- Level 10: +5% Nahkampfschaden
- Level 20: +10% Schaden + **Bleeding** (15% Chance, 3s DOT)
- Level 30: +15% Schaden
- Level 40: +20% Schaden + **Critical Strike** (15% Chance, 2x Schaden)
- Level 50: +25% Schaden + Rare Weapon Drop Bonus

---

### ⛏️ Mining (Bergbau)

**XP-Quellen:**
- Stein: 5 XP
- Kohle: 10 XP
- Eisen: 15 XP
- Gold: 20 XP
- Diamant: 50 XP

**Passive Boni:**
- Level 10: +5% Mining Speed
- Level 20: +10% Speed + **Double Drop** (10% Chance)
- Level 30: +15% Speed
- Level 40: +20% Speed + **Blast Mining** (2x2 Bereich)
- Level 50: +25% Speed + Erhöhte Rare Ore Chance

---

### 🪓 Woodcutting (Holzfällen)

**XP-Quellen:**
- Eiche: 8 XP
- Birke: 10 XP
- Fichte: 12 XP
- Dunkle Eiche: 15 XP

**Passive Boni:**
- Level 10: +5% Woodcutting Speed
- Level 20: +10% Speed + **Tree Feller** (ganzer Baum fällt)
- Level 30: +15% Speed
- Level 40: +20% Speed + **Double Drop** (15% Chance)
- Level 50: +25% Speed + Rare Sapling Drops

---

### 🌾 Farming (Landwirtschaft)

**XP-Quellen:**
- Pflanzen ernten: 5-12 XP
- Tiere züchten: 20 XP
- Pflanzen: 2 XP

**Passive Boni:**
- Level 10: +5% Crop Yield
- Level 20: +10% Yield + **Green Terra** (Growth Boost)
- Level 30: +15% Yield
- Level 40: +20% Yield + **Auto Replant**
- Level 50: +25% Yield + Rare Seed Drops

---

### 🎣 Fishing (Angeln)

**XP-Quellen:**
- Normaler Fisch: 15 XP
- Seltener Fisch: 50 XP
- Schatz: 150 XP

**Passive Boni:**
- Level 10: +5% Bite Rate
- Level 20: +10% Bite Rate + **Treasure Hunter** (5% Schatz-Chance)
- Level 30: +15% Bite Rate
- Level 40: +20% Bite Rate + **Master Angler** (10% seltene Fische)
- Level 50: +25% Bite Rate + Legendary Catch Chance

---

## 📝 XP & Leveling System

### XP-Formel

```java
// XP für nächstes Level
XP_needed = 1000 * (1.05 ^ current_level)

// Beispiele:
Level 1 → 2:   1,050 XP
Level 10 → 11: 1,629 XP
Level 50 → 51: 11,467 XP
Level 99 → 100: 131,501 XP
```

### Level-Progression

| Level | XP benötigt | Total XP | Belohnung |
|-------|-------------|----------|----------|
| 1 → 10 | ~13,000 | 13,000 | +5% Bonus |
| 10 → 20 | ~21,000 | 34,000 | +10% Bonus + Fähigkeit 1 |
| 20 → 30 | ~34,000 | 68,000 | +15% Bonus |
| 30 → 40 | ~55,000 | 123,000 | +20% Bonus + Fähigkeit 2 |
| 40 → 50 | ~89,000 | 212,000 | +25% Bonus + Rare Drops |
| 50 → 100 | ~3.5M | ~3.7M | Weitere Boni |

---

## 💾 Datenstruktur

### Player Data (JSON)

```json
{
  "uuid": "player-uuid-here",
  "skills": {
    "COMBAT": {
      "level": 25,
      "xp": 1250.5,
      "totalXP": 15430.2
    },
    "MINING": {
      "level": 30,
      "xp": 890.3,
      "totalXP": 22100.8
    }
  },
  "cooldowns": {
    "COMBAT": 1736891234000
  },
  "statistics": {
    "mobsKilled": 1523,
    "blocksMined": 8934,
    "treesChopped": 456,
    "fishCaught": 234,
    "cropsHarvested": 1234
  }
}
```

### Speicherort

```
plugins/NRC_RPG/
├── config.json
└── players/
    ├── uuid-1.json
    ├── uuid-2.json
    └── ...
```

---

## 🛠️ Development

### Projektstruktur

```
NRC_RPG/
├── src/main/
│   ├── java/de/nightraid/nrcrpg/
│   │   ├── NRCRPGPlugin.java        # Main Plugin Class
│   │   ├── commands/                # Command Handlers
│   │   ├── listeners/               # Event Listeners
│   │   ├── managers/                # Manager Classes
│   │   ├── skills/                  # Skill Types & Data
│   │   ├── data/                    # Data Structures
│   │   ├── util/                    # Utilities
│   │   └── tasks/                   # Scheduled Tasks
│   └── resources/
│       ├── manifest.json            # Plugin Manifest
│       ├── config.json              # Default Config
│       └── messages/                # Localization
├── docs/                        # Documentation
├── .github/workflows/           # CI/CD
├── pom.xml                      # Maven Config
└── README.md                    # This file
```

### Bauen & Testen

```bash
# Kompilieren
mvn compile

# Tests ausführen
mvn test

# Package erstellen
mvn package

# Alle Phasen + Tests
mvn clean verify

# Installation im lokalen Maven Repository
mvn install
```

### Code-Qualität

```bash
# Code-Style prüfen (zukünftig)
mvn checkstyle:check

# Dependency-Analyse
mvn dependency:analyze

# Sicherheits-Scan
mvn dependency:check
```

---

## 🐛 Troubleshooting

### Build-Fehler

**Problem:** `Could not find or load main class`
```bash
# Lösung: Maven Repository cleanen
mvn clean
rm -rf ~/.m2/repository/de/nightraid/nrc-rpg
mvn package
```

**Problem:** `Java version mismatch`
```bash
# Lösung: Java 25 verwenden
java -version
export JAVA_HOME=/path/to/java-25
mvn clean package
```

### Plugin-Fehler

**Problem:** Plugin lädt nicht
- Prüfe `manifest.json` auf Syntax-Fehler
- Stelle sicher, dass Hytale API Version kompatibel ist
- Prüfe Server-Logs für Fehler

**Problem:** Daten werden nicht gespeichert
- Prüfe Dateiberechtigungen im `plugins/NRC_RPG/` Ordner
- Stelle sicher, dass `autoSaveInterval` > 0 ist
- Prüfe Logs für IO-Fehler

---

## 📈 Roadmap

### Version 1.1 (Nächste Release)
- [ ] Weitere Skills (Excavation, Archery, Repair)
- [ ] Skill-Synergien
- [ ] Party-Bonus-System
- [ ] Web-basiertes Leaderboard
- [ ] Achievements

### Version 2.0 (Zukunft)
- [ ] Custom Abilities mit Visual Scripting
- [ ] Prestige-System
- [ ] Skill-Trees
- [ ] API für andere Plugins
- [ ] MySQL Support

---

## 🤝 Contributing

Beiträge sind willkommen! Bitte:

1. **Fork** das Repository
2. Erstelle einen **Feature-Branch** (`git checkout -b feature/amazing-feature`)
3. **Committe** deine Änderungen (`git commit -m 'Add amazing feature'`)
4. **Push** zum Branch (`git push origin feature/amazing-feature`)
5. Öffne einen **Pull Request**

### Code-Style

- Java Code Style: Google Java Style Guide
- 4 Spaces Einrückung
- Aussagekräftige Variablennamen
- JavaDoc für öffentliche Methoden

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
- **Community** - Feedback und Testing

---

## 📚 Ressourcen

- [Hytale Modding Documentation](https://britakee-studios.gitbook.io/hytale-modding-documentation)
- [Hytale Community Discord](https://discord.gg/hytale)
- [Project Issues](https://github.com/NadoHimself/NRC_RPG/issues)
- [Project Wiki](https://github.com/NadoHimself/NRC_RPG/wiki)

---

## ⭐ Unterstützung

Wenn dir dieses Plugin gefällt:
- Gib dem Repository einen ⭐ **Star**
- Teile es mit anderen Hytale-Entwicklern
- Melde Bugs und wünsche Features via [Issues](https://github.com/NadoHimself/NRC_RPG/issues)

---

<div align="center">

**Made with ❤️ by [Age of Flair](https://ageofflair.de)**

*Ready for Hytale Early Access Testing!*

</div>