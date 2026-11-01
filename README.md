# Donkey Kong 2D

A 2D platform game inspired by the classic **Donkey Kong**, built with **Java Swing**. Guide the main character through handcrafted levels, dodge enemies and traps, collect items and rescue the princess.

The project was developed as the final assignment for the **Object-Oriented Programming (OOP)**

## Features

- Multi-room level progression with support for text-based room files
- Player movement with gravity, ladders and platform collision
- Enemies and hazards: Donkey Kong, Bats, falling bananas and traps
- Weapons and items: sword, hammer, bombs and meat
- Interactive objects: climbable stairs, openable doors and victory goal
- Persistent local top-10 leaderboard
- Object-oriented architecture based on the Observer pattern

## Controls

| Key | Action |
| --- | --- |
| Arrow keys | Move and climb |
| B | Place a bomb |

## Requirements

- **Java 17 or newer** (Java 21 recommended)
- Linux, macOS or Windows
- No external dependencies

## Getting started

### 1. Clone the repository

```bash
git clone https://github.com/hmvrserra/DonkeyKongGame.git
cd DonkeyKongGame
```

### 2. Run the game

**Terminal (Linux / macOS / Windows)**

```bash
# Compile
javac -d bin $(find src -name "*.java")

# Run
java -cp bin pt.iscte.poo.game.Main
```

> **Note:** Always run from the project root folder so the game can access `images/`, `rooms/`, and `leaderboard/`.

### Run from an IDE

- **IntelliJ IDEA:** Open the project folder, ensure the Project SDK is set to Java 17 or 21 (`File > Project Structure`), and run `pt.iscte.poo.game.Main`.
- **Eclipse:** Import the project (`File > Import > Existing Projects into Workspace`) and run `Main`.

## Project structure

```text
src/
├── objects/                 Game entities and items
└── pt/iscte/poo/
    ├── game/                Game loop, room loading, and engine
    ├── gui/                 Swing GUI renderer
    ├── observer/            Observer pattern implementation
    └── utils/               Math, geometry, and direction helpers
rooms/                       Level definitions
images/                      Sprites and textures
leaderboard/                 Score records
```

## Architecture

`GameEngine` is a singleton that observes the GUI. Each tick advances the current `Room`; `Room` parses the text-based levels and manages all game entities. Game elements inherit from `GameObject` and implement `ImageTile`, allowing the GUI to render them by layer.

## Credits

- The base GUI framework and utility classes in `pt.iscte.poo.gui`, `observer`, and `utils` were provided by the **Object-Oriented Programming course team at ISCTE-IUL (POO)**.
- Gameplay, level designs, characters, and game logic developed by **Hugo Serra**.
- Sprite assets are used for educational, non-commercial purposes.
