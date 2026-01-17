# NRC_RPG Konfiguration

## Übersicht

Diese Datei erklärt alle Konfigurationsoptionen des NRC_RPG Plugins.

---

## config.json

### Allgemeine Einstellungen

#### xp_multiplier
- **Typ:** `double`
- **Standard:** `1.0`
- **Beschreibung:** Globaler XP-Multiplikator für alle Skills
- **Beispiele:**
 - `1.0` = Normale XP-Rate
 - `2.0` = Doppelte XP
 - `0.5` = Halbe XP

#### max_level
- **Typ:** `integer`
- **Standard:** `100`
- **Beschreibung:** Maximales Level für alle Skills
- **Hinweis:** Änderungen erfordern Neuberechnung der XP-Kurve

#### enable_abilities
- **Typ:** `boolean`
- **Standard:** `true`
- **Beschreibung:** Aktiviert/Deaktiviert alle Fähigkeiten

#### auto_save_interval
- **Typ:** `integer`
- **Standard:** `300`
- **Beschreibung:** Intervall in Sekunden zwischen Auto-Saves
- **Empfehlung:** Minimum 60, Maximum 600

#### debug_mode
- **Typ:** `boolean`
- **Standard:** `false`
- **Beschreibung:** Aktiviert Debug-Logging
- **Warnung:** Kann zu hoher Log-Datei-Größe führen

#### level_up_sound
- **Typ:** `boolean`
- **Standard:** `true`
- **Beschreibung:** Spielt Sound bei Level-Up ab

#### level_up_particles
- **Typ:** `boolean`
- **Standard:** `true`
- **Beschreibung:** Zeigt Partikel bei Level-Up

#### broadcast_level_milestones
- **Typ:** `integer[]`
- **Standard:** `[25, 50, 75, 100]`
- **Beschreibung:** Bei welchen Levels wird eine Server-weite Nachricht gesendet

---

## Skill-spezifische Konfiguration

### Combat

```json
"combat": {
 "enabled": true,
 "xp_rates": {
 "melee_damage": 2.0,
 "kill_bonus": 20.0,
 "boss_kill": 500.0
 },
 "abilities": {
 "serrated_strikes": {
 "unlock_level": 20,
 "cooldown": 10,
 "damage_multiplier": 1.5,
 "radius": 3.0
 }
 }
}
```

#### XP Rates
- **melee_damage:** XP pro Schadenspunkt im Nahkampf
- **kill_bonus:** Bonus-XP für tödlichen Schlag
- **boss_kill:** XP für Boss-Kills

#### Abilities
- **serrated_strikes:**
 - `unlock_level`: Level zum Freischalten
 - `cooldown`: Cooldown in Sekunden
 - `damage_multiplier`: Schadensmultiplikator
 - `radius`: Effektradius in Blöcken

---

### Mining

```json
"mining": {
 "enabled": true,
 "xp_rates": {
 "stone": 5.0,
 "coal": 10.0,
 "copper": 15.0,
 "iron": 15.0,
 "thorium": 30.0,
 "rare_ore": 50.0
 },
 "abilities": {
 "super_breaker": {
 "unlock_level": 30,
 "cooldown": 60,
 "duration": 30,
 "speed_multiplier": 3.0
 }
 }
}
```

#### XP Rates
- Verschiedene XP-Werte für unterschiedliche Erz-Typen
- **Hinweis:** Werte werden pro abgebautem Block vergeben

#### Abilities
- **super_breaker:**
 - `duration`: Dauer der Fähigkeit in Sekunden
 - `speed_multiplier`: Geschwindigkeitsmultiplikator

---

### Woodcutting

```json
"woodcutting": {
 "enabled": true,
 "xp_rates": {
 "oak": 8.0,
 "birch": 10.0,
 "pine": 12.0,
 "rare_wood": 15.0
 },
 "abilities": {
 "tree_feller": {
 "unlock_level": 20,
 "max_blocks": 100
 },
 "leaf_blower": {
 "unlock_level": 25,
 "cooldown": 30,
 "radius": 10.0
 }
 }
}
```

#### Abilities
- **tree_feller:**
 - `max_blocks`: Maximale Anzahl Blöcke die gefällt werden
 - **Hinweis:** Verhindert Performance-Probleme bei riesigen Bäumen

- **leaf_blower:**
 - `radius`: Radius in dem Blätter entfernt werden

---

### Farming

```json
"farming": {
 "enabled": true,
 "xp_rates": {
 "harvest": 10.0,
 "plant": 2.0,
 "breed_animal": 20.0
 },
 "abilities": {
 "green_terra": {
 "unlock_level": 20,
 "cooldown": 90,
 "duration": 20
 },
 "harvest_time": {
 "unlock_level": 30,
 "cooldown": 90,
 "duration": 20,
 "radius": 5
 }
 }
}
```

#### XP Rates
- **harvest:** XP pro geernteter Pflanze
- **plant:** XP pro gepflanztem Samen
- **breed_animal:** XP pro gezüchtetem Tier

---

### Fishing

```json
"fishing": {
 "enabled": true,
 "xp_rates": {
 "common_fish": 15.0,
 "uncommon_fish": 25.0,
 "rare_fish": 40.0,
 "treasure": 100.0,
 "legendary": 150.0
 },
 "abilities": {
 "fishing_frenzy": {
 "unlock_level": 20,
 "cooldown": 120,
 "duration": 30
 }
 }
}
```

#### XP Rates
Basierend auf Seltenheit des Fangs:
- **common_fish:** Normale Fische
- **uncommon_fish:** Ungewöhnliche Fische
- **rare_fish:** Seltene Fische
- **treasure:** Schätze (Spezial-Items)
- **legendary:** Legendäre Fänge

---

## Erweiterte Konfiguration

### XP-Berechnung anpassen

Die XP-Formel lautet:
```
XP_needed = base_xp * (1.05 ^ current_level)
```

Um die Kurve anzupassen:
1. `base_xp` ändern (härter im Code)
2. Exponent anpassen für steilere/flachere Kurve

### Custom XP-Multiplikatoren per Permission

Permissions wie `nrc_rpg.xp.multiplier.X` gewähren:
- X = 1: +10% XP
- X = 2: +20% XP
- X = 5: +50% XP
- etc.

**Beispiel permissions.yml:**
```yaml
groups:
 vip:
 permissions:
 - nrc_rpg.xp.multiplier.2 # +20% XP
```

---

## Performance-Tipps

### Auto-Save Interval
- **Niedrig (60-120s):** Sicherer bei Crashes, höhere I/O-Last
- **Mittel (300s):** Empfohlen für die meisten Server
- **Hoch (600s+):** Weniger I/O, höheres Risiko bei Crash

### Abilities
Bei Performance-Problemen:
1. `enable_abilities` auf `false` setzen
2. Radius-Werte reduzieren
3. Cooldowns erhöhen

### Debug Mode
Nur für Entwicklung aktivieren!
- Erzeugt große Log-Dateien
- Kann Performance beeinträchtigen

---

## Beispiel-Konfigurationen

### Hardcore Server (langsame Progression)

```json
{
 "xp_multiplier": 0.5,
 "max_level": 100,
 "auto_save_interval": 180,
 "skills": {
 "combat": {
 "xp_rates": {
 "melee_damage": 1.0,
 "kill_bonus": 10.0
 }
 }
 }
}
```

### Casual Server (schnelle Progression)

```json
{
 "xp_multiplier": 2.0,
 "max_level": 50,
 "auto_save_interval": 300,
 "skills": {
 "combat": {
 "xp_rates": {
 "melee_damage": 3.0,
 "kill_bonus": 30.0
 }
 }
 }
}
```

### PvP-fokussiert

```json
{
 "skills": {
 "combat": {
 "enabled": true,
 "abilities": {
 "serrated_strikes": {
 "cooldown": 5,
 "damage_multiplier": 2.0
 }
 }
 },
 "mining": { "enabled": false },
 "woodcutting": { "enabled": false },
 "farming": { "enabled": false },
 "fishing": { "enabled": false }
 }
}
```

---

## Troubleshooting

### Config wird nicht geladen
1. JSON-Syntax prüfen (z.B. mit [jsonlint.com](https://jsonlint.com/))
2. Dateirechte prüfen
3. Server-Logs checken

### XP wird nicht vergeben
1. `debug_mode` aktivieren
2. Permission `nrc_rpg.skill.*` prüfen
3. `enabled` für Skill prüfen

### Abilities funktionieren nicht
1. `enable_abilities` prüfen
2. Level-Anforderung prüfen
3. Cooldown beachten

---

## Weitere Hilfe

- GitHub Issues: [github.com/NadoHimself/NRC_RPG/issues](https://github.com/NadoHimself/NRC_RPG/issues)
- Discord: [Dein Discord Server]
- Wiki: [github.com/NadoHimself/NRC_RPG/wiki](https://github.com/NadoHimself/NRC_RPG/wiki)
