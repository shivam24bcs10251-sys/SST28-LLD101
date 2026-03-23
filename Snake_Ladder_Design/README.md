# 🐍 Snake and Ladder Game (Java - LLD Design)

A modular and extensible **Snake and Ladder game** built using **Java**, following **Low-Level Design (LLD)** principles and multiple design patterns like **Strategy, Factory, and Observer**.

---

## 🚀 Features

* 🎯 Modular and scalable architecture
* 🧠 Uses multiple design patterns:

  * Strategy Pattern (Game rules, board setup)
  * Factory Pattern (Game creation)
  * Observer Pattern (Notifications)
* 🎲 Customizable board:

  * Standard board
  * Random board (Easy / Medium / Hard)
  * Fully custom board
* 👥 Multi-player support
* 🔄 Turn-based game logic
* 📢 Observer notifications for events

---

## 🏗️ Project Structure

```
snakeandladder/
│
├── Main.java
│
├── game/
│   ├── SnakeAndLadderGame.java
│   ├── SnakeAndLadderGameFactory.java
│
├── board/
│   ├── Board.java
│   ├── BoardEntity.java
│   ├── Snake.java
│   ├── Ladder.java
│
├── player/
│   ├── SnakeAndLadderPlayer.java
│
├── dice/
│   ├── Dice.java
│
├── strategy/
│   ├── BoardSetupStrategy.java
│   ├── RandomBoardSetupStrategy.java
│   ├── CustomCountBoardSetupStrategy.java
│   ├── StandardBoardSetupStrategy.java
│   ├── SnakeAndLadderRules.java
│   ├── StandardSnakeAndLadderRules.java
│
├── observer/
│   ├── IObserver.java
│   ├── SnakeAndLadderConsoleNotifier.java
```

---

## ⚙️ How to Run

### ✅ 1. Clone the Repository

```
git clone <your-repo-url>
cd snakeandladder
```

---

### ✅ 2. Compile the Code

```
javac */*.java Main.java
```

---

### ✅ 3. Run the Application

```
java Main
```

---

## 🎮 Gameplay Flow

1. Select game type:

   * Standard
   * Random (difficulty-based)
   * Custom

2. Enter number of players

3. Players take turns rolling the dice

4. Game continues until someone reaches the final cell

---

## 🧠 Design Patterns Used

### 1. Strategy Pattern

Used for:

* Board setup (`BoardSetupStrategy`)
* Game rules (`SnakeAndLadderRules`)

👉 Easily extendable (e.g., new rules, new board types)

---

### 2. Factory Pattern

Used in:

* `SnakeAndLadderGameFactory`

👉 Encapsulates game creation logic

---

### 3. Observer Pattern

Used for:

* Game event notifications

👉 Example:

* Player hits snake
* Player climbs ladder
* Game ends

---

## 🔧 Extensibility

You can easily extend the system:

* ➕ Add new rules (Timed game, Power-ups)
* 🎲 Add multiple dice
* 🌐 Convert into multiplayer server
* 🎨 Add GUI (JavaFX / Web UI)
* 🤖 Add AI players

---

## 💻 Tech Stack

* Java (Core)
* OOP & Design Patterns
* Collections Framework (HashMap, Queue, List)

---

## 🧪 Example Run

```
Enter players: 2
Name: Shivam
Name: Rahul

Shivam press enter
Rolled: 5
Shivam moved to 5

Rahul press enter
Rolled: 6
Rahul moved to 6
```

---

## 📌 Key Learnings

* Writing scalable LLD code
* Applying SOLID principles
* Designing extensible systems
* Separating concerns using packages

---

## 🤝 Contributing

Feel free to fork the repo and improve:

* Add UI
* Add APIs
* Optimize game logic

---

## ⭐ Author

**Shivam Jaiswal**

---

## 📜 License

This project is open-source and free to use.

